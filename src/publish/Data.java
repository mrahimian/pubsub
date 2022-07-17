package publish;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data contains information that should be written on File, Network ...
 */
public class Data implements Serializable {

    private String id;

    private String message;

    public Data(String message) {
        this.message = message;
        id = UUID.randomUUID().toString();

    }
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((Data)obj).id);
    }
}