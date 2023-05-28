package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {
    }

    /**
     * Función de dispersión XOR.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int r = 0, i = 0, l = llave.length;
        while (l >= 4) {
            r ^= bigEndian(llave[i++], llave[i++], llave[i++], llave[i++]);
            l -= 4;
        }
        int t = 0;
        switch (l) {
            case 1:
                t |= posiciona(llave[llave.length - 1], 24);
                break;
            case 2: {
                t |= posiciona(llave[llave.length - 2], 24);
                t |= posiciona(llave[llave.length - 1], 16);
                break;
            }
            case 3: {
                t |= posiciona(llave[llave.length - 3], 24);
                t |= posiciona(llave[llave.length - 2], 16);
                t |= posiciona(llave[llave.length - 1], 8);
                break;
            }
        }
        return r ^ t;
    }

    private static int posiciona(byte b, int d) {
        return ((b & 0xFF) << d);
    }

    private static int littleEndian(int a, int b, int c, int d) {
        return (a & 0xFF) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | ((d & 0xFF) << 24);
    }

    private static int bigEndian(int a, int b, int c, int d) {
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | (d & 0xFF);
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;

        int l = llave.length;
        int count = 0;

        int[] bar = new int[3];

        while (l >= 12) {
            a += littleEndian(llave[count++], llave[count++], llave[count++], llave[count++]);
            b += littleEndian(llave[count++], llave[count++], llave[count++], llave[count++]);
            c += littleEndian(llave[count++], llave[count++], llave[count++], llave[count++]);
            bar[0] = a;
            bar[1] = b;
            bar[2] = c;

            mix(bar);

            a = bar[0];
            b = bar[1];
            c = bar[2];

            l -= 12;
        }
        c += llave.length;
        switch (l) {
            case 11:
                c += (posiciona(llave[count + 10], 24));
            case 10:
                c += (posiciona(llave[count + 9], 16));
            case 9:
                c += (posiciona(llave[count + 8], 8));
            case 8:
                b += (posiciona(llave[count + 7], 24));
            case 7:
                b += (posiciona(llave[count + 6], 16));
            case 6:
                b += (posiciona(llave[count + 5], 8));
            case 5:
                b += (llave[count + 4] & 0xFF);
            case 4:
                a += (posiciona(llave[count + 3], 24));
            case 3:
                a += (posiciona(llave[count + 2], 16));
            case 2:
                a += (posiciona(llave[count + 1], 8));
            case 1:
                a += (llave[count] & 0xFF);
        }

        bar[0] = a;
        bar[1] = b;
        bar[2] = c;

        mix(bar);

        return bar[2];

    }

    private static void mix(int[] bar) {

        int a = bar[0];
        int b = bar[1];
        int c = bar[2];

        a -= b;
        a -= c;
        a ^= (c >>> 13);

        b -= c;
        b -= a;
        b ^= (a << 8);

        c -= a;
        c -= b;
        c ^= b >>> 13;

        a -= b;
        a -= c;
        a ^= (c >>> 12);

        b -= c;
        b -= a;
        b ^= (a << 16);

        c -= a;
        c -= b;
        c ^= (b >>> 5);

        a -= b;
        a -= c;
        a ^= (c >>> 3);

        b -= c;
        b -= a;
        b ^= (a << 10);

        c -= a;
        c -= b;
        c ^= (b >>> 15);

        bar[0] = a;
        bar[1] = b;
        bar[2] = c;

    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;
        for (int i = 0; i < llave.length; i++) {
            h += (h << 5) + (llave[i] & 0xFF);
        }
        return h;
    }
}
