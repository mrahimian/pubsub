package ir.jibit.dumb.publish;

import ir.jibit.dumb.log.LoggerUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

public class FileProtocol implements PublisherCommunicationProtocol {

    private FileOutputStream fos;
    private final Logger logger;

    /**
     * Create file or do nothing if file exists
     *
     * @param fileName Name of file to
     */
    public FileProtocol(String fileName) {
        logger = LoggerUtil.getLogger(Publisher.class.getName());

        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                logger.info("File created: " + file.getName());
            } else {
                logger.info("File already exists.");
            }
            fos = new FileOutputStream(fileName, true);

        } catch (NullPointerException np) {
            System.err.println("Logger doesn't create properly");
        } catch (Exception e) {
            logger.warning("Error while opening or creating file");
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void writeData(String msg) throws Exception {
        int counter = 0;
        while (counter < 3) {
            try {
                fos.write(msgToByteArray(msg));
                break;
            } catch (Exception e) {
                logger.warning("Error while writing data in the file. Attempt " + counter + ".\n" + e.getMessage());
                counter++;
                if (counter == 3) throw e;
            }
        }
    }

    /**
     * This method convert String message to byte array
     * First byte is version number
     * Second byte is the number of next n bytes that relates to message length
     * For example if second byte is 2, it means next two bytes shows message length (n = 2)
     * Next n bytes shows message length as mentioned
     * Then next n' bytes (calculated by message length) are reserved for the message
     * <p>
     * From version 3 and later, message hashing has been added to check whether the message correctly delivered to destination
     * <p>
     * So next 20 bytes after message reserved for hashed messaged.
     *
     * @param message
     * @return
     */
    private byte[] msgToByteArray(String message) {
        byte[] msgBytes = null;
        try {
            int msgLength = message.getBytes().length;
            int msgLenBytesNumber = getLenBytesNumber(message.getBytes());

            byte[] checksum = calculateChecksum(message);

            int byteNeeded = 1 + 1 + msgLenBytesNumber + msgLength + 20;

            msgBytes = new byte[byteNeeded];

            //first byte for version number
            msgBytes[0] = 3;
            int currentBytePointer = fillMsgLength(msgBytes, message.getBytes(), 1);

            System.arraycopy(message.getBytes(), 0, msgBytes, msgLenBytesNumber + 2, msgLength);
            currentBytePointer += msgLength;

            System.arraycopy(checksum, 0, msgBytes, currentBytePointer, checksum.length);


        } catch (Exception e) {
            String errorMessage = "Error while converting message to byte array";
            logger.warning(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return msgBytes;
    }

    /**
     * This function is for filling the bytes related to a message bytes length (here is used for both message and its hash)
     *
     * @param finalBytes Is the main byte array which will be written in the file
     * @param msgBytes Bytes of the message that its length is discussed
     * @param currentBytePointer A pointer to the first empty byte in finalBytes
     * @return New currentBytePointer
     */
    private int fillMsgLength(byte[] finalBytes, byte[] msgBytes, int currentBytePointer) {
        int msgLength = msgBytes.length;
        int rest = msgLength % 256;
        rest -= 128;
        int lenBytesNumber = getLenBytesNumber(msgBytes);

        finalBytes[currentBytePointer] = (byte) lenBytesNumber;
        currentBytePointer++;

        for (int i = 0; i < lenBytesNumber; i++) {
            currentBytePointer += i;
            if (i == lenBytesNumber - 1) {
                finalBytes[currentBytePointer] = (byte) rest;
            } else {
                finalBytes[currentBytePointer] = 127;
            }
        }

        return ++currentBytePointer;
    }

    /**
     * This function calculates the bytes need to be reserved to store a message length
     *
     * @param bytes Message bytes
     * @return Number of bytes needed
     */
    private int getLenBytesNumber(byte[] bytes) {
        int msgLength = bytes.length;
        int fullByte = msgLength / 256;
        return fullByte + 1;
    }

    /**
     * A string is hashed using the SHA1
     * algorithm
     *
     * @param message To be hashed
     * @return Hashed message as a byte array
     */
    private byte[] calculateChecksum(String message) {

        try {
            return DigestUtils.sha1(message);
        } catch (Exception e) {
            String errorMessage = "Error while hashing message.\n" + e.getMessage();
            logger.warning(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }


    /**
     * Close file output stream.
     * Writing will be illegal after calling this function
     */
    public void shutDown() {
        int counter = 0;
        while (counter < 3) {
            try {
                fos.close();
                break;
            } catch (IOException e) {
                logger.warning("Unable to close file output stream in publisher.\nAttempt " + counter + 1);
                counter++;
            }
        }
    }
}
