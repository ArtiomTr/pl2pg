package org.example.postgres.generation;

public class GenerateException extends Exception {

    public GenerateException(Throwable cause) {
        super("Failed to generate", cause);
    }

}
