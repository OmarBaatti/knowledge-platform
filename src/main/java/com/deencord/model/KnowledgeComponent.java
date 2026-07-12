package com.deencord.model;

import java.util.Iterator;

public interface KnowledgeComponent {
    String getName();
    void display();
    Iterator<KnowledgeComponent> iterator();
}
