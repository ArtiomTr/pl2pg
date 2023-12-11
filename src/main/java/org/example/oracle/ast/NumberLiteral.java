package org.example.oracle.ast;

public class NumberLiteral extends Expression {

    private String value;

    public NumberLiteral(String value) {
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
