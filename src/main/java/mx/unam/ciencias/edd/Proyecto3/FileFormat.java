package mx.unam.ciencias.edd.Proyecto3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FileFormat {

    public FileFormat() {

    }

    private String body = "";

    /* Array containing the first four bytes that our program requires. */
    private byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

    /* The method we'll use to write the first four bytes when creating a file */
    public String bytesMaze() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(firstFour);
            String streamData = out.toString();
            return streamData;

        } catch (IOException ioe) {
            // error
        }
        return null;
    }

    /*
     * The method we will use to write the bytes that represent the dimensions we
     * will be working with.
     */

    public String getDimensions(int w, int h) {
        return Integer.toHexString(w) + Integer.toHexString(h);
    }

    /* Private class we will use to ensure the rooms have all the requirements */
    private class RoomFormat {

    }

}
