package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.Lista;

/*Clase para Colas que extiende de EstructurasCaja */
public class ColaSvg extends EstructurasCaja {

    /* Constructor por omisión */
    public ColaSvg() {
    }

    /* Crea una cola a partir de un arreglo de elementos */
    @Override
    public String crea(Lista<String> elementos) {
        int baseWidth = (100 * elementos.getLongitud()) + 20;
        int baseHeight = 80;

        String svgCola = creaSvg(baseHeight, baseWidth);
        svgCola += creaBase(baseWidth, baseHeight);
        int x = 20;

        for (String e : elementos) {
            int textx = x + 40;
            svgCola += creaNodo(e, x, 10, textx, 40);
            x += 100;
        }
        return svgCola;
    }
}
