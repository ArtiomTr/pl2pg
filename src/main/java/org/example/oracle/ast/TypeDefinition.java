package org.example.oracle.ast;

public class TypeDefinition extends Declaration {

    private Identifier id;

    private TypeToken typeToken;

    public TypeDefinition(Identifier id, TypeToken typeToken) {
        this.id = id;
        this.typeToken = typeToken;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Identifier getId() {
        return id;
    }

    public TypeToken getTypeToken() {
        return typeToken;
    }
}
