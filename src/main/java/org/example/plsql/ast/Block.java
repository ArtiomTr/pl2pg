package org.example.plsql.ast;

public class Block extends Statement {

    private Body body;

    public Block(Body body) {
        this.body = body;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Body getBody() {
        return body;
    }
}
