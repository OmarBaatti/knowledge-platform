package com.deencord.service;

import com.deencord.model.KnowledgeComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchService {
    public List<KnowledgeComponent> search(Iterator<KnowledgeComponent> iterator, String keyword) {
        List<KnowledgeComponent> results = new ArrayList<>();

        while(iterator.hasNext()) {
            KnowledgeComponent component = iterator.next();
            if(component.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(component);
            }
        }

        return  results;
    }
}
