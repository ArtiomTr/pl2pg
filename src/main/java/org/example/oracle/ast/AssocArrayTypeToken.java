package org.example.oracle.ast;

public class AssocArrayTypeToken extends TypeToken {

    private TypeToken rowType;

    private TypeToken indexType;

    public AssocArrayTypeToken(TypeToken rowType, TypeToken indexType) {
        this.rowType = rowType;
        this.indexType = indexType;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
