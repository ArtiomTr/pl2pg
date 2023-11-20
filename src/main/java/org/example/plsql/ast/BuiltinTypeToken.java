package org.example.plsql.ast;

public class BuiltinTypeToken extends TypeToken {

    public enum Type {
        PLS_INTEGER,
        BINARY_INTEGER,
        LONG
    }

    private Type type;

    public BuiltinTypeToken(Type type) {
        this.type = type;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
