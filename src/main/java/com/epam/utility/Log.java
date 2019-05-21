package com.epam.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static Logger logger = LoggerFactory.getLogger(Log.class);

    public static void startTestCase(String testCaseID) {

        logger.info("****************************************************************************************");
        logger.info("****************************************************************************************");
        logger.info("**********************                  " + testCaseID + "        **********************");
        logger.info("****************************************************************************************");
        logger.info("****************************************************************************************");

    }

    public static void endTestCase(String testCaseID) {
        logger.info("**********************        " + testCaseID + " -END-" + "       **********************");
        logger.info("****************************************************************************************");
    }


    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

}
