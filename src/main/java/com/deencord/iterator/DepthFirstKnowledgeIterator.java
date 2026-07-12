package com.deencord.iterator;

import com.deencord.model.Category;
import com.deencord.model.KnowledgeComponent;

import java.util.List;
import java.util.Stack;

public class DepthFirstKnowledgeIterator implements KnowledgeIterator{
    private Stack<KnowledgeComponent> stack;

    public DepthFirstKnowledgeIterator(KnowledgeComponent root) {
        stack = new Stack<>();
        stack.push(root);
    }

    @Override
    public boolean hasNext(){
        return !stack.isEmpty();
    }

    @Override
    public KnowledgeComponent next() {
        KnowledgeComponent current = stack.pop();

        if (current instanceof Category category) {
            List<KnowledgeComponent> children = category.getChildren();

            for (int i = children.size() - 1; i >= 0; i--) {
                stack.push(children.get(i));
            }
        }

        return current;
    }
}
