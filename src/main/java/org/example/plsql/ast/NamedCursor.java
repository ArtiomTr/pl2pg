package org.example.plsql.ast;

public class NamedCursor extends Cursor {

    private String name;

    public NamedCursor(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }
}
