package ir.jibit.dumb.publish;

import ir.jibit.dumb.data.Data;

public interface Encoder {

    /**
     * Encode data and deliver to PublisherCommunicationProtocol
     * @param data
     * @return
     */
    String encode(Data data);
}
