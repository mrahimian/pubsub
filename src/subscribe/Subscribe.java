package subscribe;

import publish.Data;

import java.io.*;
import java.util.ArrayList;

public class Subscribe {

    public void subscribe(String fileName){
        try (
                FileInputStream inputStream = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(inputStream)
        ) {

            Object obj = ois.readObject();
            ArrayList<Data> arr = (ArrayList<Data>)obj;
//            Data data = (Data) obj;
//            System.out.println(data.getMessage());
            for(Data data : arr){
                System.out.println(data.getMessage());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
