package org.example.plsql.ast;

public class UnaryExpression extends Expression {
    public enum Operator {
        IDENTITY,
        NEGATION,
        NOT,
    }

    private Expression argument;

    private Operator operator;

    public UnaryExpression(Expression argument, Operator operator) {
        this.argument = argument;
        this.operator = operator;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof UnaryExpression)) {
            return false;
        }

        UnaryExpression other = (UnaryExpression) node;

        return other.argument.areEqual(this.argument) && other.operator == this.operator;
    }
}
