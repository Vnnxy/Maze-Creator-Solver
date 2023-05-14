package mx.unam.ciencias.edd.Proyecto3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class FileFormat {

    public FileFormat() {

    }

    private String body = "";

    /* Array containing the first four bytes that our program requires. */
    private Byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

    /* The method we'll use to write the first four bytes when creating a file */
    public String bytesMaze() {

    }

    /*
     * The method we'll use to check if a file contains the first four required
     * bytes, thus making it valid.
     */
    public boolean hasMaze(byte[] file) {
        String line = null;
        try {
            new ByteArrayInputStream(file)
        } catch (IOException ioe) {
            // I probably need a message here
        }
    }

}
