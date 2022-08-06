package ir.jibit.dumb.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LoggerUtil {
    public static Logger getLogger(String className) {

        FileHandler fileHandler ;
        Logger logger = null;
        try {
            fileHandler = new FileHandler("app.log", true);
            logger = Logger.getLogger(className);
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("Unable to open log file");
        }
        return logger;
    }
}
