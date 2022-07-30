package log;

import publish.Publisher;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SysLogger {
    private static SysLogger sysLogger;
    private final Logger logger;

    private SysLogger(String className) throws IOException {

        FileHandler fileHandler ;
        try {
            fileHandler = new FileHandler("app.log", true);
            logger = Logger.getLogger(className);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new IOException("Unable to open log file");
        }
    }

    public static SysLogger getInstance(String className) throws IOException {
        if (sysLogger == null){
            sysLogger = new SysLogger(className);
        }
        return sysLogger;
    }
    public Logger getLogger() {
        return logger;
    }
}
