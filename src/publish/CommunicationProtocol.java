package publish;

public interface CommunicationProtocol {
    /**
     * Get data from the resource and deliver to decoder
     * @return
     */
    void writeData(String msg);
}
