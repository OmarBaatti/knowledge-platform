package com.deencord.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String id;
    private String username;
    private Set<Lesson> completedLessons;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
        this.completedLessons = new HashSet<>();
    }

    public void completeLesson(Lesson lesson) {
        completedLessons.add(lesson);
    }

    public Set<Lesson> getCompletedLessons() {
        return completedLessons;
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
