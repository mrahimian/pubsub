package ir.jibit.dumb.subscribe;

import ir.jibit.dumb.data.Data;
import ir.jibit.dumb.log.LoggerUtil;
import ir.jibit.dumb.publish.Publisher;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * A mm.Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public class Subscriber {

    private final ExecutorService specialOne = Executors.newCachedThreadPool();
    private final ExecutorService es;
    private final SubscriberCommunicationProtocol cp;
    private final Decoder decoder;

    private final Logger logger;


    public Subscriber(SubscriberCommunicationProtocol cp, Decoder decoder, ExecutorService es) throws IOException {
        this.cp = cp;
        this.decoder = decoder;
        this.es = es;
        logger = LoggerUtil.getLogger(Publisher.class.getName());
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
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.warning(e.getMessage());
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
        try {
            specialOne.submit(() -> {
                try {
                    while (true) {
                        var msg = _subscribe();
                        if (msg.isPresent()) {
                            consumer.accept(msg.get());
                        } else {
                            Thread.sleep(1000);

                        }
                    }
                } catch (Exception e) {
                    logger.warning("Error while subscribing data in async manner :\n" + e.getMessage());
                }
            });
        } catch (RejectedExecutionException re) {
            logger.warning("call subscribe after shutdown");
        }
    }

    /**
     * Subscribing after shutting down is no longer possible
     */
    public void shutDown() {
        //es.shutdown();
        specialOne.shutdown();
    }
}
