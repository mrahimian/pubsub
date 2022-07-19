package subscribe;

import publish.Data;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();

        Subscriber subscriber = new FileSubscriber("sTime.bin");

        executor.execute(()->{
            subscriber.subscribe(msg-> System.out.println(msg.getMessage()));
        });

//        System.out.println(subscriber.subscribe().getMessage());

    }
}
