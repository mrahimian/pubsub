package publish;

public interface CommunicationProtocol {
    /**
     * Get data from the encoder and write data to the resource
     * @return
     */
    void writeData(String msg);
}
