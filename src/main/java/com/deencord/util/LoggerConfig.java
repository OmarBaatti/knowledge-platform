package com.deencord.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerConfig {
    public static final Logger logger = Logger.getLogger("Deencord");

    static {
        ConsoleHandler handler = new ConsoleHandler() {
            {
                setOutputStream(System.out);
            }
        };


        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
        logger.setLevel(Level.ALL);

        logger.setUseParentHandlers(false);
    }

    public static Logger getLogger() {
        return logger;
    }
}
