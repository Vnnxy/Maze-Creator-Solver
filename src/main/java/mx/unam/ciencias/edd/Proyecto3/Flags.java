package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class Flags {

    private String[] path;
    private long seed;
    private Boolean gen;
    private int width;
    private int height;

    /* Our flags */
    public Flags(Lista<String> entrada) {
        chooseAction(entrada);
        // We are creating a maze
        if (gen) {
            flags(path);
        } else {
            // We are solving
            FileInput.inputDet();
        }
    }

    /* We decide what we want to do */
    public void chooseAction(Lista<String> entrada) {
        if (entrada.contiene("-g"))
            gen = true;
    }

    /* Método para revisar las banderas de un arreglo */
    public void flags(String[] flag) {
        for (int i = 0; i < flag.length; i++) {
            switch (flag[i]) {
                case "-g":
                    gen = true;
                    break;

                case "-w":
                    if (i + 1 > flag.length)
                        throw new IllegalArgumentException("iae");
                    if (revisaNumeros(flag[i + 1]) == false)
                        width = Integer.parseInt(flag[i + 1]);
                    break;
                case "-h":
                    if (i + 1 > flag.length)
                        throw new IllegalArgumentException("iae");
                    height = Integer.parseInt(flag[i + 1]);
                    break;
                case "-s":
                    if (i + 1 > flag.length)
                        throw new IllegalArgumentException("iae");
                    seed = Integer.parseInt(flag[i + 1]);
                    break;

                default:

                    seed = defaultSeed();
                    break;

            }
        }
    }

    /* We get a seed form the computer clock */
    private long defaultSeed() {
        LocalDateTime now = LocalDateTime.now();
        long seed;

        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        seed = year + month + day + hour + minute + second + millis;
        return seed;

    }

    public void obtenSolucion(String archivo) {

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
