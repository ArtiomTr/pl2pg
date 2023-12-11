package org.example.postgres.generation;

import org.example.oracle.ast.*;
import org.example.oracle.types.CharacterType;
import org.example.oracle.types.Type;
import org.example.utils.StringUtils;

import java.util.List;

public class GeneratorVisitor implements Visitor<String> {

    private static class Utils {

        public static String stringifyExpressionWithTargetType(GeneratorVisitor visitor, Expression expression, Type targetType) {
            if (!(targetType instanceof CharacterType)) {
                return expression.accept(visitor);
            }

            if (expression instanceof NullLiteral) {
                return "''";
            }

            return "coalesce(" + expression.accept(visitor) + ", '')";
        }
    }

    private int indent = 0;
    private static final int INDENTATION_SIZE = 4;
    private static final String LINE_SEPARATOR = "\r\n";

    private String getIndent() {
        return StringUtils.repeat(" ", indent);
    }

    @Override
    public String visit(Program p) {
        List<Block> blocks = p.getBlocks();

        StringBuilder output = new StringBuilder();
        for (Block b: blocks) {
            output.append("DO $$").append(LINE_SEPARATOR);
            output.append(b.accept(this));
            output.append(LINE_SEPARATOR).append("$$;").append(LINE_SEPARATOR);
        }

        return output.toString();
    }

    @Override
    public String visit(Block block) {
        DeclareSection section = block.getDeclareSection();

        StringBuilder output = new StringBuilder();
        if (section != null) {
            output.append(section.accept(this));
        }

        output.append(block.getBody().accept(this));

        return output.toString();
    }

    @Override
    public String visit(Body body) {
        StringBuilder builder = new StringBuilder();

        builder.append(getIndent()).append("BEGIN").append(LINE_SEPARATOR);
        this.indent += INDENTATION_SIZE;
        for (Statement st: body.getStatements()) {
            builder.append(st.accept(this)).append(LINE_SEPARATOR);
        }
        this.indent -= INDENTATION_SIZE;
        builder.append(getIndent()).append("END;");

        return builder.toString();
    }

    @Override
    public String visit(AssignmentStatement statement) {
        return getIndent() + statement.getTarget().accept(this) +
                " := " +
                Utils.stringifyExpressionWithTargetType(this, statement.getValue(), statement.getResolvedType()) + ";";
    }

    @Override
    public String visit(BetweenExpression expression) {
        return "(" + expression.getValue().accept(this) + " BETWEEN " + expression.getLeft().accept(this) + " AND " + expression.getRight().accept(this) + ")";
    }

    @Override
    public String visit(BinaryExpression expression) {
        return "(" + expression.getLeft().accept(this) + " " + expression.getOperator().prettyPrint() + " " + expression.getRight().accept(this) + ")";
    }

    @Override
    public String visit(BooleanLiteral literal) {
        return literal.getValue() ? "TRUE" : "FALSE";
    }

    @Override
    public String visit(CaseStatement statement) {
        StringBuilder builder = new StringBuilder();

        builder.append(getIndent()).append("CASE");
        Expression selector = statement.getSelector();
        if (selector != null) {
            builder.append(" ").append(selector.accept(this));
        }

        builder.append(LINE_SEPARATOR);
        this.indent += INDENTATION_SIZE;
        for(CaseStatement.When when : statement.getWhens()) {
            builder.append(when.accept(this)).append(LINE_SEPARATOR);
        }

        CaseStatement.Else elseBlock = statement.getElseBlock();
        if (elseBlock != null) {
            builder.append(elseBlock.accept(this)).append(LINE_SEPARATOR);
        }

        this.indent -= INDENTATION_SIZE;

        builder.append(getIndent()).append("END CASE;");

        return builder.toString();
    }

    @Override
    public String visit(CaseStatement.Else elseStatement) {
        StringBuilder builder = new StringBuilder();
        builder.append(getIndent()).append("ELSE").append(LINE_SEPARATOR);
        this.indent += INDENTATION_SIZE;
        builder.append(elseStatement.getThen().accept(this));
        this.indent -= INDENTATION_SIZE;
        return builder.toString();
    }

    @Override
    public String visit(CaseStatement.When whenStatement) {
        StringBuilder builder = new StringBuilder();
        builder.append(getIndent()).append("WHEN ").append(whenStatement.getExpression().accept(this)).append(" THEN").append(LINE_SEPARATOR);
        this.indent += INDENTATION_SIZE;
        builder.append(whenStatement.getThen().accept(this));
        this.indent -= INDENTATION_SIZE;
        return builder.toString();
    }

    @Override
    public String visit(CollectionExistExpression expression) {
        return null;
    }

    @Override
    public String visit(CursorExpression expression) {
        return null;
    }

    @Override
    public String visit(ExpressionIdentifier identifier) {
        String output = identifier.getIdentifier().accept(this);

        Type type = identifier.getResolvedType();

        if (type instanceof CharacterType) {
            return "coalesce(" + output + ", '')";
        }
        return output;
    }

    @Override
    public String visit(Identifier identifier) {
        return identifier.getName();
    }

    @Override
    public String visit(IfStatement statement) {
        return null;
    }

    @Override
    public String visit(IfStatement.Else elseStatement) {
        return null;
    }

    @Override
    public String visit(IfStatement.Elsif elsifStatement) {
        return null;
    }

    @Override
    public String visit(ImplicitCursor cursor) {
        return null;
    }

    @Override
    public String visit(Label label) {
        return null;
    }

    @Override
    public String visit(LoopStatement loopStatement) {
        return null;
    }

    @Override
    public String visit(NamedCursor cursor) {
        return null;
    }

    @Override
    public String visit(NullLiteral literal) {
        return null;
    }

    @Override
    public String visit(NumberLiteral literal) {
        return literal.getValue();
    }

    @Override
    public String visit(StringLiteral literal) {
        return '\'' + literal.getValue().replace("\\", "\\\\").replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'") + '\'';
    }

    @Override
    public String visit(UnaryExpression expression) {
        return "(" + expression.getOperator().prettyPrint() + " " + expression.getArgument().accept(this) + ")";
    }

    @Override
    public String visit(AssocArrayTypeToken assocArray) {
        return null;
    }

    @Override
    public String visit(DeclareSection section) {
        StringBuilder builder = new StringBuilder();
        builder.append(getIndent()).append("DECLARE").append(LINE_SEPARATOR);
        this.indent += INDENTATION_SIZE;
        for (Declaration d : section.getDeclarationList()) {
            builder.append(d.accept(this)).append(LINE_SEPARATOR);
        }
        this.indent -= INDENTATION_SIZE;

        return builder.toString();
    }

    @Override
    public String visit(NestedTableTypeToken nestedTable) {
        return null;
    }

    @Override
    public String visit(NonNullTypeToken nonNull) {
        return null;
    }

    @Override
    public String visit(RecordTypeToken record) {
        return null;
    }

    @Override
    public String visit(RecordTypeToken.FieldDefinition fieldDefinition) {
        return null;
    }

    @Override
    public String visit(RefCursorTypeToken refCursor) {
        return null;
    }

    @Override
    public String visit(RowTypeAttribute rowTypeAttribute) {
        return null;
    }

    @Override
    public String visit(SubtypeDefinition subtype) {
        return null;
    }

    @Override
    public String visit(SubtypeDefinition.CharacterSetConstraint constraint) {
        return null;
    }

    @Override
    public String visit(SubtypeDefinition.PrecisionConstraint constraint) {
        return null;
    }

    @Override
    public String visit(SubtypeDefinition.RangeConstraint constraint) {
        return null;
    }

    @Override
    public String visit(TypeAttribute typeAttribute) {
        return null;
    }

    @Override
    public String visit(TypeDefinition type) {
        return null;
    }

    @Override
    public String visit(VaryingArrayTypeToken varyingArray) {
        return null;
    }

    @Override
    public String visit(VariableDeclaration declaration) {
        StringBuilder builder = new StringBuilder();

        builder.append(getIndent()).append(declaration.getId().accept(this)).append(" ").append(declaration.getType().accept(this));

        Expression defaultValue = declaration.getDefaultValue();
        if (defaultValue != null) {
            builder.append(" := ").append(Utils.stringifyExpressionWithTargetType(this, defaultValue, declaration.getResolvedType()));
        }
        builder.append(';');

        return builder.toString();
    }

    @Override
    public String visit(TypeReference reference) {
        return reference.getTypeName().accept(this);
    }

    @Override
    public String visit(TypeReferenceOperator referenceOperator) {
        return null;
    }
}
