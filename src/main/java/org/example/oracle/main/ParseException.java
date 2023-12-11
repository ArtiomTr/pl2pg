package org.example.oracle.main;

public class ParseException extends Exception {

    public ParseException(Throwable cause) {
        super("Failed to parse", cause);
    }

}
