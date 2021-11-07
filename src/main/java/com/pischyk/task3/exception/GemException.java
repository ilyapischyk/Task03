package com.pischyk.task3.exception;

public class GemException extends Exception {
    public GemException() {
    }

    public GemException(String message) {
        super(message);
    }

    public GemException(String message, Throwable cause) {
        super(message, cause);
    }

    public GemException(Throwable cause) {
        super(cause);
    }
}
