package org.example.plsql.ast;

public class NullLiteral extends Expression {

    public NullLiteral() {}

    @Override
    public boolean areEqual(Node node) {
        return (node instanceof NullLiteral);
    }
}
