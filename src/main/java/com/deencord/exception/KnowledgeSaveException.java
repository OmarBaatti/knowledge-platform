package com.deencord.exception;

public class KnowledgeSaveException extends RuntimeException {
    public KnowledgeSaveException (String message) {
        super(message);
    }
    public KnowledgeSaveException (String message, Throwable cause) {
        super(message, cause);
    }
}