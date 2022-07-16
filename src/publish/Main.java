package publish;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FilePublisher publish = new FilePublisher("sTime.ser");
        publish.createInstance();
        try {
            publish.publish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    /*{
        "name" : "mohammad",
        "lastName" : "rahimian",
        "father": "    {\n" +
            "        \"name\" : \"mohammad\",\n" +
            "        \"lastName\" : \"rahimian\"\n" +
            "    }"
    }*/

}
