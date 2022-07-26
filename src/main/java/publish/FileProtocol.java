package publish;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileProtocol implements CommunicationProtocol{

    private FileOutputStream fos;

    /**
     * Create file or do nothing if file exists
     *
     * @param fileName
     */
    public FileProtocol(String fileName) {

        try {
            fos = new FileOutputStream(fileName,true);
        }catch (Exception e){

        }
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
    @Override
    public void writeData(String msg) {
        try{
            fos.write(msgToByteArray(msg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] msgToByteArray(String message){

        int msgLength = message.length();
        int fullByte = msgLength/256;
        int rest = msgLength % 256 ;
        int lenBytesNumber = fullByte + 1;
        rest -= 128;

        int byteNeeded = 1 + 1 + lenBytesNumber + msgLength;
        byte[] msgBytes = new byte[byteNeeded];

        //first byte for version number
        msgBytes[0] = 2;
        //second byte for number of next byte that relates to message length
        msgBytes[1] = (byte)(fullByte + 1);

        for (int i = 0; i < lenBytesNumber ; i++) {
            if(i == lenBytesNumber-1){
                msgBytes[i+2] = (byte)rest;
            }else {
                msgBytes[i+2] = 127;
            }
        }
        System.arraycopy(message.getBytes(), 0, msgBytes, lenBytesNumber+2, msgLength);

        return msgBytes;
    }
}
