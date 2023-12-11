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
                    throw new TypeCheckError("Incorrect types supplied to BETWEEN operator - cannot convert to numeric.");
                }
            }

            // When comparing a character value with `DATE` value, Oracle converts the character data to `DATE`
//            if (Arrays.stream(types).anyMatch())
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
        Identifier target = statement.getTarget();

        Type targetType = this.table.lookupVariable(target.getName());

        if (targetType == null) {
            throw new TypeCheckError("Use of undeclared variable \"" + target.getName() + "\"");
        }

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

        return Utils.performComparisonImplicitTypeConversion(valueType, leftType, rightType);
    }

    @Override
    public Type visit(BinaryExpression expression) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(BooleanLiteral literal) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(CaseStatement statement) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(CaseStatement.Else elseStatement) {
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(CaseStatement.When whenStatement) {
        // TODO:
        throw new RuntimeException("not implemented");
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
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(Identifier identifier) {
        // TODO:
        throw new RuntimeException("not implemented");
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
        // TODO:
        throw new RuntimeException("not implemented");
    }

    @Override
    public Type visit(NumberLiteral literal) {
        return new NumberType(null, null);
    }

    @Override
    public Type visit(StringLiteral literal) {
        return new VarChar2Type(null, null);
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
