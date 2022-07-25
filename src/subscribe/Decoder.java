package subscribe;

public interface Decoder {
    /**
     * get data as a string and return it as a Data
     *
     * @param data
     * @return Data
     */
    <T> T decode(String data);
}
