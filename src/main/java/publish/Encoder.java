package publish;

import data.Data;

public interface Encoder {

    /**
     * Encode data and deliver to CommunicationProtocol
     * @param data
     * @return
     */
    String encode(Data data);
}
