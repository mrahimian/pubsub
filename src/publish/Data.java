package publish;

import java.io.Serializable;

public class Data implements Serializable {

    String message;

    public Data(String message) {
        this.message = message;
        }
    public String getMessage() {
        return message;
    }

}