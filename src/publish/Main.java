package publish;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Publish publish = new Publish();
        String name = publish.createFile("sTime.ser");
        try {
            publish.publish(name);
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
