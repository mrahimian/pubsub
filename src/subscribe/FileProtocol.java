package subscribe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;

public class FileProtocol implements CommunicationProtocol{

    private FileInputStream fis;

    public FileProtocol(String fileName){
        try {
            fis = new FileInputStream(fileName);
        }catch (Exception e){

        }
    }

    /**
     * Get a message from file
     * @return message
     */
    @Override
    public String readData() {
        String message = null;
//        try(FileInputStream fis = new FileInputStream(fileName)){
        try {

            //first byte is the protocol version
            int version = fis.readNBytes(1)[0];

            //second byte is the number of next bytes that shows message length
            int lenBytesNumber = fis.readNBytes(1)[0];

            int msgLength = getMessageLength(fis.readNBytes(lenBytesNumber),lenBytesNumber);

            if (msgLength == 0) return null;

            byte[] msgBytes  = fis.readNBytes(msgLength);
            message = new String(msgBytes);

        }catch (IOException | NullPointerException e ){
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException ae){

        }
        return message;
    }

    /**
     * An array of bytes is converted from binary to integer
     * @param bytes array of bytes
     * @return decimal
     */
    private int getMessageLength(byte[] bytes, int lenBytesNumber){
        int length = 0;
        length = (lenBytesNumber - 1) * 256 + bytes[lenBytesNumber-1] + 128;


        return length;
    }
}
