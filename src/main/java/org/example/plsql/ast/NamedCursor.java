package org.example.plsql.ast;

public class NamedCursor extends Cursor {

    private String name;

    public NamedCursor(String name) {
        this.name = name;
    }

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof NamedCursor) && ((NamedCursor) node).name.equals(this.name);
    }
}
