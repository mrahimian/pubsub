package ir.jibit.dumb.subscribe;

import ir.jibit.dumb.log.LoggerUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Logger;

public class FileProtocol implements SubscriberCommunicationProtocol {

    private FileInputStream fis;
    private final Logger logger;
    private final String cpName;
    private int filePointer;

    public FileProtocol(String fileName, String cpName) throws Exception {
        logger = LoggerUtil.getLogger(FileProtocol.class.getName());
        this.cpName = cpName;

        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + fileName + " not found");
        }
        fis = new FileInputStream(fileName);

        try {
            File storeFile = new File(this.cpName);
            if (storeFile.createNewFile()) {
                logger.info("Store file created: " + storeFile.getName());
                filePointer = 0;
                new DataOutputStream(new FileOutputStream(this.cpName)).writeInt(filePointer);
            } else {
                logger.info("Store file already exists.");
                filePointer = new DataInputStream(new FileInputStream(this.cpName)).readInt();
                fis.readNBytes(filePointer);
            }
        } catch (IOException e) {
            String errorMessage = "Error while opening or reading store file " + cpName;
            logger.warning(errorMessage);
            throw new IOException(errorMessage);
        }
    }

    /**
     * Get one message from file
     * See writeData() function in publish/FileProtocol to realize the method of storing bytes
     * @see ir.jibit.dumb.publish.FileProtocol
     * @return message
     */
    @Override
    synchronized public String readData() throws Exception {
        String message = null;
        try {
            //first byte is the protocol version
            byte[] firstByte = fis.readNBytes(1);
            if (firstByte.length == 0) return null;
            int version = firstByte[0];

            int msgLength = 0;
            int lenBytesNumber = 0;

            if (version == 2 || version == 3) {

                //second byte is the number of next bytes that shows message length
                lenBytesNumber = fis.readNBytes(1)[0];
                msgLength = getMessageLength(fis.readNBytes(lenBytesNumber), lenBytesNumber);

                byte[] msgBytes = fis.readNBytes(msgLength);
                message = new String(msgBytes);
                filePointer += 1 + 1 + lenBytesNumber + msgLength;
            }

            if (version == 3) {

                byte[] checksum = fis.readNBytes(20);
                byte[] checksum2 = DigestUtils.sha1(message);
                if (!Arrays.equals(checksum, checksum2)) {
                    throw new Exception("Data sent does not match the current data");
                }

                filePointer += 20;

            }

            new DataOutputStream(new FileOutputStream(this.cpName)).writeInt(filePointer);
        } catch (Exception e) {
            logger.warning("Error while reading data from file:\n" + e.getMessage());
            throw e;
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
