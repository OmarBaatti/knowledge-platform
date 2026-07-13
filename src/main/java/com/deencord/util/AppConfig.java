package com.deencord.util;

import java.util.logging.Logger;

public final class AppConfig {
    private static final Logger logger = LoggerConfig.getLogger();
    private static final String KNOWLEDGE_PATH_ENV = "DEENCORD_KNOWLEDGE_PATH";
    private static final String DEFAULT_KNOWLEDGE_PATH = "src/main/resources/knowledge";

    private AppConfig() {}
    public static String getKnowledgePath() {
        String envPath = System.getenv(KNOWLEDGE_PATH_ENV);

        if (envPath != null && !envPath.isBlank()) {
            logger.info("Using knowledge path from environment variable.");
            return envPath;
        }

        logger.info("No " + KNOWLEDGE_PATH_ENV + " set, using default path.");
        return DEFAULT_KNOWLEDGE_PATH;
    }
}
