package org.example.plsql.types;

public class Subtype extends Type {

    private Type baseType;

    @Override
    public boolean canCastTo(Type targetType) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
