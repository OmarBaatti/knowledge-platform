package com.deencord.service;

import com.deencord.model.Category;
import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import com.deencord.repository.KnowledgeRepository;
import com.deencord.util.InputValidator;

import java.util.Iterator;

public class KnowledgeService {
    private final KnowledgeRepository repository;
    private Category knowledgeBase;

    public KnowledgeService(KnowledgeRepository repository) {
        this.repository = repository;
    }

    public void loadKnowledge() {
        this.knowledgeBase = repository.loadKnowledgeBase();
    }

    public Category getKnowledgeBase() {
        return knowledgeBase;
    }

    public void displayKnowledge() {
        if (knowledgeBase == null) { throw new IllegalStateException("Knowledge base not loaded"); }

        knowledgeBase.display();
    }

    public Iterator<KnowledgeComponent> getIterator() {
        if (knowledgeBase == null) { throw new IllegalStateException("Knowledge base not loaded"); }

        return knowledgeBase.iterator();
    }

    public Lesson findLessonByName(String name) {
        if (knowledgeBase == null) { throw new IllegalStateException("Knowledge base not loaded"); }

        String keyword = InputValidator.sanitizeSearchKeyword(name).trim();
        Iterator<KnowledgeComponent> iterator = knowledgeBase.iterator();
        while (iterator.hasNext()) {
            KnowledgeComponent component = iterator.next();
            if (component instanceof Lesson lesson && lesson.getName().equalsIgnoreCase(keyword)) {
                return lesson;
            }
        }
        return null;
    }

    public int countLessons() {
        if (knowledgeBase == null) { throw new IllegalStateException("Knowledge base not loaded"); }

        int count = 0;
        for (KnowledgeComponent component : knowledgeBase) {
            if (component instanceof Lesson) {
                count++;
            }
        }
        return count;
    }
}
