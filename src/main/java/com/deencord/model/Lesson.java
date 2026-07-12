package com.deencord.model;

import java.util.Collections;
import java.util.Iterator;

public class Lesson implements KnowledgeComponent{
    private String title;
    private String markdownContent;
    private String author;

    public Lesson(String title, String markdownContent, String author) {
        this.title = title;
        this.markdownContent = markdownContent;
        this.author = author;
    }

    public String getMarkdownContent() {
        return markdownContent;
    }

    public String getAuthor() {
        return author;
    }

    public void updateContent(String newContent) {
        markdownContent = newContent;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public void display(){
        System.out.println("# " + title);
        System.out.println(markdownContent);
    }

    @Override
    public Iterator<KnowledgeComponent> iterator(){
        return Collections.emptyIterator();
    }
}
