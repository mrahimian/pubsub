package subscribe;

import data.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class JsonDecoder implements Decoder{

    @Override
    public Optional<Data> decode(String msg) {
        Data data = null;
        try {
            JSONObject json = new JSONObject(msg);
            data = new Data((String)(json.get("message")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return data != null ? Optional.of(data) : Optional.empty();
    }
}
