package subscribe;

import com.google.gson.Gson;
import data.Data;
import org.json.JSONObject;

public class JsonDecoder implements Decoder{

    @Override
    public Data decode(String data) {
        JSONObject json = new JSONObject(data);
        return new Data((String)(json.get("message")));
    }
}
