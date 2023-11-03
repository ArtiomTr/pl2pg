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
    public boolean areEqual(Node node) {
        if (!(node instanceof BinaryExpression)) {
            return false;
        }

        BinaryExpression other = (BinaryExpression) node;

        return other.left.areEqual(this.left) && other.right.areEqual(this.right) && other.operator == this.operator;
    }
}
