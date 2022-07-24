package publish;

import data.Data;

import java.io.*;
import java.util.ArrayList;

/**
 * It's a Publisher that publishes data to File
 */
public class FilePublisher implements Publisher {

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
     * Get message and write it in the file
     *
     * @throws IOException
     */
    @Override
    public void publish(String message) throws IOException {

        try (FileOutputStream fos = new FileOutputStream(fileName,true)) {

            fos.write(msgToByteArray(message));
        }
    }


    private byte[] msgToByteArray(String message){
        String formattedMsg = formatMsg(message);
        int msgLength = formattedMsg.length();

        int id = 0;
        byte[] msgBytes = new byte[msgLength+10];
        // write size of message in first ten byte of msgBytes array
        while (msgLength > 0) {
            msgBytes[id++] = (byte) (msgLength % 2);
            msgLength = msgLength / 2;
        }

        System.arraycopy(formattedMsg.getBytes(), 0, msgBytes, 10, msgBytes.length - 10);

        return msgBytes;
    }

    private String formatMsg(String message){
        return "{\n\tmessage:\"" + message + "\"\n}";
    }


}
