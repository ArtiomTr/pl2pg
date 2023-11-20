package org.example.plsql.ast;

public class RefCursorTypeToken extends TypeToken {

    private TypeToken returnType;

    public RefCursorTypeToken(TypeToken returnType) {
        this.returnType = returnType;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("Not implemented");
    }
}
