package com.deencord.service;

import com.deencord.exception.UserOperationException;
import com.deencord.model.Bookmark;
import com.deencord.model.Lesson;
import com.deencord.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    void completeLesson_addsLessonToUsersCompletedSet() {
        UserService userService = new UserService();
        User user = new User("1", "alice");
        Lesson lesson = new Lesson("intro", "content", "SYSTEM");

        userService.completeLesson(user, lesson);

        assertTrue(user.getCompletedLessons().contains(lesson));
    }

    @Test
    void completeLesson_withNullLesson_throwsUserOperationException() {
        UserService userService = new UserService();
        User user = new User("1", "alice");

        assertThrows(UserOperationException.class, () -> userService.completeLesson(user, null));
    }

    @Test
    void bookmarkLesson_addsBookmarkForLesson() {
        UserService userService = new UserService();
        User user = new User("1", "alice");
        Lesson lesson = new Lesson("intro", "content", "SYSTEM");

        userService.bookmarkLesson(user, lesson);

        List<Bookmark> bookmarks = userService.getBookmarks(user);
        assertEquals(1, bookmarks.size());
        assertEquals(lesson, bookmarks.getFirst().getLesson());
    }

    @Test
    void getProgress_reflectsCompletedLessonRatio() {
        UserService userService = new UserService();
        User user = new User("1", "alice");
        userService.completeLesson(user, new Lesson("intro", "content", "SYSTEM"));

        double progress = userService.getProgress(user, 4);

        assertEquals(25.0, progress);
    }

    @Test
    void getBookmarks_withNullUser_throwsUserOperationException() {
        UserService userService = new UserService();

        assertThrows(UserOperationException.class, () -> userService.getBookmarks(null));
    }
}
