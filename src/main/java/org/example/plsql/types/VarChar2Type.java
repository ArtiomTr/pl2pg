package org.example.plsql.types;

public class VarChar2Type extends CharacterType {

    public enum MaxSizeUnit {
        CHAR,
        BYTE,
    }

    private Integer maxSize;

    private MaxSizeUnit unit;

    public VarChar2Type(Integer maxSize, MaxSizeUnit unit) {
        this.maxSize = maxSize;
        this.unit = unit;
    }

    @Override
    public boolean canCastTo(Type targetType) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
