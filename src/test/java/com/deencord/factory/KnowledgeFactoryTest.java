package com.deencord.factory;

import com.deencord.exception.InvalidKnowledgeException;
import com.deencord.model.Category;
import com.deencord.model.Lesson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KnowledgeFactoryTest {

    @Test
    void createCategory_withValidName_returnsCategory() {
        Category category = KnowledgeFactory.createCategory("java");
        assertEquals("java", category.getName());
    }

    @Test
    void createCategory_withBlankName_throwsException() {
        assertThrows(InvalidKnowledgeException.class, () -> KnowledgeFactory.createCategory("  "));
    }

    @Test
    void createCategory_withNullName_throwsException() {
        assertThrows(InvalidKnowledgeException.class, () -> KnowledgeFactory.createCategory(null));
    }

    @Test
    void createLesson_withValidData_returnsLesson() {
        Lesson lesson = KnowledgeFactory.createLesson("intro", "# Hello", "SYSTEM");
        assertEquals("intro", lesson.getName());
        assertEquals("# Hello", lesson.getMarkdownContent());
    }

    @Test
    void createLesson_withEmptyContent_throwsException() {
        assertThrows(InvalidKnowledgeException.class, () -> KnowledgeFactory.createLesson("intro", "  ", "SYSTEM"));
    }

    @Test
    void createLesson_withBlankTitle_throwsException() {
        assertThrows(InvalidKnowledgeException.class, () -> KnowledgeFactory.createLesson("", "content", "SYSTEM"));
    }


}
