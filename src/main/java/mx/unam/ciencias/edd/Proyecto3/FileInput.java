package mx.unam.ciencias.edd.Proyecto3;

public class FileInput {
    public FileInput() {
    }

    // We choose where we are reading from
    public static void inputDet(String[] args) {
        if (args.length == 0 || args[0] == null) {
            FileReader.stdin();
        } else
            FileReader.readFile(args[0]);
    }

}
