package publish;

import data.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Main {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");


    public static void main(String[] args) {
        CommunicationProtocol cp = new FileProtocol("sTime.bin");
        Encoder encoder = new JsonEncoder();
        Publisher publisher = new Publisher(cp,encoder);
        try {
//            publisher.publish( new Data(getTime()) );
            publisher.publish( new Data(getTime()) );
//            while (true){
//                long beforePublish = System.currentTimeMillis();
//                publisher.publish( getTime() );
//                publisher.publish( getTime() );
//                publisher.publish( getTime() );
////                for (int i = 0; i < 100; i++) {
////                    publisher.publish( FilePublisher.getTime() );
////                }
//                long afterPublish = System.currentTimeMillis();
//                Thread.sleep(300);
////                System.out.println(afterPublish - beforePublish);
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get current time in below format :
     * yyyy/MM/dd HH:mm:ss.SSS
     *
     * @return time in above format as String
     */
    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
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
