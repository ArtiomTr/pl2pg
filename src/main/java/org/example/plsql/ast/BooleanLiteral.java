package org.example.plsql.ast;

public class BooleanLiteral extends Expression {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }


    @Override
    public boolean areEqual(Node node) {
        return (node instanceof BooleanLiteral) && ((BooleanLiteral) node).value == this.value;
    }
}
