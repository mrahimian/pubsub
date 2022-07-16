package subscribe;

public class Main {
    public static void main(String[] args) {
        FileSubscriber subscribe = new FileSubscriber();
        subscribe.subscribe("sTime.ser");
    }
}
