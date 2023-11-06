package org.example.plsql.ast;

public class Block extends Statement {

    private Body body;

    public Block(Body body) {
        this.body = body;
    }

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof Block) && this.labelsEqual((Block) node) && ((Block) node).body.areEqual(body);
    }
}
