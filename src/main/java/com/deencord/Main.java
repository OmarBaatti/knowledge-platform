package com.deencord;

import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import com.deencord.repository.KnowledgeRepository;
import com.deencord.service.KnowledgeService;
import com.deencord.service.SearchService;
import com.deencord.util.LoggerConfig;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = LoggerConfig.getLogger();
    public static void main(String[] args) {
        logger.info("Starting Deencord Knowledge Platform...");

        String knowledgePath = "src/main/resources/knowledge";
        KnowledgeRepository repository = new KnowledgeRepository(knowledgePath);
        KnowledgeService knowledgeService = new KnowledgeService(repository);
        SearchService searchService = new SearchService();

        try {
            knowledgeService.loadKnowledge();
            logger.info("Knowledge markdowns loaded successfully");

            // knowledgeService.displayKnowledge();

            searchKeyword(knowledgeService, searchService, "java");
            searchKeyword(knowledgeService, searchService, "collections");

        } catch (Exception e) {
            logger.severe("Application Error: " + e.getMessage());
        }

        logger.info("Application finished.");
    }

    public static void searchKeyword(KnowledgeService knowledgeService, SearchService searchService, String keyword) {
        Iterator<KnowledgeComponent> iterator = knowledgeService.getIterator();
        List<KnowledgeComponent> results = searchService.search(iterator, keyword);

        if (!results.isEmpty()) {
            System.out.println("----------------------");
        }
        for (KnowledgeComponent result : results) {
            System.out.println("Found: " + result.getName());
            if (result instanceof Lesson lesson) {
                System.out.println("Content:\n" + lesson.getMarkdownContent());
            } else {
                System.out.println("Type: Category");
            }
            System.out.println("----------------------");
        }
    }
}