package org.example.plsql.ast;

public class VariableDeclaration extends Declaration {

    private Identifier id;

    private TypeToken type;

    private Expression defaultValue;

    public VariableDeclaration(Identifier id, TypeToken type, Expression defaultValue) {
        this.id = id;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public Identifier getId() {
        return id;
    }

    public TypeToken getType() {
        return type;
    }

    public Expression getDefaultValue() {
        return defaultValue;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
