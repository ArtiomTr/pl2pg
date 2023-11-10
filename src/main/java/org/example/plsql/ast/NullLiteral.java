package org.example.plsql.ast;

public class NullLiteral extends Expression {

    public NullLiteral() {}

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
