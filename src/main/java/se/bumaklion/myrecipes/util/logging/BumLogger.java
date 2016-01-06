package main.java.se.bumaklion.myrecipes.util.logging;

import org.apache.log4j.Logger;

public class BumLogger {

    private static Logger logger = Logger.getRootLogger();

    public static void debug(Object message) {
        logger.debug(message);
    }

    public static void error(Object message) {
        logger.error(message);
    }

    public static void error(Exception e) {
        logger.error(e);
        e.printStackTrace();
    }
}
