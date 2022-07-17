package publish;

import java.io.IOException;

/**
 * Publisher exists to publish(write) data on an external resource such as File, Database, Network ...
 */
public interface Publisher {


    /**
     * it writes the data on external resource
     * @throws IOException
     */
    void publish(String message) throws IOException;

}
