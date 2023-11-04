package org.example.plsql.ast;

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
    public boolean areEqual(Node node) {
        if (!(node instanceof BetweenExpression)) {
            return false;
        }

        BetweenExpression other = (BetweenExpression) node;

        return other.value.areEqual(this.value) && other.left.areEqual(this.left) && other.right.areEqual(this.right);
    }
}
