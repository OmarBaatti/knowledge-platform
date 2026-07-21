package com.deencord.model;

import java.util.*;

public class User {
    private String id;
    private String username;
    private Set<Lesson> completedLessons;
    private List<Bookmark> bookmarks;

    public User(String id, String username) {
        this.id = id;
        this.username = username.isBlank() ? "guest" : username;
        this.completedLessons = new HashSet<>();
        this.bookmarks = new ArrayList<>();
    }

    public void completeLesson(Lesson lesson) {
        completedLessons.add(lesson);
    }

    public Set<Lesson> getCompletedLessons() {
        return completedLessons;
    }

    public void addBookmark (Bookmark bookmark) {
        bookmarks.add(bookmark);
    }

    public List<Bookmark> getBookmarks() {
        return Collections.unmodifiableList(bookmarks);
    }

    public double getProgress(int totalLessons) {
        if (totalLessons == 0) {
            return 100;
        }
        return (completedLessons.size() * 100.0) / totalLessons;
    }

    public String getUsername() {
        return username;
    }
}
