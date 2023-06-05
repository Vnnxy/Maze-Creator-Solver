package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class Flags {

    private int seed = -1;
    private Boolean gen = false;
    private int width = 0;
    private int height = 0;

    /* Our flags */
    public Flags(String[] entrada) {
        chooseAction(entrada);
        // We are creating a maze
        if (gen) {
            flags(entrada);
            create();
        } else {
            // We are solving
            FileInput.inputDet(entrada);
        }
    }

    /* We decide what we want to do */
    public void chooseAction(String[] entrada) {
        for (int i = 0; i < entrada.length; i++) {
            if (entrada[i].equals("-g")) {
                gen = true;
                break;
            }
        }

    }

    /* Método para revisar las banderas de un arreglo */
    public void flags(String[] flag) {
        for (int i = 0; i < flag.length; i++) {

            switch (flag[i]) {

                case "-w":
                    if (i + 1 > flag.length || revisaNumeros(flag[i + 1]) == false)
                        throw new IllegalArgumentException("iae");

                    width = Integer.parseInt(flag[i + 1]);
                    break;
                case "-h":
                    if (i + 1 > flag.length || revisaNumeros(flag[i + 1]) == false)
                        throw new IllegalArgumentException("iae");
                    height = Integer.parseInt(flag[i + 1]);

                    break;
                case "-s":
                    if (i + 1 > flag.length || revisaNumeros(flag[i + 1]) == false)
                        throw new IllegalArgumentException("iae");
                    seed = Integer.parseInt(flag[i + 1]);
                    break;
            }
        }
    }

    /* We get a seed form the computer clock */
    private void defaultSeed() {
        LocalDateTime now = LocalDateTime.now();

        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        seed = year + month + day + hour + minute + second + millis;

    }

    public void create() {
        if (width == 0 || height == 0)
            throw new IllegalArgumentException("iae");
        if (seed == -1)
            defaultSeed();
        MazeRunner mr = new MazeRunner(width, height, seed);

    }

    /* Método auxiliar para revisar si el string dado contiene un entero */
    private static boolean revisaNumeros(String entrada) {
        try {
            int i = Integer.parseInt(entrada);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
