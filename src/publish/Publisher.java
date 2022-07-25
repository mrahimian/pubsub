package publish;

import data.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Publisher exists to publish(write) data on an external resource such as File, Database, Network ...
 */
public class Publisher {
    private final CommunicationProtocol cp;
    private final Encoder encoder;

    public Publisher(CommunicationProtocol cp, Encoder encoder) {
        this.cp = cp;
        this.encoder = encoder;
    }

    /**
     * it writes the data on external resource
     */
    void publish(Data data) {
        try {
            String msg = encoder.encode(data);
            cp.writeData(msg);
            System.out.println(msg);
        }catch (Exception e){

        }
    };

}
