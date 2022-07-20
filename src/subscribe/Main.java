package subscribe;

public class Main {
    public static void main(String[] args) {
        Subscriber subscriber = new FileSubscriber("sTime.bin", "subscriber1");
        subscriber.subscribe(msg -> System.out.println(msg.getMessage()));

        System.out.println("@@@@");

//        System.out.println(subscriber.subscribe().getMessage());

    }
}
