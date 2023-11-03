package org.example.plsql.ast;

public class Block extends Node {

    private Body body;

    public Block(Body body) {
        this.body = body;
    }

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof Block) && ((Block) node).body.areEqual(body);
    }
}
