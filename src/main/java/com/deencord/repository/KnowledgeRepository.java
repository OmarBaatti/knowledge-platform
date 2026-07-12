package com.deencord.repository;

import com.deencord.exception.KnowledgeLoadException;
import com.deencord.factory.KnowledgeFactory;
import com.deencord.model.Category;
import com.deencord.model.Lesson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class KnowledgeRepository {
    private final Path knowledgeDirectory;

    public KnowledgeRepository(String directoryPath) {
        this.knowledgeDirectory = Paths.get(directoryPath);
    }

    public Category loadKnowledgeBase() {
        try {
            if(!Files.exists(knowledgeDirectory)) {
                throw new IOException("Knowledge directory does not exist");
            }
            return loadCategory(knowledgeDirectory);
        } catch (IOException e) {
            throw new KnowledgeLoadException("Failed to load knowledge base", e);
        }
    }

    private Category loadCategory(Path path) throws IOException {
        Category category = KnowledgeFactory.createCategory(path.getFileName().toString());


        try (Stream<Path> files = Files.list(path)) {
            files.forEach(file -> {
                try {
                    if (Files.isDirectory(file)) {
                        category.add(loadCategory(file));
                    } else if (file.toString().endsWith(".md")) {
                        category.add(loadLesson(file));
                    } else {
                        // Ignore unsupported file types
                    }
                } catch (IOException e) {
                    throw new KnowledgeLoadException("Failed Loading: " + file.getFileName(), e);
                }
            });
        }

        return category;
    }

    private Lesson loadLesson(Path file) throws IOException {
        String title = file
                .getFileName()
                .toString()
                .replace(".md", "");

        String content = Files.readString(file);

        return KnowledgeFactory.createLesson(title, content, "SYSTEM"); // CHANGE AUTHOR

    }
}
