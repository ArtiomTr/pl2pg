package org.example.plsql.types;

public class NumberType extends NumericType {

    private Integer precision;
    private Integer scale;

    public NumberType(Integer precision, Integer scale) {
        this.precision = precision;
        this.scale = scale;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Integer getScale() {
        return scale;
    }

    @Override
    public boolean canCastTo(Type targetType) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
