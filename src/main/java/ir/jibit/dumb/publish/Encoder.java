package ir.jibit.dumb.publish;

import ir.jibit.dumb.data.Data;

public interface Encoder {

    /**
     * Encode data and deliver to PublisherCommunicationProtocol
     * @param data Data to be encoded
     * @return encoded data as a string
     * @throws Exception Any exception may be occurred during encoding
     */
    String encode(Data data) throws Exception;
}
