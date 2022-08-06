package ir.jibit.dumb.publish;

import ir.jibit.dumb.data.Data;

public class JsonEncoder implements Encoder {

    /**
     * Encode data message
     * @param data To be encoded
     * @return Encoded data as string
     */
    @Override
    public String encode(Data data) throws NullPointerException {
        try {
            return "{\n\tmessage:\"" + data.getMessage() + "\"\n}";
        } catch (NullPointerException np) {
            throw new NullPointerException("Null Data\n" + np.getMessage());
        }
    }
}
