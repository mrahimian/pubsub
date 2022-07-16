package subscribe;

/**
 * A Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public interface Subscriber {

    /**
     * Read data
     */
    void subscribe();
}
