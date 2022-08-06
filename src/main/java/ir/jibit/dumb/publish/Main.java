package ir.jibit.dumb.publish;

import ir.jibit.dumb.data.Data;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Main {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");


    public static void main(String[] args) {
        PublisherCommunicationProtocol cp;
        Publisher publisher;
        try {
            cp = new FileProtocol("sTime.bin");
            Encoder enc = new JsonEncoder();
            publisher = new Publisher(cp, enc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
//            publisher.publish( new Data(getTime()) );
//            while (true){
            publisher.publish(new Data(getTime()));
//                long beforePublish = System.currentTimeMillis();
////                for (int i = 0; i < 100; i++) {
////                    publisher.publish( FilePublisher.getTime() );
////                }
//                long afterPublish = System.currentTimeMillis();
            Thread.sleep(300);
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


}
