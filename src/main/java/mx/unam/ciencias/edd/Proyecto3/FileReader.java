package mx.unam.ciencias.edd.Proyecto3;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileReader {

    public FileReader() {
    }

    public byte[] readFile(String path) {
        try {

            File file = new File(path);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            System.out.println("all good");
            MazeReader mze = new MazeReader();
            mze.getCells(fileContent);
            return fileContent;

        } catch (IOException ioe) {
            System.exit(1);
        }
        return null;

    }

}
