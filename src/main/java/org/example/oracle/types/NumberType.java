package org.example.oracle.types;

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
}
