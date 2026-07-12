package com.deencord.exception;

public class KnowledgeLoadException extends RuntimeException {
    public KnowledgeLoadException (String message) {
        super(message);
    }
    public KnowledgeLoadException (String message, Throwable cause) {
        super(message, cause);
    }
}
