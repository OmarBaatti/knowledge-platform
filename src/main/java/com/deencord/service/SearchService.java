package com.deencord.service;

import com.deencord.model.KnowledgeComponent;
import com.deencord.util.LoggerConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class SearchService {
    private static final Logger logger = LoggerConfig.getLogger();
    public List<KnowledgeComponent> search(Iterator<KnowledgeComponent> iterator, String keyword) {
        logger.info("Searching knowledge for: " + keyword);
        List<KnowledgeComponent> results = new ArrayList<>();

        while(iterator.hasNext()) {
            KnowledgeComponent component = iterator.next();
            if(component.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(component);
            }
        }

        logger.info("Search completed. Results found: " + results.size());
        return  results;
    }
}
