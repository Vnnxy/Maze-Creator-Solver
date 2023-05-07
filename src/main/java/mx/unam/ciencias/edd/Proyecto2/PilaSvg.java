package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.Lista;

/*Clase para las Pilas que extiende de EstructurasCaja */
public class PilaSvg extends EstructurasCaja {
    /* Constructor por omisión */
    public PilaSvg() {
    }

    /* Crea una pila a partir de un arreglo de elementos */
    @Override
    public String crea(Lista<String> elementos) {
        int baseWidth = 100;
        int baseHeight = (80 * elementos.getLongitud()) + 20;

        String svgPila = creaSvg(baseHeight, baseWidth);
        svgPila += creaBase(baseWidth, baseHeight);
        int y = 20;

        for (String e : elementos) {
            int texty = y + 30;
            svgPila += creaNodo(e, 10, y, 50, texty);
            y += 80;
        }
        return svgPila;
    }
}
