package ir.jibit.dumb.subscribe;

public interface Decoder {
    /**
     * get data as a string and return it as a Data
     * @param data to be decoded
     * @return decoded data
     */
    <T> T decode(String data);
}
