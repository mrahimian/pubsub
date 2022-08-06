package ir.jibit.dumb.subscribe;

import ir.jibit.dumb.data.Data;
import ir.jibit.dumb.log.LoggerUtil;
import ir.jibit.dumb.publish.Publisher;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;
import java.util.logging.Logger;

public class JsonDecoder implements Decoder{

    private final Logger logger ;

    public JsonDecoder() {
        logger = LoggerUtil.getLogger(Publisher.class.getName());
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
