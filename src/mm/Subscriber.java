package mm;

import java.time.Duration;
import java.util.function.Consumer;

public interface Subscriber {
    // read a message in a blocking matter
    XMsg subscribe();

    // read a message in a blocking matter with respect to the given timeout
    // throws TimeoutException
    XMsg subscribe(Duration timeout);

    // pass any incomming message to the consumer method
    void subscribe(Consumer<XMsg> consumer);
}
