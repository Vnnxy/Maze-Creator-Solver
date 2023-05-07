package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.Lista;

public class ArreglosSvg extends EstructurasCaja {
    /* Constructor por omisión */
    public ArreglosSvg() {
    }

    /* Crea una cola a partir de un arreglo de elementos */
    @Override
    public String crea(Lista<String> elementos) {
        int baseWidth = (80 * elementos.getLongitud());
        int baseHeight = 60;

        String svgArr = creaSvg(baseHeight, baseWidth);
        svgArr += creaBase(baseWidth, baseHeight);
        int x = 0;

        for (String e : elementos) {
            int textx = x + 40;
            svgArr += creaNodo(e, x, 0, textx, 30);
            x += 80;
        }
        return svgArr;
    }

}
