package publish;

import data.Data;
import log.SysLogger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Publisher exists to publish(write) data on an external resource such as File, Database, Network ...
 */
public class Publisher {
    private final Logger logger;
    private final CommunicationProtocol cp;
    private final Encoder encoder;

    public Publisher(CommunicationProtocol cp, Encoder encoder) throws IOException {
        this.cp = cp;
        this.encoder = encoder;

        logger = SysLogger.getInstance(Publisher.class.getName()).getLogger();
    }

    /**
     * it writes the data on external resource
     */
    void publish(Data data) {
        try {
            String msg = encoder.encode(data);
            cp.writeData(msg);
        }catch (Exception e){
            logger.warning("Error while writing data on file.");
        }
    };

}
