package org.example.plsql.ast;

public class StringLiteral extends Expression {

    private String value;

    public StringLiteral(String value) {
        this.value = value;
    }


    @Override
    public boolean areEqual(Node node) {
        return (node instanceof StringLiteral) && ((StringLiteral) node).value.equals(this.value);
    }
}
