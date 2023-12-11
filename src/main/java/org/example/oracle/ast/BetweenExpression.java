package org.example.oracle.ast;

public class BetweenExpression extends Expression {

    private Expression value;
    private Expression left;
    private Expression right;

    public BetweenExpression(Expression value, Expression left, Expression right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getValue() {
        return value;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
