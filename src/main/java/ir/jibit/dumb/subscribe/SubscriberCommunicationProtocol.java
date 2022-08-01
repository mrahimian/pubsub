package ir.jibit.dumb.subscribe;

public interface SubscriberCommunicationProtocol {
    /**
     * Get data from the resource and deliver to decoder
     * @return
     */
    String readData();
}
