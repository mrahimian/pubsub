package subscribe;

import publish.Data;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class FileSubscriber implements Subscriber, Serializable {
    transient private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final String fileName;

    private final String subscriberName;

    /**
     * The last element read by the file
     */
    private Data lastElement;
    /**
     * The last index read by the file
     */
    private int lastIndex = 0;

    public FileSubscriber(String fileName, String subscriberName) {
        this.fileName = fileName;
        this.subscriberName = subscriberName + ".bin";
        this.lastElement = null;

        try {
            File file = new File(this.subscriberName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(Consumer<Data> consumer) {
        es.submit(() -> {
            try {
                _process(consumer);
            } catch (Exception e) {
                // TODO @MR addd catch statement
                e.printStackTrace();
            }
        });
    }

    private void _process(Consumer<Data> consumer) throws Exception {
        final FileSubscriber fs = getLastStatus();
        Data data = null;
        boolean flag = true;
        while (true) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                Object obj = ois.readObject();

                data = (Data) obj;
                if (flag) {
//                    lastIndex = fs != null ? ((fs.lastIndex + 11) > arr.size() ? arr.size()-1 : fs.lastIndex+11) : lastIndex;
//                    lastIndex = fs != null ? ((fs.lastIndex + 2) > arr.size() ? arr.size() - 1 : fs.lastIndex + 2) : lastIndex;
                    lastIndex = fs != null ? fs.lastIndex : lastIndex;
//                    lastElement = fs != null ? (arr.get(lastIndex)) : lastElement;
                    flag = false;
                }

//                if (lastElement != null && lastElement.equals(arr.get(arr.size() - 1))) {
//                    Thread.sleep(2000);
//                    System.out.println("---");
//                    continue;
//                }

////                    int count = 0;
//                for (int i = lastIndex; i < arr.size(); i++) {
//                    try {
//                        consumer.accept(arr.get(i));
//                        //Thread.sleep(300);
////                            count++;
//                    } catch (Exception e) {
//                        // Wait until the correct data is read if an exception occurs while reading data
//                        //i--;
//                        // TODO
//                    }
//
//                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(subscriberName))) {
//                        lastIndex = i;
//                        lastElement = arr.get(lastIndex);
//                        oos.writeObject(FileSubscriber.this);
//                    }
//                }
            } catch (EOFException e) {
                Thread.sleep(100);
            }catch (Exception e) {
                // todo
                e.printStackTrace();
            }
        }
    }

    @Override
    public Data subscribe() {
        try (
                FileInputStream inputStream = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(inputStream)
        ) {
            return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileSubscriber getLastStatus() {
        FileSubscriber fs = null;
        try (
                FileInputStream inputStream = new FileInputStream(subscriberName);
                ObjectInputStream ois = new ObjectInputStream(inputStream)
        ) {
            Object obj = ois.readObject();
            fs = (FileSubscriber) obj;
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the file ");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return fs;
    }

}
