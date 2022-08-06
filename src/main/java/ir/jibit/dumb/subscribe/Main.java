package ir.jibit.dumb.subscribe;

import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
//        Subscriber subscriber = new FileSubscriber("sTime.bin", "subscriber1");
//        subscriber.subscribe(msg -> System.out.println(msg.getMessage()));
        SubscriberCommunicationProtocol fp ;
        Decoder decoder;
        Subscriber subscriber;

        try {
            fp = new FileProtocol("sTime.bin", "cp1");
            decoder = new JsonDecoder();
            var es = Executors.newCachedThreadPool();
            subscriber = new Subscriber(fp,decoder,es);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());

//        System.out.println(subscriber.subscribe().toString());
        subscriber.subscribe(msg -> System.out.println(msg));
        subscriber.shutDown();
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe(6000).toString());



//        System.out.println(subscriber.subscribe().getMessage());

    }
}
