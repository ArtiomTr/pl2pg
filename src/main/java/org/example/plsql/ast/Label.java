package org.example.plsql.ast;

public class Label extends Node {

    private String name;

    public Label(String name) {
        this.name = name;
    }

    public Label(Identifier identifier) {
        this.name = identifier.getName();
    }

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof Label) && ((Label) node).name.equals(this.name);
    }
}
