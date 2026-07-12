package com.deencord.service;

import com.deencord.model.Category;
import com.deencord.model.KnowledgeComponent;
import com.deencord.repository.KnowledgeRepository;

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
        if (knowledgeBase == null) {
            throw new IllegalStateException(
                    "Knowledge base not loaded"
            );
        }

        knowledgeBase.display();
    }

    public Iterator<KnowledgeComponent> getIterator() {
        if (knowledgeBase == null) {
            throw new IllegalStateException("Knowledge base not loaded");
        }

        return knowledgeBase.iterator();
    }
}
