package org.example.plsql.ast;

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
        OR,
    };

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
