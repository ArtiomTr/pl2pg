package org.example.plsql.ast;

public class Identifier extends Node {

    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof Identifier) && ((Identifier) node).name.equals(this.name);
    }
}
