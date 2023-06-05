package mx.unam.ciencias.edd.Proyecto3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.*;

public class FileReader {

    public FileReader() {
    }

    /* Our method that reads from the stdin s given file. */
    public static void readFile(String path) {
        try {

            File file = new File(path);
            FileInputStream fis = new FileInputStream(path);
            byte[] fileContent = fis.readAllBytes();
            MazeReader mze = new MazeReader();
            try {
                mze.checkFormat(fileContent);
                mze.getCells(fileContent);
            } catch (IllegalArgumentException iae) {
                System.out.println("Please use a valid file");
                System.exit(1);
            }

        } catch (IOException ioe) {
            System.out.println("Cannot access the given file");
            System.exit(1);
        } catch (IllegalArgumentException iae) {
            System.out.println("Wrong dimensions");
        }
    }

    public static void stdin() {
        try {
            byte[] data = System.in.readAllBytes();
            MazeReader mze = new MazeReader();
            try {
                mze.checkFormat(data);
                mze.getCells(data);
            } catch (IllegalArgumentException iae) {
                System.out.println("Please use a valid file");
                System.exit(1);
            }

        } catch (IOException ioe) {
            System.out.println("Cannot access the given file");
            System.exit(1);
        }

    }

}
