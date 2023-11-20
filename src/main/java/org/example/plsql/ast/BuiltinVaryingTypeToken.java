package org.example.plsql.ast;

public class BuiltinVaryingTypeToken extends TypeToken {
    public enum Type {
        VARCHAR,
        VARCHAR2,
        STRING,
    }

    private Type type;

    private NumberLiteral size;

    public BuiltinVaryingTypeToken(Type type, NumberLiteral size) {
        this.type = type;
        this.size = size;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
