package ir.jibit.dumb.publish;

import ir.jibit.dumb.data.Data;

public class JsonEncoder implements Encoder{

    @Override
    public String encode(Data data) {
        return "{\n\tmessage:\"" + data.getMessage() + "\"\n}";
    }
}
