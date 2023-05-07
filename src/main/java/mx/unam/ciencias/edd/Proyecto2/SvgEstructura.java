package mx.unam.ciencias.edd.Proyecto2;

import java.lang.StringBuffer;

/*Clase abstracta para crear nuestro svg */
public abstract class SvgEstructura {

    /* Constructor por omisión */
    public SvgEstructura() {
    }

    /* Variable para el inicio de un archivo svg */
    protected final static String svgInicio = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    /* Variable para el inicio de un archivo svg */
    protected final static String svgCierre = "\n\t</g>\n</svg>\n";

    /* Método para crear nuestro svg */
    protected static String creaSvg(int height, int width) {
        String svg = "<svg width=\"%1$d\" height=\"%2$d\" xmlns=\"http://www.w3.org/2000/svg\" > \n\t <g> \n";
        return String.format(svg, width, height);
    }

    /*
     * Método para ensamblar nuestro svg, recibe la cadena con el svg de la
     * estructura
     * correspondiente
     */
    public static String ensambla(String estructure) {
        StringBuffer string = new StringBuffer(svgInicio);
        string.append(estructure);
        string.append(svgCierre);
        System.out.print(string.toString());
        return string.toString();

    }

}
