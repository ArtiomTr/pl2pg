package org.example.plsql.ast;

public class Block extends Statement {

    private Body body;

    private DeclareSection declareSection;

    public Block(Body body, DeclareSection declareSection) {
        this.body = body;
        this.declareSection = declareSection;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Body getBody() {
        return body;
    }

    public DeclareSection getDeclareSection() {
        return declareSection;
    }
}
