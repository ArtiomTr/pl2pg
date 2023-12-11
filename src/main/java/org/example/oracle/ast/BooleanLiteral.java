package org.example.oracle.ast;

public class BooleanLiteral extends Expression {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean getValue() {
        return value;
    }
}
