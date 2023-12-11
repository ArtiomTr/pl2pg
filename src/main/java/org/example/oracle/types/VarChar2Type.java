package org.example.oracle.types;

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
}
