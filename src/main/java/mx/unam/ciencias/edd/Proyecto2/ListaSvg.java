package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.Lista;

/*Clase para Listas que extiende de EstructurasCaja */
public class ListaSvg extends EstructurasCaja {

    /* Constructor por omisión */
    public ListaSvg() {
    }

    /* Crea una flecha en la coordenada especificada */
    public static String creaFlecha(int x) {
        x = x + 85;
        int x2 = x + 10;
        String flecha = "<path d=\"M %1$d 35 L %2$d 35 \" stroke=\"black\" marker-end=\"url(#arrowhead)\" />\n <path d=\"M %3$d 35 L %4$d 35 \" stroke=\"black\" marker-end=\"url(#arrowhead)\" />";
        flecha = String.format(flecha, x, x2, x2, x);
        return flecha;
    }

    /* Svg para tener flechas */
    private static String defineFlecha = ("<defs>\n\t<marker id=\"arrowhead\" viewBox=\"0 0 10 10\" refX=\"5\" refY=\"5\" markerWidth=\"8\" markerHeight=\"6\" orient=\"auto-start-reverse\">\n \t<path d=\"M 0 0 L 10 5 L 0 10 z\" />\n \t</marker>\n </defs>");

    /* Crea una lista a partir de un arreglo de elementos */
    @Override
    public String crea(Lista<String> elementos) {
        int svgwidth = 100 * elementos.getLongitud() - 10;
        int svgheight = 70;
        String svgLista = creaSvg(svgheight, svgwidth);
        svgLista += defineFlecha;
        int x = 5;

        for (String e : elementos) {

            int textx = x + 40;
            svgLista += creaNodo(e, x, 5, textx, 35);
            if (e != elementos.getUltimo())
                svgLista += creaFlecha(x);
            x += 100;
        }
        return svgLista;
    }
}
