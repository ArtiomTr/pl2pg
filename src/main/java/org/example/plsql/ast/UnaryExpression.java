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
