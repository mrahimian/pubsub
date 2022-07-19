package subscribe;

import publish.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FileSubscriber implements Subscriber{

    private String fileName;

    /**
     * The last element read by the file
     */
    Data lastElement;
    /**
     * The last index read by the file
     */
    int lastIndex;
    public FileSubscriber(String fileName){
        this.fileName = fileName;
        lastElement = null;
        lastIndex = 0;
    }
    @Override
    public void subscribe(Consumer<Data> consumer){

        ArrayList<Data> arr ;
        while (true) {
            try (
                    FileInputStream inputStream = new FileInputStream(fileName);
                    ObjectInputStream ois = new ObjectInputStream(inputStream)
            ) {
                Object obj = ois.readObject();
                arr = (ArrayList<Data>) obj;

                if (lastElement != null && lastElement.equals(arr.get(arr.size()-1))){
                    Thread.sleep(2000);
                    System.out.println("---");
                }else{

                    int i;
                    for (i = lastIndex; i < arr.size() ; i++) {
                        try {
                            consumer.accept(arr.get(i));
                        }catch (Exception e){
                            // Wait until the correct data is read if an exception occurs while reading data
                            i--;
                        }
                    }
                    lastElement = arr.get(arr.size()-1);
                    lastIndex = i;

                }
            } catch (IOException ex) {
                System.err.println("File not found or is empty");
                return;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Data subscribe() {
        try (
                FileInputStream inputStream = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(inputStream)
        ){
            return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
