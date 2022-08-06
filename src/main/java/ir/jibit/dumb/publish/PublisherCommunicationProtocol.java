package ir.jibit.dumb.publish;

import java.io.IOException;

public interface PublisherCommunicationProtocol {
    /**
     * Get data from the encoder and write data to the resource
     * @param msg Will be written in communication protocol
     * @throws Exception - In case of an exception while writing data
     */
    void writeData(String msg) throws Exception;


    void shutDown() throws IOException;
}
