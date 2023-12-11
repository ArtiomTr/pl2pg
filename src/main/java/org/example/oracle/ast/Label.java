package org.example.oracle.ast;

public class Label extends Node {

    private String name;

    public Label(String name) {
        this.name = name;
    }

    public Label(Identifier identifier) {
        this.name = identifier.getName();
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }
}
