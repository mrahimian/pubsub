package log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SysLogger {
    private final Logger logger;

    public SysLogger(String className) throws IOException {

        FileHandler fileHandler ;
        try {
            fileHandler = new FileHandler("app.log", true);
            logger = Logger.getLogger(className);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new IOException("Unable to open log file");
        }
    }


    public Logger getLogger() {
        return logger;
    }
}
