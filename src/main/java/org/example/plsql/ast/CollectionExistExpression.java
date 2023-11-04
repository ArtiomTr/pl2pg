package org.example.plsql.ast;

public class CollectionExistExpression extends Expression {

    private Identifier collection;
    private Expression index;

    public CollectionExistExpression(Identifier collection, Expression index) {
        this.collection = collection;
        this.index = index;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof CollectionExistExpression)) {
            return false;
        }

        CollectionExistExpression other = (CollectionExistExpression) node;

        return this.collection.areEqual(other.collection) && this.index.areEqual(other.index);
    }
}
