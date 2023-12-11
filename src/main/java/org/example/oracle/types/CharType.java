package org.example.oracle.types;

public class CharType extends CharacterType {

    public enum MaxSizeUnit {
        CHAR,
        BYTE,
    }

    private Integer maxSize;

    private MaxSizeUnit unit;

    public CharType(Integer maxSize, MaxSizeUnit unit) {
        this.maxSize = maxSize;
        this.unit = unit;
    }
}
