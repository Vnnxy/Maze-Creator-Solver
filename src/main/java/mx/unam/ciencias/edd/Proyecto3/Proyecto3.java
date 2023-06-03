package mx.unam.ciencias.edd.Proyecto3;

import java.io.IOException;

public class Proyecto3 {
    public static void main(String[] args) {
        // FileReader f = new FileReader();
        // f.readFile(args[0]);
        MazeGen mg = new MazeGen(4, 4, 4);
        byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45, 0x04, 0x04 };
        GenCell[] g = mg.getGrid();
        byte[] bytes = new byte[22];
        for (int j = 0; j < 6; j++)
            bytes[j] = firstFour[j];
        int i = 6;
        for (GenCell cell : g) {
            bytes[i] = cell.getByte();
            i++;
        }

    }
}
