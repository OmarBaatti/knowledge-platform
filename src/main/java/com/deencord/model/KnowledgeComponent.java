package com.deencord.model;

import java.util.Iterator;

public interface KnowledgeComponent extends Iterable<KnowledgeComponent>{
    String getName();
    void display();

    Iterator<KnowledgeComponent> iterator();
}
