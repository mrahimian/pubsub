package subscribe;

import data.Data;
import log.SysLogger;
import org.json.JSONObject;
import org.json.JSONException;
import publish.Publisher;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class JsonDecoder implements Decoder{

    private final Logger logger ;

    public JsonDecoder() throws IOException {
        logger = new SysLogger(Publisher.class.getName()).getLogger();
    }

    @Override
    public Optional<Data> decode(String msg) {
        Data data = null;
        try {
            JSONObject json = new JSONObject(msg);
            data = new Data((String)(json.get("message")));
        } catch (JSONException e) {
            logger.warning("Error while decoding message :\n" + e.getMessage());
        }

        return data != null ? Optional.of(data) : Optional.empty();
    }
}
