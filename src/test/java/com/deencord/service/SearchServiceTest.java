package com.deencord.service;

import com.deencord.model.Category;
import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceTest {
    @Test
    void search_isCaseInsensitive_andMatchesPartialNames() {
        Category root = new Category("root");
        root.add(new Lesson("JavaBasics", "content", "SYSTEM"));
        root.add(new Lesson("Python", "content", "SYSTEM"));

        SearchService searchService = new SearchService();
        List<KnowledgeComponent> results = searchService.search(root.iterator(), "java");

        assertEquals(1, results.size());
        assertEquals("JavaBasics", results.getFirst().getName());
    }

    @Test
    void search_withNoMatches_returnsEmptyList() {
        Category root = new Category("root");
        root.add(new Lesson("Python", "content", "SYSTEM"));

        SearchService searchService = new SearchService();
        List<KnowledgeComponent> results = searchService.search(root.iterator(), "rust");

        assertTrue(results.isEmpty());
    }
}
