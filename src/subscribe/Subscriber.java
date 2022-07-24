package subscribe;

import data.Data;

import java.util.function.Consumer;

/**
 * A mm.Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public class Subscriber {

    CommunicationProtocol cp;
    Decoder decoder;


    public Subscriber(CommunicationProtocol cp, Decoder decoder) {
        this.cp = cp;
        this.decoder = decoder;
    }

    /**
     * pass any message to consumer method in an async manner
     */
    void subscribe(Consumer<Data> consumer){

    }

    /**
     * read a message in blocking matter
     */
    Data subscribe(){
        return decoder.decode(cp.getData());

    }

}
