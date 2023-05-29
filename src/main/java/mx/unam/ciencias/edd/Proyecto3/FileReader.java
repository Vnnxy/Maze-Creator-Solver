package mx.unam.ciencias.edd.Proyecto3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;

import mx.unam.ciencias.edd.*;

public class FileReader {

    public FileReader() {
    }

    public byte[] readFile(String path) {
        try {

            File file = new File(path);
            FileInputStream fis = new FileInputStream(path);

            // byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[] fileContent = fis.readAllBytes();
            MazeReader mze = new MazeReader();
            Grafica<Cell> m = mze.getCells(fileContent);
            return fileContent;

        } catch (IOException ioe) {
            System.exit(1);
        }
        return null;

    }

}
