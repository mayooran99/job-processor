package com.rest.jobprocessor.logger;

import org.slf4j.Logger;

/**
 * Class to get SLF4J logger instances for logging purposes throughout the application
 */
public class LoggerFactory {

    static {
        System.setProperty("logback.configurationFile", System.getProperty("user.dir") + "\\" + "logbackConfig.xml");
    }

    private LoggerFactory() {

    }

    /**
     * Get an instance of Logback logger for the specified class
     *
     * @param className Name of the class which requires the logger
     * @return logger instance for the specified class
     */
    public static Logger getLogger(Class className) {
        return org.slf4j.LoggerFactory.getLogger(className);
    }
}