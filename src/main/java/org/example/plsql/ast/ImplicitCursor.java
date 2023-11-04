package org.example.plsql.ast;

public class ImplicitCursor extends Cursor {

    public ImplicitCursor() {}


    @Override
    public boolean areEqual(Node node) {
        return node instanceof ImplicitCursor;
    }
}
