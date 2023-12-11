package org.example.oracle.ast;

public class RefCursorTypeToken extends TypeToken {

    private TypeToken returnType;

    public RefCursorTypeToken(TypeToken returnType) {
        this.returnType = returnType;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
