package com.deencord.iterator;

import com.deencord.model.Category;
import com.deencord.model.KnowledgeComponent;
import com.deencord.model.Lesson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepthFirstKnowledgeIteratorTest {

    @Test
    void iterator_traversesEmptyCategory_returnsOnlyItself() {
        Category root = new Category("root");

        Iterator<KnowledgeComponent> it = root.iterator();
        List<String> visited = collect(it);

        assertEquals(List.of("root"), visited);
    }

    @Test
    void iterator_traversesNestedStructure_inDepthFirstOrder() {
        Category root = new Category("root");
        Category child = new Category("child");
        Lesson lessonA = new Lesson("lessonA", "content", "SYSTEM");
        Lesson lessonB = new Lesson("lessonB", "content", "SYSTEM");

        child.add(lessonA);
        root.add(child);
        root.add(lessonB);

        List<String> visited = collect(root.iterator());

        assertEquals(List.of("root", "child", "lessonA", "lessonB"), visited);
    }

    @Test
    void iterator_onLessonLeaf_hasNoChildren() {
        Lesson lesson = new Lesson("solo", "content", "SYSTEM");
        Iterator<KnowledgeComponent> it = lesson.iterator();

        assertFalse(it.hasNext());
    }

    private List<String> collect(Iterator<KnowledgeComponent> iterator) {
        List<String> names = new ArrayList<>();
        while (iterator.hasNext()) {
            names.add(iterator.next().getName());
        }
        return names;
    }
}
