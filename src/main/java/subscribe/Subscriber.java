package subscribe;

import data.Data;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * A mm.Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public class Subscriber {

    private final ExecutorService specialOne = Executors.newCachedThreadPool();
    private final ExecutorService es;
    private final CommunicationProtocol cp;
    private final Decoder decoder;


    public Subscriber(subscribe.CommunicationProtocol cp, Decoder decoder, ExecutorService es) {
        this.cp = cp;
        this.decoder = decoder;
        this.es = es;
    }

    /**
     * read a message in blocking matter
     */
    private Optional<Data> _subscribe() {
        String msg = cp.readData();

        if (msg != null) {
            return decoder.decode(msg);
        }

        while (msg == null) {
            msg = cp.readData();
        }

        return decoder.decode(msg);
    }

    /**
     * read a message in a blocking matter with respect to the given timeout
     *
     * @param timeout in milliseconds
     * @return
     */
    public Optional<Data> subscribe(int timeout) {
        final Future<Optional<Data>> future = es.submit(() -> _subscribe());
        try {
            var data = timeout > 0 ? future.get(timeout, TimeUnit.MILLISECONDS) : future.get();
            return data;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("timeout");
        }

        return Optional.empty();
    }

    public Data subscribe() {
        return subscribe(-1).get();
    }

    /**
     * pass any message to consumer method in an async manner
     */
    public void subscribe(Consumer<Data> consumer) {
        specialOne.submit(() -> {
            try {
                while (true) {
                    var msg = subscribe(1000);
                    if (msg.isPresent()) {
                        consumer.accept(msg.get());
                    }
                }
            } catch (Exception e) {
                // todo
            }
        });
    }

    public void shutDown() {
        es.shutdown();
        specialOne.shutdown();
    }
}
