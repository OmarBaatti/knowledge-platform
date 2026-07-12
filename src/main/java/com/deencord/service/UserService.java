package com.deencord.service;

import com.deencord.model.Lesson;
import com.deencord.model.User;

public class UserService {
    public void completeLesson(User user, Lesson lesson) {
        user.completeLesson(lesson);
    }

    public double getProgress(User user, int totalLessons) {
        return user.getProgress(totalLessons);
    }

/*
    saveUserProgress()

    loadUserProgress()

    getCompletedLessons()

    calculateStatistics()
 */
}
