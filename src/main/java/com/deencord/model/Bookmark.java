package com.deencord.model;

import java.time.LocalDateTime;

public class Bookmark {
    private Lesson lesson;
    private LocalDateTime createdAt;

    public Bookmark(Lesson lesson) {
        this.lesson = lesson;
        this.createdAt = LocalDateTime.now();
    }

    public Lesson getLesson() {
        return lesson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
