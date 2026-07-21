package com.deencord.service;

import com.deencord.repository.KnowledgeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class KnowledgeServiceTest {

    private KnowledgeService buildLoadedService(Path root) throws IOException {
        Path javaFolder = root.resolve("java");
        Files.createDirectory(javaFolder);
        Files.writeString(javaFolder.resolve("intro.md"), "# Intro content");
        Files.writeString(javaFolder.resolve("generics.md"), "# Generics content");
        Files.writeString(root.resolve("standalone.md"), "# Standalone content");

        KnowledgeService service = new KnowledgeService(new KnowledgeRepository(root.toString()));
        service.loadKnowledge();
        return service;
    }

    @Test
    void countLessons_countsOnlyLessons_notCategories(@TempDir Path tempDir) throws IOException {
        KnowledgeService service = buildLoadedService(tempDir);

        assertEquals(3, service.countLessons());
    }

    @Test
    void countLessons_beforeLoad_throwsIllegalStateException() {
        KnowledgeService service = new KnowledgeService(new KnowledgeRepository("irrelevant"));

        assertThrows(IllegalStateException.class, service::countLessons);
    }
}
