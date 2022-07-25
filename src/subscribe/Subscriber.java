package subscribe;

import data.Data;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * A mm.Subscriber subscribes data on external resource such as File, Database, Network ...
 */
public class Subscriber {

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final CommunicationProtocol cp;
    private final Decoder decoder;


    public Subscriber(CommunicationProtocol cp, Decoder decoder) {
        this.cp = cp;
        this.decoder = decoder;
    }

    /**
     * pass any message to consumer method in an async manner
     */
    void subscribe(Consumer<Data> consumer){
        es.submit(()->{
            try{
                _process(consumer);
            }catch (Exception e){

            }
        });
    }

    void _process(Consumer<Data> consumer){
        while (true){
            String msg = cp.readData();
            if(msg != null){
                consumer.accept(decoder.decode(msg));
            }
        }
    }

    /**
     * read a message in blocking matter
     */
    Data subscribe(){
        return _process();
    }

    /**
     * read a message in a blocking matter with respect to the given timeout
     * @param timeout in milliseconds
     * @return
     */
    Data subscribe(int timeout){
        final Future<Data> future = es.submit(()-> _process());
        Data data = null;
        try {
            data = future.get(timeout,TimeUnit.MILLISECONDS);
            es.shutdown();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("timeout");
        }

        return data;
    }

    Data _process(){
        String msg = cp.readData();

        if(msg != null){
            return decoder.decode(msg);
        }
        while (msg == null){
            msg = cp.readData();
        }
        return decoder.decode(msg);
    }
}
