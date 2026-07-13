package com.deencord;

import com.deencord.exception.AppException;
import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import com.deencord.repository.KnowledgeRepository;
import com.deencord.service.KnowledgeService;
import com.deencord.service.SearchService;
import com.deencord.util.AppConfig;
import com.deencord.util.InputValidator;
import com.deencord.util.LoggerConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = LoggerConfig.getLogger();
    public static void main(String[] args) {
        logger.info("Starting Deencord Knowledge Platform...");

        String knowledgePath = AppConfig.getKnowledgePath();
        KnowledgeRepository repository = new KnowledgeRepository(knowledgePath);
        KnowledgeService knowledgeService = new KnowledgeService(repository);
        SearchService searchService = new SearchService();

        try {
            knowledgeService.loadKnowledge();
            logger.info("Knowledge markdowns loaded successfully");
        } catch (AppException e) {
            logger.log(Level.SEVERE, "Failed to load knowledge base", e);
            System.out.println("Application couldn't start. Please contact support.");
            return;
        }
        runSearchLoop(knowledgeService, searchService);
        logger.info("Application finished.");
    }
    private static void runSearchLoop(KnowledgeService knowledgeService, SearchService searchService) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter a search keyword (or 'exit' to quit): ");
                String input = scanner.nextLine();

                if (input.trim().equalsIgnoreCase("exit")) {
                    break;
                }

                handleSearch(knowledgeService, searchService, input);
            }
        }
    }

    private static void handleSearch(KnowledgeService knowledgeService, SearchService searchService, String rawInput) {
        try {
            String keyword = InputValidator.sanitizeSearchKeyword(rawInput);
            Iterator<KnowledgeComponent> iterator = knowledgeService.getIterator();
            List<KnowledgeComponent> results = searchService.search(iterator, keyword);
            printResults(results);
        } catch (AppException e) {
            logger.log(Level.WARNING, "Search failed for keyword " + rawInput, e);
            System.out.println("Invalid search: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during search", e);
            System.out.println("Something went wrong  with the search. Try again!");
        }
    }
    public static void printResults(List<KnowledgeComponent> results) {
        if (results.isEmpty()) {
            System.out.println("No results found!");
            return;
        }

        System.out.println("----------------------");
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