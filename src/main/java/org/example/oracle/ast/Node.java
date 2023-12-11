package org.example.oracle.ast;

import org.example.oracle.types.Type;

public abstract class Node {

    private Type resolvedType;

    public abstract <T> T accept(Visitor<T> visitor);

    public void setResolvedType(Type type) {
        this.resolvedType = type;
    }

    public Type getResolvedType() {
        return resolvedType;
    }
}
