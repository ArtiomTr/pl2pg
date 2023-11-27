package org.example.plsql.types;

public abstract class Type {
    
    public abstract boolean canCastTo(Type targetType);
}
