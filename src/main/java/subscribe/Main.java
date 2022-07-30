package subscribe;

import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
//        Subscriber subscriber = new FileSubscriber("sTime.bin", "subscriber1");
//        subscriber.subscribe(msg -> System.out.println(msg.getMessage()));
        CommunicationProtocol fp ;
        Decoder decoder;
        Subscriber subscriber;
        try {
            fp = new FileProtocol("sTime.bin", "cp1");
            decoder = new JsonDecoder();
            var es = Executors.newCachedThreadPool();
            subscriber = new Subscriber(fp,decoder,es);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //todo : if file not exists


//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
        new Thread(()->{
            System.out.println(subscriber.subscribe().toString());
            System.out.println(subscriber.subscribe().toString());
            System.out.println(subscriber.subscribe().toString());
            System.out.println(subscriber.subscribe().toString());
        }).start();
        System.out.println(subscriber.subscribe().toString());
        System.out.println(subscriber.subscribe().toString());
        System.out.println(subscriber.subscribe().toString());
        System.out.println(subscriber.subscribe().toString());
//        subscriber.subscribe(msg -> System.out.println(msg));
        subscriber.shutDown();
//        subscriber.subscribe(msg -> System.out.println(msg));
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe(6000).toString());



//        System.out.println(subscriber.subscribe().getMessage());

    }
}
