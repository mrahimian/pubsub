package publish;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");


    public static void main(String[] args) {
        Publisher publisher = new FilePublisher("sTime.bin");
        try {
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
            publisher.publish( getTime() );
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
