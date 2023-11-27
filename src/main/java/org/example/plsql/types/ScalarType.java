package org.example.plsql.types;

public abstract class ScalarType extends DataType {

    protected boolean nonNullable = false;

    public boolean isNonNullable() {
        return this.nonNullable;
    }

    public void setNonNullable(boolean nonNullable) {
        this.nonNullable = nonNullable;
    }
}
