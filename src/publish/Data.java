package publish;

import java.io.Serializable;

/**
 * Data contains information that should be written on File, Network ...
 */
public class Data implements Serializable {

    private String message;

    public Data(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}