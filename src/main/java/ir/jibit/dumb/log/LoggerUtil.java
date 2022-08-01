package ir.jibit.dumb.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LoggerUtil {
    public static Logger getLogger(String className) throws IOException {

        FileHandler fileHandler ;
        try {
            fileHandler = new FileHandler("app.log", true);
            var logger = Logger.getLogger(className);
            logger.addHandler(fileHandler);

            return logger;
        } catch (IOException e) {
            throw new IOException("Unable to open log file");
        }
    }
}
