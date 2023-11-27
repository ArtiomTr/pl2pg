package org.example.plsql.ast;

public class TypeReferenceOperator extends TypeToken {

    private Identifier name;

    public TypeReferenceOperator(Identifier name) {
        this.name = name;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
