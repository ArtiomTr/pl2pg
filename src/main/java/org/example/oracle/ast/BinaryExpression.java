package org.example.oracle.ast;

public class BinaryExpression extends Expression {
    public enum Operator {
        EXPONENTIATION,
        MULTIPLICATION,
        DIVISION,
        ADDITION,
        SUBTRACTION,
        CONCATENATION,
        LESS_THAN,
        LESS_THAN_OR_EQUAL,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL,
        EQUAL,
        NOT_EQUAL,
        IS,
        NOT,
        AND,
        OR;

        public String prettyPrint() {
            switch (this) {
                case IS:
                    return "IS";
                case AND:
                    return "AND";
                case OR:
                    return "OR";
                case EQUAL:
                    return "=";
                case ADDITION:
                    return "+";
                case CONCATENATION:
                    return "||";
                case DIVISION:
                    return "/";
                case LESS_THAN:
                    return "<";
                case NOT:
                    return "NOT";
                case SUBTRACTION:
                    return "-";
                case EXPONENTIATION:
                    return "**";
                case NOT_EQUAL:
                    return "!=";
                case MULTIPLICATION:
                    return "*";
                case GREATER_THAN:
                    return ">";
                case LESS_THAN_OR_EQUAL:
                    return "<=";
                case GREATER_THAN_OR_EQUAL:
                    return ">=";
                default:
                    throw new RuntimeException("Cannot pretty-print symbol " + this);
            }
        };
    }

    private Expression left;
    private Expression right;
    private Operator operator;

    public BinaryExpression(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public Operator getOperator() {
        return operator;
    }
}
