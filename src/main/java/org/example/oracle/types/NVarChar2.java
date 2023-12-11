package org.example.oracle.types;

public class NVarChar2 extends CharacterType {

    private Integer maxSize;

    public NVarChar2(Integer maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean canCastTo(Type targetType) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
