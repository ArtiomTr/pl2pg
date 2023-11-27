package org.example.plsql.types;

public class NCharType extends CharacterType {

    private Integer maxSize;

    public NCharType(Integer maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean canCastTo(Type targetType) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
