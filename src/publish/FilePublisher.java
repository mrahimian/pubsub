package publish;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * It's a Publisher that publishes data to File
 */
public class FilePublisher implements Publisher {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

    /**
     * Name of the file to be written into
     */
    private String fileName;

    /**
     * Set the fileName
     * Create file or do nothing if file exists
     *
     * @param fileName
     */
    public FilePublisher(String fileName) {
        this.fileName = fileName;

        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


    /**
     * Get current time and write it three times in the file
     *
     * @throws IOException
     */
    @Override
    public void publish(String message) throws IOException {
        ArrayList<Data> arr;
        try {
            try (FileInputStream inputStream = new FileInputStream(fileName);
                 ObjectInputStream ois = new ObjectInputStream(inputStream)) {
                Object obj = ois.readObject();
                arr = (ArrayList<Data>) obj;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try (FileOutputStream outputStream = new FileOutputStream(fileName);
                 ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

                Data data = new Data(message);
                arr.add(data);
                oos.writeObject(arr);

            }
        }catch (EOFException e){
            try(FileOutputStream outputStream = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
                arr = new ArrayList<>();
                fillArray(message,arr);
                oos.writeObject(arr);
                oos.flush();
            }
        }

    }

    /**
     * Get current time in below format :
     * yyyy/MM/dd HH:mm:ss.SSS
     *
     * @return time in above format as String
     */
    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private void fillArray(String message, ArrayList<Data> arr){
        for (int i = 0; i < 3; i++) {
            Data data = new Data(message);
            arr.add(data);
        }
    }
}
