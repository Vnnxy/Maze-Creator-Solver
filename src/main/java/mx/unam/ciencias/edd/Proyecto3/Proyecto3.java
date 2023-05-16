package mx.unam.ciencias.edd.Proyecto3;

public class Proyecto3 {
    public static void main(String[] args) {
        FileFormat ff = new FileFormat();
        ff.bytesMaze();
        System.out.println(ff.getDimensions(8, 13));
    }
}
