package org.example.oracle.ast;

public class NonNullTypeToken extends TypeToken {

    private TypeToken typeToken;

    public NonNullTypeToken(TypeToken typeToken) {
        this.typeToken = typeToken;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
