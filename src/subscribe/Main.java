package subscribe;

public class Main {
    public static void main(String[] args) {
//        Subscriber subscriber = new FileSubscriber("sTime.bin", "subscriber1");
//        subscriber.subscribe(msg -> System.out.println(msg.getMessage()));
        CommunicationProtocol fp = new FileProtocol("sTime.bin");
        Decoder decoder = new JsonDecoder();

        Subscriber subscriber = new Subscriber(fp,decoder);
        System.out.println(subscriber.subscribe().toString());
//        System.out.println(fp.getData());
//        System.out.println(new JsonDecoder().decode(fp.getData()));

//        System.out.println("@@@@");

//        System.out.println(subscriber.subscribe().getMessage());

    }
}
