package org.example.oracle.ast;

public class RowTypeAttribute extends TypeToken {

    private Identifier name;

    public RowTypeAttribute(Identifier name) {
        this.name = name;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
