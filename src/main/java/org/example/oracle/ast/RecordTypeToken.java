package org.example.oracle.ast;

import java.util.List;

public class RecordTypeToken extends TypeToken {

    public static class FieldDefinition extends Node {

        private Identifier name;

        private TypeToken typeToken;

        private Expression initialValue;

        public FieldDefinition(Identifier name, TypeToken typeToken, Expression initialValue) {
            this.name = name;
            this.typeToken = typeToken;
            this.initialValue = initialValue;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    private List<FieldDefinition> fields;

    public RecordTypeToken(List<FieldDefinition> fields) {
        this.fields = fields;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
