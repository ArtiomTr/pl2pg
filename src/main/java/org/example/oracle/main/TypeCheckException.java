package org.example.oracle.main;

public class TypeCheckException extends Exception {

    public TypeCheckException(Throwable cause) {
        super("Type error", cause);
    }
}
