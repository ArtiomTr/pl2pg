package org.example.oracle.ast;

public class StringLiteral extends Expression {

    private String value;

    public StringLiteral(String value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getValue() {
        return value;
    }
}
