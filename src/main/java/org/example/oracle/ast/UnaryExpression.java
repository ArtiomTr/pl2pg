package org.example.oracle.ast;

public class UnaryExpression extends Expression {
    public enum Operator {
        IDENTITY,
        NEGATION,
        NOT;

        public String prettyPrint() {
            switch (this) {
                case IDENTITY:
                    return "+";
                case NEGATION:
                    return "-";
                case NOT:
                    return "NOT";
                default:
                    throw new RuntimeException("Cannot pretty-print operator " + this);
            }
        }
    }

    private Expression argument;

    private Operator operator;

    public UnaryExpression(Expression argument, Operator operator) {
        this.argument = argument;
        this.operator = operator;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getArgument() {
        return argument;
    }

    public Operator getOperator() {
        return operator;
    }
}
