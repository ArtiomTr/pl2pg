package org.example.plsql.ast;

public class VaryingArrayTypeToken extends TypeToken {

    private NumberLiteral sizeLimit;
    private TypeToken dataType;

    public VaryingArrayTypeToken(NumberLiteral sizeLimit, TypeToken dataType) {
        this.sizeLimit = sizeLimit;
        this.dataType = dataType;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
