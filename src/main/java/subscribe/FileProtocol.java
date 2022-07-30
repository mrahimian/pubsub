package subscribe;

import log.SysLogger;
import publish.Publisher;

import java.io.*;
import java.util.logging.Logger;

public class FileProtocol implements CommunicationProtocol {

    private FileInputStream fis;
    private final Logger logger;
    private final String cpName;
    private int filePointer;

    public FileProtocol(String fileName, String cpName) throws IOException {
        logger = new SysLogger(Publisher.class.getName()).getLogger();
        this.cpName = cpName;

        File file = new File(fileName);
        if(!file.exists()){
            throw new FileNotFoundException("File " + fileName + " not found");
        }
        fis = new FileInputStream(fileName);

        try {
            File storeFile = new File(this.cpName);
            if (file.createNewFile()) {
                logger.info("Store file created: " + storeFile.getName());
                filePointer = 0;
                new DataOutputStream(new FileOutputStream(this.cpName)).writeInt(filePointer);
            } else {
                logger.info("Store file already exists.");
                filePointer = new DataInputStream(new FileInputStream(this.cpName)).readInt();
                fis.readNBytes(filePointer);
            }
        } catch (Exception e) {
            logger.warning("Error while opening or reading store file " + cpName);
        }
    }

    /**
     * Get a message from file
     *
     * @return message
     */
    @Override
    synchronized public String readData() {
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
        } catch (Exception e) {
            logger.warning("Error while reading data from file");
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
