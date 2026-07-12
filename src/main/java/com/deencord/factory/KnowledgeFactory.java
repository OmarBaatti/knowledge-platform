package com.deencord.factory;

import com.deencord.exception.InvalidKnowledgeException;
import com.deencord.model.Category;
import com.deencord.model.Lesson;


/*
public enum KnowledgeType {
    LESSON,
    CATEGORY
}
// In the future I could use this type to centralize Objects fabrication in 1 function create()
*/
public class KnowledgeFactory {
    public static Category createCategory(String name) {
        validateName(name);
        return new Category(name);
    }
    public static Lesson createLesson(String title, String markdownContent, String author) {
        validateName(title);
        if(markdownContent == null || markdownContent.isBlank()) {
            throw new InvalidKnowledgeException("Lesson cannot be empty");
        }
        return new Lesson(title, markdownContent, author);
    }

    private static void validateName(String name) {
        if(name == null || name.isBlank()) {
            throw new InvalidKnowledgeException("Name cannot be empty");
        }
    }
}
