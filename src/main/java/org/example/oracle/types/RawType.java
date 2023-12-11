package org.example.oracle.types;

public class RawType extends ScalarType {

    private Integer maxSize;

    public RawType(Integer maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean canCastTo(Type targetType) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
