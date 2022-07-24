package subscribe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;

public class FileProtocol implements CommunicationProtocol{

    private final String fileName;

    public FileProtocol(String fileName){
        this.fileName = fileName;

    }

    /**
     * Get a message from file
     * @return message
     */
    @Override
    public String getData() {
        String message = null;
        try(FileInputStream fis = new FileInputStream(fileName)){
            //10 first bytes are the size of the message
            byte[] lenBytes = fis.readNBytes(10);
            int msgLength = getMessageLength(lenBytes);

            byte[] msgBytes  = fis.readNBytes(msgLength);
            message = new String(msgBytes);

        }catch (IOException e ){

        }
        return message;
    }

    /**
     * An array of bytes is converted from binary to integer
     * @param bytes array of bytes
     * @return decimal
     */
    private int getMessageLength(byte[] bytes){
        int length = 0;
        for (int i = 0; i < bytes.length ; i++) {
            length += bytes[i] * Math.pow(2,i);
        }
        return length;
    }
}
