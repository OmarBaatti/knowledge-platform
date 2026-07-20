package com.deencord.model;

import com.deencord.iterator.DepthFirstKnowledgeIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Category implements KnowledgeComponent {
    private String name;
    private List<KnowledgeComponent> children;

    public Category(String name) {
       this.name = name;
       this.children = new ArrayList<>();
    }

    public void add(KnowledgeComponent component) {
        children.add(component);
    }

    public void remove(KnowledgeComponent component) {
        children.remove(component);
    }

    public List<KnowledgeComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void display(){
        System.out.println("Category: " + name);

        for (KnowledgeComponent child : children) {
            child.display();
        }
    }

    @Override
    public Iterator<KnowledgeComponent> iterator(){
        return new DepthFirstKnowledgeIterator(this);
    }
}
