package com.deencord;

import com.deencord.exception.AppException;
import com.deencord.exception.KeywordSearchException;
import com.deencord.model.Bookmark;
import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import com.deencord.model.User;
import com.deencord.repository.KnowledgeRepository;
import com.deencord.service.KnowledgeService;
import com.deencord.service.SearchService;
import com.deencord.service.UserService;
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
        UserService userService = new UserService();

        try {
            knowledgeService.loadKnowledge();
            logger.info("Knowledge markdowns loaded successfully");
        } catch (AppException e) {
            logger.log(Level.SEVERE, "Failed to load knowledge base", e);
            System.out.println("Application couldn't start. Please contact support.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            User user = createUser(scanner);
            printHelp();
            runSearchLoop(scanner, knowledgeService, searchService, userService, user);
        }
        logger.info("Application finished.");
    }

    private static User createUser(Scanner scanner) {
        System.out.println("Enter your username: ");
        String username = "guest";
        try {
            username = scanner.nextLine().trim();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed reading the user input: ", e);
        }
        return new User(username, username); // id = username for now
    }

    private static void printHelp() {
        System.out.println("Commands: <keyword> to search | complete <lesson> | bookmark <lesson> | progress | bookmarks | help | exit");
    }

    private static void runSearchLoop(Scanner scanner, KnowledgeService knowledgeService, SearchService searchService, UserService userService, User user) {
        while (true) {
            System.out.print(">: ");
            String rawinput;
            try {
                rawinput = scanner.nextLine();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed reading the user input: ", e);
                return;
            }

            String input = rawinput.trim().toLowerCase();
            if (input.equals("exit")) {
                break;
            } else if (input.equals("help")) {
                printHelp();
            } else if (input.equals("progress")) {
                handleProgress(knowledgeService, userService, user);
            } else if (input.equals("bookmarks")) {
                handleListBookmarks(userService, user);
            } else if (input.startsWith("complete ")) {
                handleComplete(knowledgeService, userService, user, input.substring("complete ".length()));
            } else if (input.startsWith("bookmark ")) {
                handleBookmark(knowledgeService, userService, user, input.substring("bookmark ".length()));
            } else {
                handleKnowledgeComponentSearch(knowledgeService, searchService, input);
            }
        }
    }

    private static void handleKnowledgeComponentSearch(KnowledgeService knowledgeService, SearchService searchService, String rawInput) {
        try {
            String keyword = InputValidator.sanitizeSearchKeyword(rawInput);
            Iterator<KnowledgeComponent> iterator = knowledgeService.getIterator();
            List<KnowledgeComponent> results = searchService.search(iterator, keyword);
            printKnowledgeComponentSearchResult(results);
        } catch (AppException e) {
            logger.log(Level.WARNING, "Search failed for keyword " + rawInput, e);
            System.out.println("Invalid search: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during search", e);
            System.out.println("Something went wrong  with the search. Try again!");
        }
    }

    private static void handleComplete(KnowledgeService knowledgeService, UserService userService, User user, String lessonName) {
        try {
            Lesson lesson = knowledgeService.findLessonByName(lessonName);
            if (lesson == null) {
                throw new KeywordSearchException("Couldn't find the lesson: " + lessonName);
            }
            userService.completeLesson(user, lesson);
            System.out.println("Marked complete: " + lesson.getName());
        } catch (AppException e) {
            logger.log(Level.WARNING, "Failed to complete lesson " + lessonName, e);
            System.out.println("Couldn't complete that lesson: " + e.getMessage());
        }
    }

    private static void handleBookmark(KnowledgeService knowledgeService, UserService userService, User user, String lessonName) {
        try {
            Lesson lesson = knowledgeService.findLessonByName(lessonName);
            if (lesson == null) {
                throw new KeywordSearchException("Couldn't find the lesson: " + lessonName);
            }

            userService.bookmarkLesson(user, lesson);
            System.out.println("Bookmarked: " + lesson.getName());
        } catch (AppException e) {
            logger.log(Level.WARNING, "Failed to bookmark lesson " + lessonName, e);
            System.out.println("Couldn't bookmark that lesson: " + e.getMessage());
        }
    }

    private static void handleProgress(KnowledgeService knowledgeService, UserService userService, User user) {
        int totalLessons = knowledgeService.countLessons();
        double progress = userService.getProgress(user, totalLessons);
        System.out.printf("%s: %.1f%% complete (%d/%d lessons)%n", user.getUsername(), progress, user.getCompletedLessons().size(), totalLessons);
    }

    private static void handleListBookmarks(UserService userService, User user) {
        List<Bookmark> bookmarks = userService.getBookmarks(user);
        if (bookmarks.isEmpty()) {
            System.out.println("No bookmarks yet.");
            return;
        }
        System.out.println("----------------------");
        for (Bookmark bookmark : bookmarks) {
            System.out.println(bookmark.getLesson().getName() + " (saved " + bookmark.getCreatedAt() + ")");
        }
        System.out.println("----------------------");
    }


    private static void printKnowledgeComponentSearchResult(List<KnowledgeComponent> results) {
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