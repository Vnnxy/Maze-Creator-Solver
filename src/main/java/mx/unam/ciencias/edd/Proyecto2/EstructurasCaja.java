package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.Lista;

/*Clase abstracta para estructuras lineales */
public abstract class EstructurasCaja extends SvgEstructura {

    /* Crea un nodo */
    public static String creaNodo(String text, int x, int y, int textx, int texty) {
        String nodo = "\n<rect x=\"%1$d\" y=\"%2$d\" width=\"80\" height=\"60\" fill=\"white\" stroke=\"black\" stroke-width=\"2\"></rect>\n <text x=\"%3$d\" y=\"%4$d\" font-family=\"sans-serif\" font-size=\"20\" fill=\"black\" dominant-baseline=\"middle\" text-anchor=\"middle\">%5$s</text>\n";
        nodo = String.format(nodo, x, y, textx, texty, text);
        return nodo;
    }

    /* Crea una base para los nodos */
    public static String creaBase(int width, int height) {
        String base = "\t \t <rect width= \"%1$s\" height=\"%2$d\" fill=\"White\" stroke=\"Black\"/> \n";
        base = String.format(base, width, height);
        return base;
    }

    /* Método que sobrecargaremos para crear la cadena con el svg */
    public abstract String crea(Lista<String> elementos);

}
