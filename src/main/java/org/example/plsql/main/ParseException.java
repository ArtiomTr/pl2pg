package org.example.plsql.main;

public class ParseException extends Exception {

    public ParseException(Exception cause) {
        super("Failed to parse", cause);
    }

}
