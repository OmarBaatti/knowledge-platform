package com.deencord.repository;

import com.deencord.exception.KnowledgeLoadException;
import com.deencord.model.Category;
import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class KnowledgeRepositoryTest {
    @Test
    void loadKnowledgeBase_withValidStructure_buildsCorrectTree(@TempDir Path tempDir) throws IOException {
        Path javaFolder = tempDir.resolve("java");
        Files.createDirectory(javaFolder);
        Files.writeString(javaFolder.resolve("intro.md"), "# Intro content");

        KnowledgeRepository repository = new KnowledgeRepository(tempDir.toString());
        Category root = repository.loadKnowledgeBase();

        assertEquals(1, root.getChildren().size());
        KnowledgeComponent child = root.getChildren().getFirst();
        assertEquals("java", child.getName());
        Category javaCategory = assertInstanceOf(Category.class, child);

        assertEquals(1, javaCategory.getChildren().size());
        Lesson introLesson = assertInstanceOf(Lesson.class, javaCategory.getChildren().getFirst());
        assertEquals("intro", introLesson.getName());
    }

    @Test
    void loadKnowledgeBase_withNonExistentDirectory_throwsShieldedException() {
        KnowledgeRepository repository = new KnowledgeRepository("this/does/not/exist");

        assertThrows(KnowledgeLoadException.class, repository::loadKnowledgeBase);
    }

    @Test
    void loadKnowledgeBase_ignoresNonMarkdownFiles(@TempDir Path tempDir) throws IOException {
        Files.writeString(tempDir.resolve("notes.txt"), "should be ignored");
        Files.writeString(tempDir.resolve("lesson.md"), "# Real lesson");

        KnowledgeRepository repository = new KnowledgeRepository(tempDir.toString());
        Category root = repository.loadKnowledgeBase();

        assertEquals(1, root.getChildren().size());
        assertEquals("lesson", root.getChildren().getFirst().getName());
    }
}
