package org.example.oracle.types;

public abstract class CharacterType extends ScalarType {


    @Override
    public boolean canCastTo(Type targetType) {
        return targetType instanceof CharacterType;
    }
}
