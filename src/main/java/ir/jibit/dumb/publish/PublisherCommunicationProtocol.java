package ir.jibit.dumb.publish;

public interface PublisherCommunicationProtocol {
    /**
     * Get data from the encoder and write data to the resource
     * @return
     */
    void writeData(String msg);
}
