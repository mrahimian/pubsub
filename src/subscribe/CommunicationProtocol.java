package subscribe;

public interface CommunicationProtocol {
    /**
     * Get data from the resource and deliver to decoder
     * @return
     */
    String getData();
}
