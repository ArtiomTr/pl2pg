package org.example.plsql.ast;

public class TypeAttribute extends TypeToken {

    private Identifier id;

    private Identifier field;

    public TypeAttribute(Identifier id, Identifier field) {
        this.id = id;
        this.field = field;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
