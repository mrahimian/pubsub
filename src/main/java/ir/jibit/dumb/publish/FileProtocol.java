package ir.jibit.dumb.publish;

import ir.jibit.dumb.log.LoggerUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class FileProtocol implements PublisherCommunicationProtocol {

    private FileOutputStream fos;
    private final Logger logger;

    /**
     * Create file or do nothing if file exists
     *
     * @param fileName
     */
    public FileProtocol(String fileName) throws IOException {
        logger = LoggerUtil.getLogger(Publisher.class.getName());

        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                logger.info("File created: " + file.getName());
            } else {
                logger.info("File already exists.");
            }
            fos = new FileOutputStream(fileName, true);
            //todo close fos
        } catch (Exception e) {
            logger.warning("Error while opening or creating file");
        }

    }

    @Override
    public synchronized void writeData(String msg) {
        try {
            fos.write(msgToByteArray(msg));
        } catch (IOException e) {
            logger.warning("Error while writing data in the file");
        }
    }

    /**
     * This method convert String message to byte array
     * First byte is version number
     * Second byte is the number of next bytes that relates to message length
     *
     * @param message
     * @return
     */
    private byte[] msgToByteArray(String message) {
        //todo checksum
        byte[] msgBytes = null;
        try {
            int msgLength = message.length();
            int fullByte = msgLength / 256;
            int rest = msgLength % 256;
            int lenBytesNumber = fullByte + 1;
            rest -= 128;

            int byteNeeded = 1 + 1 + lenBytesNumber + msgLength;
            msgBytes = new byte[byteNeeded];

            //first byte for version number
            msgBytes[0] = 2;
            //second byte for number of next byte that relates to message length
            msgBytes[1] = (byte) (fullByte + 1);

            for (int i = 0; i < lenBytesNumber; i++) {
                if (i == lenBytesNumber - 1) {
                    msgBytes[i + 2] = (byte) rest;
                } else {
                    msgBytes[i + 2] = 127;
                }
            }
            System.arraycopy(message.getBytes(), 0, msgBytes, lenBytesNumber + 2, msgLength);
        } catch (Exception e) {
            logger.warning("Error while converting message to byte array");
        }

        return msgBytes;
    }
}
