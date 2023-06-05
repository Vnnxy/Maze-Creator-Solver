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

    /* Array containing the first four bytes that our program requires. */
    private byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

}
