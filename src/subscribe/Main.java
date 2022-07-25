package subscribe;

import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
//        Subscriber subscriber = new FileSubscriber("sTime.bin", "subscriber1");
//        subscriber.subscribe(msg -> System.out.println(msg.getMessage()));
        CommunicationProtocol fp = new FileProtocol("sTime.bin", "cp1");
        Decoder decoder = new JsonDecoder();

        var es = Executors.newFixedThreadPool(1);

        Subscriber subscriber = new Subscriber(fp,decoder, es);
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe().toString());
//        System.out.println(subscriber.subscribe(6000).toString());

//        subscriber.subscribe(msg -> System.out.println(msg));
//        System.out.println(fp.getData());
//        System.out.println(new JsonDecoder().decode(fp.getData()));

//        System.out.println("@@@@");

//        System.out.println(subscriber.subscribe().getMessage());

    }
}
