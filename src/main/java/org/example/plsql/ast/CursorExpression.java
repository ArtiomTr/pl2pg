package org.example.plsql.ast;

public class CursorExpression extends Expression {

    public enum Operator {
        FOUND,
        ISOPEN,
        NOTFOUND,
    }

    private Operator operator;
    private Cursor cursor;

    public CursorExpression(Cursor cursor, Operator operator) {
        this.cursor = cursor;
        this.operator = operator;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof CursorExpression)) {
            return false;
        }

        CursorExpression other = (CursorExpression) node;

        return other.operator.equals(this.operator) && other.cursor.areEqual(this.cursor);
    }
}
