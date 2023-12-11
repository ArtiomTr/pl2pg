package org.example.oracle.ast;

public class CollectionExistExpression extends Expression {

    private Identifier collection;
    private Expression index;

    public CollectionExistExpression(Identifier collection, Expression index) {
        this.collection = collection;
        this.index = index;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Identifier getCollection() {
        return collection;
    }

    public Expression getIndex() {
        return index;
    }
}
