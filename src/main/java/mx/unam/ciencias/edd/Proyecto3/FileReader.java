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
            checkFormat(fileContent);
            return fileContent;

        } catch (IOException ioe) {
            System.exit(1);
        }
        return null;

    }

    /* Array containing the first four bytes that our program requires. */
    private byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

    private void checkFormat(byte[] content) {
        // Maze check
        int i = 0;
        while (i < 4) {
            if (content[i] != firstFour[i]) {
                System.out.println("MAZE erronea");
                throw new IllegalArgumentException("IAE");
            }
            i++;
        }
        // Falta poner limites
        if (content[i + 1] < 2
                || content[i + 1] > 0xFF) {
            System.out.println("Dimension erronea");
            throw new IllegalArgumentException("IAE");
        }

    }

    private void getCells(byte[] content) {
        for (int i = 6; i < content.length; i++) {
            try {
                Cell cell = new Cell(content[i]);
            } catch (IllegalArgumentException iae) {
                System.exit(1);
            }

        }
    }

    private class Cell {
        Boolean north = true;
        Boolean south = true;
        Boolean east = true;
        Boolean west = true;

        public Cell(byte b) {

            int c = 4;
            int points = (b >> 4) & 15; // este si srve
            int walls = (b) & 15; // este igual

            if ((walls & 1) == 1) {
                east = false;
                c--;
            }
            if (((walls >> 1) & 1) == 1) {
                north = false;
                c--;
            }
            if (((walls >> 2) & 1) == 1) {
                west = false;
                c--;
            }
            if (((walls >> 3) & 1) == 1) {
                south = false;
                c--;
            }

            if (c != 2)
                throw new IllegalArgumentException("iae");

        }
    }

}
