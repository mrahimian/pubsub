package publish;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Publisher publisher = new FilePublisher("sTime.bin");
        try {
            while (true){
                long beforePublish = System.currentTimeMillis();
                publisher.publish( FilePublisher.getTime() );
                publisher.publish( FilePublisher.getTime() );
                publisher.publish( FilePublisher.getTime() );
//                for (int i = 0; i < 100; i++) {
//                    publisher.publish( FilePublisher.getTime() );
//                }
                long afterPublish = System.currentTimeMillis();
                Thread.sleep(1000);
//                System.out.println(afterPublish - beforePublish);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){

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
