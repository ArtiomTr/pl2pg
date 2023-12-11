package org.example.oracle.types;

public class NullType extends ScalarType {

    @Override
    public boolean canCastTo(Type targetType) {
        return targetType instanceof ScalarType;
    }
}
