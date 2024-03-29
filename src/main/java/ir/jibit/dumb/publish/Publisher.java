package ir.jibit.dumb.publish;

import ir.jibit.dumb.data.Data;
import ir.jibit.dumb.log.LoggerUtil;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Publisher exists to publish(write) data on an external resource such as File, Database, Network ...
 */
public class Publisher {
    private final Logger logger;
    private final PublisherCommunicationProtocol cp;
    private final Encoder encoder;

    public Publisher(PublisherCommunicationProtocol cp, Encoder encoder) throws IOException {
        this.cp = cp;
        this.encoder = encoder;

        logger = LoggerUtil.getLogger(Publisher.class.getName());
    }

    /**
     * it writes the data on external resource
     */
    void publish(Data data) throws Exception {
        try {
            String msg = encoder.encode(data);
            cp.writeData(msg);
        } catch (Exception e) {
            logger.warning("Error while publishing data.");
            throw e;
        }
    }

    /**
     * Writing will be illegal after calling this function
     *
     * @throws IOException
     */
    void shutDown() throws IOException {
        try {
            cp.shutDown();
        } catch (IOException e) {
            String errorMessage = "Error while shutting down publishing";
            logger.warning(errorMessage);
            throw new IOException(e);
        }
    }

}
