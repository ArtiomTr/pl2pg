package org.example.plsql.ast;

public class CursorExpression extends Expression {
    public enum Operator {
        FOUND,
        ISOPEN,
        NOTFOUND;

        public String prettyPrint() {
            switch (this) {
                case FOUND:
                    return "FOUND";
                case NOTFOUND:
                    return "NOTFOUND";
                case ISOPEN:
                    return "ISOPEN";
                default:
                    throw new RuntimeException("Cannot pretty print " + this);
            }
        }
    }

    private Operator operator;
    private Cursor cursor;

    public CursorExpression(Cursor cursor, Operator operator) {
        this.cursor = cursor;
        this.operator = operator;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
