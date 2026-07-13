package com.deencord.util;

import com.deencord.exception.InvalidKnowledgeException;
import com.deencord.exception.KeywordSearchException;

public final class InputValidator {
    private static final int MAX_KEYWORD_LENGTH = 100;
    private InputValidator() {}
    public static String sanitizeSearchKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new KeywordSearchException("Search keyword cannot be empty");
        }

        String trimmed = keyword.trim();
        if (trimmed.length() > MAX_KEYWORD_LENGTH) {
            throw new KeywordSearchException("Search keyword is too long");
        }

        String sanitized = trimmed.replaceAll("[^a-zA-Z0-9 _-]", "");

        if (sanitized.isBlank()) {
            throw new KeywordSearchException("Search keyword contains no valid characters");
        }

        return sanitized;
    }
}
