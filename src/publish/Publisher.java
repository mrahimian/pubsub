package publish;

import java.io.IOException;

/**
 * Publisher exists to publish(write) data on an external resource such as File, Database, Network ...
 */
public interface Publisher {

    /**
     * create instance of IO resource (File, Connection ...)
     */
    void createInstance();

    /**
     * it writes the data on external resource
     * @throws IOException
     */
    void publish() throws IOException;

}
