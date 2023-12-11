package org.example.oracle.ast;

public class TypeReference extends TypeToken {

    private Identifier typeName;

    public TypeReference(Identifier typeName) {
        this.typeName = typeName;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Identifier getTypeName() {
        return typeName;
    }
}
