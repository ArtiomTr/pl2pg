package org.example.oracle.types;

public class BooleanType extends ScalarType {
    @Override
    public boolean canCastTo(Type targetType) {
        return targetType instanceof BooleanType;
    }
}
