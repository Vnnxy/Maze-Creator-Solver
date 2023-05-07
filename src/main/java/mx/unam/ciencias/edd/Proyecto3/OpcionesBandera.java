package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class OpcionesBandera {

    private String[] path;
    private long semilla;
    private Boolean generador;
    private int width;
    private int height;

    public OpcionesBandera(Lista<String> entrada) {
        determinaAccion(entrada);
        if (generador) {
            flags(path);
        } else {
            obtenSolucion(null);
        }
    }

    /* Determinamos si se quiere generar un laberinto o se quiere resolverlo. */
    public void determinaAccion(Lista<String> entrada) {
        if (entrada.contiene("-g"))
            generador = true;
    }

    /* Método para revisar las banderas de un arreglo */
    public void flags(String[] flag) {
        for (int i = 0; i < flag.length; i++) {
            switch (flag[i]) {
                case "-g":
                    generador = true;
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
                    semilla = Integer.parseInt(flag[i + 1]);
                    break;

                default:

                    semilla = defaultSeed();
                    break;

            }
        }
    }

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
