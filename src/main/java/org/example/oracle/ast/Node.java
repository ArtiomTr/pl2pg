package org.example.oracle.ast;

public abstract class Node {

    public abstract <T> T accept(Visitor<T> visitor);
}
