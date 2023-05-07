package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/*Clase pública que extiende de árbol para árboles AVLx */
public class Avl extends Arboles {

    /* Constructor público para Avl */
    public Avl(ArbolAVL<Integer> avl) {
        super(avl);
    }

    /*
     * Método para crear un vértice, lo sobrecargamos, pues necesitamos añadir el
     * balance de cada uno.
     */
    @Override
    protected String creaVertice(VerticeArbolBinario<Integer> vertice, double x, double y) {

        String tostring = vertice.toString();
        String[] text = tostring.split(" ");

        int tamañoBalance = 15;
        String svg = creaCirculo(x, y, color, colorBorde);
        svg += creaTexto(x, y, textTamaño, colorText, text[0]);
        svg += creaTexto(xAvl, y - 30, tamañoBalance, colorText, "(" + text[1] + ")");
        return svg;
    }
}
