package org.example.oracle.ast;

public class ImplicitCursor extends Cursor {

    public ImplicitCursor() {}

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
