package org.example.plsql.ast;

public class ExpressionIdentifier extends Expression {

    private Identifier identifier;

    public ExpressionIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }


    @Override
    public boolean areEqual(Node node) {
        return (node instanceof ExpressionIdentifier) && ((ExpressionIdentifier) node).identifier.areEqual(this.identifier);
    }
}
