package ir.jibit.dumb.subscribe;

public interface SubscriberCommunicationProtocol {
    /**
     * Get data from the resource and deliver to decoder
     * @return one message
     * @throws Exception - In case of occurring any exception
     */
    String readData() throws Exception;
}
