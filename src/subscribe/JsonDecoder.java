package subscribe;

import com.google.gson.Gson;
import data.Data;

public class JsonDecoder implements Decoder{
    @Override
    public Data decode(String data) {

        Gson gson = new Gson();

        return gson.fromJson(data, Data.class);

    }
}
