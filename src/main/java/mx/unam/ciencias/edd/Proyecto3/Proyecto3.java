package mx.unam.ciencias.edd.Proyecto3;

public class Proyecto3 {
    public static void main(String[] args) {
        // FileReader f = new FileReader();
        // f.readFile(args[0]);

        Boolean north = false;
        Boolean south = false;
        Boolean east = false;
        Boolean west = false;
        int b = 0xF6;
        int points = (b >> 4) & 15; // este si srve
        int walls = (b) & 15; // este igual

        if ((walls & 1) == 1)
            east = true;
        if (((walls >> 1) & 1) == 1)
            north = true;
        if (((walls >> 2) & 1) == 1)
            west = true;
        if (((walls >> 3) & 1) == 1)
            south = true;

        System.out.println("s:" + south + " w:" + west + " e:" + east + " n:" + north);

    }
}
