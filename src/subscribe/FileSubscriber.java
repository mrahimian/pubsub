package subscribe;

import publish.Data;

import java.io.*;
import java.util.ArrayList;

public class FileSubscriber implements Subscriber{

    String fileName;
    public FileSubscriber(String fileName){
        this.fileName = fileName;
    }
    public ArrayList<Data> subscribe(){
        ArrayList<Data> arr = null;
        try (
                FileInputStream inputStream = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(inputStream)
        ) {
            Object obj = ois.readObject();
            arr = (ArrayList<Data>) obj;

//            for (int i = 0; i < arr.size() ; i++) {
//                System.out.println(arr.get(i).getMessage());
//            }

//                for (Data data : arr) {
//                    System.out.println(data.getMessage());
//                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return arr;
    }
}
