package org.example.plsql.ast;

public class NumberLiteral extends Expression {

    private String value;

    public NumberLiteral(String value) {
        this.value = value;
    }

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof NumberLiteral) && ((NumberLiteral) node).value.equals(this.value);
    }
}
