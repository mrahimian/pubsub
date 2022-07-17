package subscribe;

import publish.Data;

import java.util.ArrayList;

/**
 * A Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public interface Subscriber {

    /**
     * Read data
     */
    ArrayList<Data> subscribe();
}
