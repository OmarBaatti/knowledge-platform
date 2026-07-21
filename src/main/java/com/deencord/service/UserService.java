package com.deencord.service;

import com.deencord.model.Bookmark;
import com.deencord.model.Lesson;
import com.deencord.model.User;
import com.deencord.util.LoggerConfig;

import java.util.List;
import java.util.logging.Logger;

public class UserService {

    private static final Logger logger = LoggerConfig.getLogger();
    public void completeLesson(User user, Lesson lesson) {
        user.completeLesson(lesson);
        logger.info("User " + user.getUsername() + " completed lesson: " + lesson.getName());
    }

    public void bookmarkLesson(User user, Lesson lesson) {
        user.addBookmark(new Bookmark(lesson));
        logger.info("User " + user.getUsername() + " bookmarked lesson: " + lesson.getName());
    }

    public double getProgress(User user, int totalLessons) {
        return user.getProgress(totalLessons);
    }

    public List<Bookmark> getBookmarks(User user) {
        return user.getBookmarks();
    }
/*
    saveUserProgress()

    loadUserProgress()

    getCompletedLessons()

    calculateStatistics()
 */
}
