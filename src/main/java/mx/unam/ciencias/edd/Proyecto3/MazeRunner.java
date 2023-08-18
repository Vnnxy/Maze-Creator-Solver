package mx.unam.ciencias.edd.Proyecto3;

import java.io.IOException;

public class MazeRunner {
    public MazeRunner(int width, int height, int seed) {
        this.height = height;
        this.width = width;
        this.seed = seed;

        if (height < 2 || height > 0xFF || width < 2 || width > 0xFF)
            throw new IllegalArgumentException("iae");

        writeFile();
        createMaze();
    }

    private int width;
    private int height;
    private int seed;

    private byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

    private void writeFile() {
        try {
            // We write MAZE on our file
            System.out.write(firstFour);
            // We get the dimensions into our output
            getDimensions();
        } catch (IOException ioException) {
        }

    }

    /*
     * The method we will use to write the bytes that represent the dimensions we
     * will be working with.
     */

    public void getDimensions() {
        System.out.write(width);
        System.out.write(height);

    }

    public void createMaze() {
        MazeGen mg = new MazeGen(width, height, seed);
        GenCell[] g = mg.getGrid();
        byte[] bytes = new byte[width * height];
        int i = 0;
        for (GenCell cell : g) {
            bytes[i] = cell.getByte();
            i++;
        }
        try {
            System.out.write(bytes);
        } catch (IOException ioe) {
        }
    }
}
