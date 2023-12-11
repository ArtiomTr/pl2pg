package org.example.oracle.types;

import org.example.oracle.ast.*;

import java.util.Arrays;

public class TypeCheckVisitor implements Visitor<Type> {

    public static class Utils {

        public static Type performComparisonImplicitTypeConversion(Type ...types) {
            // Do implicit conversions by rules https://docs.oracle.com/cd/E11882_01/server.112/e41084/sql_elements002.htm#SQLRF51047
            // When comparing a character value with numeric value, Oracle converts character data to a numeric value
            // This branch also covers case, when all types are numeric.
            if (Arrays.stream(types).anyMatch((v) -> v instanceof NumericType) && Arrays.stream(types).allMatch((v) -> v instanceof NumericType || v instanceof CharacterType)) {
                // Determine numeric type, in which all values could fit
                NumericType targetType = Utils.getLargestNumericType(Arrays.stream(types).filter((v) -> v instanceof NumericType).toArray(NumericType[]::new));

                // Check if all arguments can be converted to given numeric type
                if (Arrays.stream(types).allMatch((v) -> v.canCastTo(targetType))) {
                    return targetType;
                } else {
                    return null;
                }
            }

            // When comparing a character value with `DATE` value, Oracle converts the character data to `DATE`
//            if (Arrays.stream(types).anyMatch())

            // If not a special case, try to cast all types to the first one
            if (Arrays.stream(types).skip(1).allMatch((v) -> v.canCastTo(types[0]))) {
                return types[0];
            }

            return null;
        }

        public static NumericType getLargestNumericType(NumericType ...types) {
            int floatCount = 0;
            int doubleCount = 0;
            int intCount = 0;
            int plsIntCount = 0;
            int numberCount = 0;

            Integer maxPrecision = null;
            Integer maxScale = null;

            for (NumericType type : types) {
                if (type instanceof BinaryFloatType) {
                    floatCount += 1;
                } else if (type instanceof BinaryDoubleType) {
                    doubleCount += 1;
                } else if (type instanceof BinaryIntegerType) {
                    intCount += 1;
                } else if (type instanceof PlsIntegerType) {
                    plsIntCount += 1;
                } else if (type instanceof NumberType) {
                    numberCount += 1;

                    NumberType casted = (NumberType) type;

                    if (casted.getPrecision() != null && (maxPrecision == null || maxPrecision < casted.getPrecision())) {
                        maxPrecision = casted.getPrecision();
                    }

                    if (casted.getScale() != null && (maxScale == null || maxScale < casted.getScale())) {
                        maxScale = casted.getScale();
                    }
                } else {
                    throw new RuntimeException("Unknown numeric type " + type);
                }
            }

            if (numberCount > 0) {
                return new NumberType(maxPrecision, maxScale);
            } else if (doubleCount > 0) {
                return new BinaryDoubleType();
            } else if (floatCount > 0) {
                return new BinaryFloatType();
            } else if (plsIntCount > 0) {
                return new PlsIntegerType();
            } else if (intCount > 0) {
                return new BinaryIntegerType();
            } else {
                throw new RuntimeException("Invalid state - most likely TypeCheck issue");
            }
        }
    }

    private SymbolTable table;

    public TypeCheckVisitor() {
        this.table = new SymbolTable();
    }

    @Override
    public Type visit(Program p) {
        for (Block b : p.getBlocks()) {
            SymbolTable old = table;
            this.table = old.cloneTable();
            b.accept(this);
            this.table = old;
        }

        return null;
    }

    @Override
    public Type visit(Block block) {
        DeclareSection declarations = block.getDeclareSection();

        if (declarations != null) {
            declarations.accept(this);
        }

        return block.getBody().accept(this);
    }

    @Override
    public Type visit(Body body) {
        for (Statement s : body.getStatements()) {
            s.accept(this);
        }

        // TODO: add return statement logic
        return null;
    }

    @Override
    public Type visit(AssignmentStatement statement) {
        Type targetType = statement.getTarget().accept(this);

        Type valueType = statement.getValue().accept(this);

        if (!valueType.canCastTo(targetType)) {
            throw new TypeCheckError("Type " + valueType + " is not assignable to " + targetType);
        }

        return null;
    }

    @Override
    public Type visit(BetweenExpression expression) {
        Type valueType = expression.getValue().accept(this);
        Type leftType = expression.getLeft().accept(this);
        Type rightType = expression.getRight().accept(this);

        Type outputType = Utils.performComparisonImplicitTypeConversion(valueType, leftType, rightType);
        if (outputType == null) {
            throw new TypeCheckError("Incorrect types supplied to BETWEEN operator - cannot convert to numeric.");
        }

        return outputType;
    }

    @Override
    public Type visit(BinaryExpression expression) {
        Type leftType = expression.getLeft().accept(this);
        Type rightType = expression.getRight().accept(this);

        BinaryExpression.Operator operator = expression.getOperator();

        Type returnType = null;

        switch (operator) {
            case ADDITION:
            case SUBTRACTION:
            case MULTIPLICATION:
            case EXPONENTIATION:
            case DIVISION:
                if (leftType instanceof NumericType && rightType instanceof NumericType) {
                    returnType = Utils.getLargestNumericType((NumericType) leftType, (NumericType) rightType);
                } else if (leftType.canCastTo(new NumberType(null, null)) && rightType.canCastTo(new NumberType(null, null))) {
                    returnType = new NumberType(null, null);
                }
                break;
            case EQUAL:
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
            case LESS_THAN:
            case LESS_THAN_OR_EQUAL:
            case NOT_EQUAL:
                if (Utils.performComparisonImplicitTypeConversion(leftType, rightType) != null) {
                    returnType = new BooleanType();
                }

                break;
            case AND:
            case OR:
                if (leftType.canCastTo(new BooleanType()) && rightType.canCastTo(new BooleanType())) {
                    returnType = new BooleanType();
                }
                break;
            case CONCATENATION:
                if (leftType.canCastTo(new VarChar2Type(null, null)) && rightType.canCastTo(new VarChar2Type(null, null))) {
                    returnType = leftType;
                }
                break;
            case IS:
                if (rightType.canCastTo(new NullType())) {
                    returnType = new BooleanType();
                }
                break;
        }

        if (returnType == null) {
            throw new TypeCheckError("Invalid `" + operator.prettyPrint() + "` operator operands - expected numeric types, but got " + leftType + " and " + rightType);
        }

        expression.setResolvedType(returnType);

        return returnType;
    }

    @Override
    public Type visit(BooleanLiteral literal) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(CaseStatement statement) {
        Expression selector = statement.getSelector();

        Type targetType;
        if (selector != null) {
            targetType = selector.accept(this);
        } else {
            targetType = new BooleanType();
        }

        for (CaseStatement.When when : statement.getWhens()) {
            Type type = when.accept(this);
            if (!type.canCastTo(targetType)) {
                throw new TypeCheckError("Invalid case statement - when clause must have same type, as selector (expected " + targetType + ", but found " + type + ")");
            }
        }

        CaseStatement.Else elseBlock = statement.getElseBlock();
        if (elseBlock != null) {
            elseBlock.accept(this);
        }

        statement.setResolvedType(targetType);
        return targetType;
    }

    @Override
    public Type visit(CaseStatement.Else elseStatement) {
        Type type = elseStatement.getThen().accept(this);
        elseStatement.setResolvedType(type);
        return type;
    }

    @Override
    public Type visit(CaseStatement.When whenStatement) {
        whenStatement.getThen().accept(this);
        return whenStatement.getExpression().accept(this);
    }

    @Override
    public Type visit(CollectionExistExpression expression) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(CursorExpression expression) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(ExpressionIdentifier identifier) {
        Type t = identifier.getIdentifier().accept(this);
        identifier.setResolvedType(t);
        return t;
    }

    @Override
    public Type visit(Identifier identifier) {
        Type type = table.lookupVariable(identifier.getName());
        if (type == null) {
            throw new TypeCheckError("Use of undeclared variable");
        }
        identifier.setResolvedType(type);
        return type;
    }

    @Override
    public Type visit(IfStatement statement) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(IfStatement.Else elseStatement) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(IfStatement.Elsif elsifStatement) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(ImplicitCursor cursor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(Label label) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(LoopStatement loopStatement) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(NamedCursor cursor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(NullLiteral literal) {
        Type type = new NullType();
        literal.setResolvedType(type);
        return type;
    }

    @Override
    public Type visit(NumberLiteral literal) {
        Type t = new NumberType(null, null);
        literal.setResolvedType(t);
        return t;
    }

    @Override
    public Type visit(StringLiteral literal) {
        Type type = new VarChar2Type(null, null);
        literal.setResolvedType(type);
        return type;
    }

    @Override
    public Type visit(UnaryExpression expression) {
        Type argumentType = expression.getArgument().accept(this);

        switch (expression.getOperator()) {
            case NOT:
                if (argumentType instanceof BooleanType) {
                    return argumentType;
                } else {
                    throw new TypeCheckError("Invalid NOT operator operand - expected to be of type Boolean, but found " + argumentType);
                }
            case NEGATION:
            case IDENTITY:
                if (argumentType instanceof NumericType) {
                    return argumentType;
                } else {
                    throw new TypeCheckError("Invalid numeric unary operator operand - expected to be numeric type, but was " + argumentType);
                }
            default:
                throw new RuntimeException("Unknown unary operator " + expression.getOperator());
        }
    }

    @Override
    public Type visit(AssocArrayTypeToken assocArray) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(DeclareSection section) {
        for (Declaration declaration : section.getDeclarationList()) {
            declaration.accept(this);
        }

        return null;
    }

    @Override
    public Type visit(NestedTableTypeToken nestedTable) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(NonNullTypeToken nonNull) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(RecordTypeToken record) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(RecordTypeToken.FieldDefinition fieldDefinition) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(RefCursorTypeToken refCursor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(RowTypeAttribute rowTypeAttribute) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(SubtypeDefinition subtype) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(SubtypeDefinition.CharacterSetConstraint constraint) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(SubtypeDefinition.PrecisionConstraint constraint) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(SubtypeDefinition.RangeConstraint constraint) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(TypeAttribute typeAttribute) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(TypeDefinition type) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(VaryingArrayTypeToken varyingArray) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(VariableDeclaration declaration) {
        Type variableType = declaration.getType().accept(this);

        Expression defaultValue = declaration.getDefaultValue();
        if (defaultValue != null) {
            Type expressionType = defaultValue.accept(this);

            if (!expressionType.canCastTo(variableType)) {
                throw new TypeCheckError("Invalid default value type - expected " + variableType + " but got " + expressionType);
            }
        }

        this.table.addVariable(declaration.getId().getName(), variableType);

        declaration.setResolvedType(variableType);

        return null;
    }

    @Override
    public Type visit(TypeReference reference) {
        return this.table.lookupType(reference.getTypeName().getName());
    }

    @Override
    public Type visit(TypeReferenceOperator referenceOperator) {
        return this.table.lookupVariable(referenceOperator.getName().getName());
    }
}
