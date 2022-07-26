package subscribe;

import java.io.*;

public class FileProtocol implements CommunicationProtocol {

    private FileInputStream fis;
    private final String cpName;
    private int filePointer;

    public FileProtocol(String fileName, String cpName) {
        this.cpName = cpName;
        try {
            fis = new FileInputStream(fileName);
        } catch (Exception e) {

        }

        try {
            File file = new File(this.cpName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                filePointer = 0;
                new DataOutputStream(new FileOutputStream(this.cpName)).writeInt(filePointer);
            } else {
                System.out.println("File already exists.");
                filePointer = new DataInputStream(new FileInputStream(this.cpName)).readInt();
                fis.readNBytes(filePointer);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Get a message from file
     *
     * @return message
     */
    @Override
    public String readData() {
        String message = null;
        try {
            //first byte is the protocol version
            int version = fis.readNBytes(1)[0];

            int msgLength = 0;
            int lenBytesNumber = 0;
            if (version == 2) {

                //second byte is the number of next bytes that shows message length
                lenBytesNumber = fis.readNBytes(1)[0];

                msgLength = getMessageLength(fis.readNBytes(lenBytesNumber), lenBytesNumber);
            }

            if (msgLength == 0) return null;

            byte[] msgBytes = fis.readNBytes(msgLength);
            message = new String(msgBytes);

            filePointer += 1 + 1 + lenBytesNumber + msgLength;
            new DataOutputStream(new FileOutputStream(this.cpName)).writeInt(filePointer);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ae) {

        }
        return message;
    }

    /**
     * An array of bytes is converted from binary to integer
     *
     * @param bytes array of bytes
     * @return decimal
     */
    private int getMessageLength(byte[] bytes, int lenBytesNumber) {
        int length = (lenBytesNumber - 1) * 256 + bytes[lenBytesNumber - 1] + 128;

        return length;
    }
}
