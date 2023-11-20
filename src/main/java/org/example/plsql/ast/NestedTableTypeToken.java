package org.example.plsql.ast;

public class NestedTableTypeToken extends TypeToken {

    private TypeToken dataType;

    public NestedTableTypeToken(TypeToken dataType) {
        this.dataType = dataType;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("not implemented");
    }
}
