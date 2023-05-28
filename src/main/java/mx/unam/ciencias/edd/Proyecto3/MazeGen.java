package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.*;

public class MazeGen {

    public int createMaze(int w, int h, int seed) {
        byte[] arr = new byte[] { (byte) w, (byte) h, (byte) seed };
        int prueba = Dispersores.dispersaDJB(arr);
        return prueba;
    }
}
