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

            // byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[] fileContent = fis.readAllBytes();
            MazeReader mze = new MazeReader();
            mze.getCells(fileContent);

        } catch (IOException ioe) {
            System.exit(1);
        }
    }

    public static void stdin() {
        try {
            byte[] data = System.in.readAllBytes();
            MazeReader mze = new MazeReader();
            mze.getCells(data);
        } catch (IOException ioe) {
        }

    }

}
