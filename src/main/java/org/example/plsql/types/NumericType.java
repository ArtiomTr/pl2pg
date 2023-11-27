package org.example.plsql.types;

public abstract class NumericType extends ScalarType {

    protected Long minValue;
    protected Long maxValue;

    @Override
    public boolean canCastTo(Type targetType) {
        return targetType instanceof NumericType;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    public static class Builder {

        private NumericType type;

        public Builder(NumericType base) {
            this.type = base;
        }

        public NumericType build() {
            return this.type;
        }

        public Builder nonNullable(boolean nonNullable) {
            this.type.setNonNullable(nonNullable);
            return this;
        }

        public Builder nonNullable() {
            return this.nonNullable(true);
        }

        public Builder minValue(Long minValue) {
            this.type.setMinValue(minValue);
            return this;
        }

        public Builder maxValue(Long maxValue) {
            this.type.setMaxValue(maxValue);
            return this;
        }
    }
}
