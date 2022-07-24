package subscribe;

import data.Data;

public interface Decoder {
    /**
     * get data as a string and return it as a Data
     * @param data
     * @return Data
     */
    Data decode(String data);
}
