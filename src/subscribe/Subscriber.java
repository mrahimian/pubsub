package subscribe;

import data.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * A mm.Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public class Subscriber {

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final CommunicationProtocol cp;
    private final Decoder decoder;


    public Subscriber(CommunicationProtocol cp, Decoder decoder) {
        this.cp = cp;
        this.decoder = decoder;
    }

    /**
     * pass any message to consumer method in an async manner
     */
    void subscribe(Consumer<Data> consumer){
        while (true){
            String msg = cp.readData();
            if(msg != null){
                consumer.accept(decoder.decode(msg));
            }
        }
    }

    /**
     * read a message in blocking matter
     */
    Data subscribe(){

        String msg = cp.readData();

        if(msg != null){
            return decoder.decode(msg);
        }
        while (msg == null){
            msg = cp.readData();
        }
        return decoder.decode(msg);
    }

    /**
     * read a message in a blocking matter with respect to the given timeout
     * @param timeout
     * @return
     */
    Data subscribe(int timeout){
        return null;

    }

}
