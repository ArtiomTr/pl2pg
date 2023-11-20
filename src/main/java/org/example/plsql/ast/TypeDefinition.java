package org.example.plsql.ast;

public class TypeDefinition extends Declaration {

    private Identifier id;

    private TypeToken typeToken;

    public TypeDefinition(Identifier id, TypeToken typeToken) {
        this.id = id;
        this.typeToken = typeToken;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("Not implemented!");
    }

    public Identifier getId() {
        return id;
    }

    public TypeToken getTypeToken() {
        return typeToken;
    }
}
