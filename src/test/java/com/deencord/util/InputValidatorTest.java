package com.deencord.util;

import com.deencord.exception.KeywordSearchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {
    @Test
    void sanitizeSearchKeyword_withValidInput_returnsTrimmed() {
        String result = InputValidator.sanitizeSearchKeyword("  java  ");
        assertEquals("java", result);
    }

    @Test
    void sanitizeSearchKeyword_withNull_throwsException() {
        assertThrows(KeywordSearchException.class, () -> InputValidator.sanitizeSearchKeyword(null));
    }

    @Test
    void sanitizeSearchKeyword_withBlank_throwsException() {
        assertThrows(KeywordSearchException.class, () -> InputValidator.sanitizeSearchKeyword("   "));
    }

    @Test
    void sanitizeSearchKeyword_withOnlyInvalidCharacters_throwsException() {
        assertThrows(KeywordSearchException.class, () -> InputValidator.sanitizeSearchKeyword("!!!@@@###"));
    }

    @Test
    void sanitizeSearchKeyword_stripsDisallowedCharacters() {
        String result = InputValidator.sanitizeSearchKeyword("java<script>");
        assertFalse(result.contains("<"));
        assertFalse(result.contains(">"));
    }

    @Test
    void sanitizeSearchKeyword_exceedingMaxLength_throwsException() {
        String longInput = "a".repeat(200);
        assertThrows(KeywordSearchException.class, () -> InputValidator.sanitizeSearchKeyword(longInput));
    }
}
