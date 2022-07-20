package subscribe;

import mm.XMsg;
import publish.Data;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * A mm.Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public interface Subscriber {

    /**
     * pass anny message to consumer method in an async manner
     */
    void subscribe(Consumer<Data> consumer);

    /**
     * read a message in blocking matter
     */
    Data subscribe();

}
