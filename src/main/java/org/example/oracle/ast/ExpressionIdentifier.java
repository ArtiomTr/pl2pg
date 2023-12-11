package org.example.oracle.ast;

public class ExpressionIdentifier extends Expression {

    private Identifier identifier;

    public ExpressionIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
