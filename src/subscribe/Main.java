package subscribe;

import publish.Data;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Subscriber subscriber = new FileSubscriber("sTime.bin");
        int lastIndex = 0;
        Data lastElement = null;
        while (true) {
            try {
                ArrayList<Data> dataArray = subscriber.subscribe();
                if(lastElement != null && lastElement.equals(dataArray.get(dataArray.size()-1))){
                    Thread.sleep(2000);
                    System.out.println("...");
                }else {
                    int i;
                    for (i = lastIndex; i < dataArray.size(); i++) {
                        System.out.println(dataArray.get(i).getMessage());
                    }
                    lastElement = dataArray.get(dataArray.size() - 1);
                    lastIndex = i;
                }
            }catch (InterruptedException ie){
                System.out.println("Interrupted");
            }
        }
//        while (true){

//        }
    }
}
