package mx.unam.ciencias.edd.Proyecto2;

/*Clase abstracta para crear estructuras circulares  */
public abstract class EstructurasCirculares extends SvgEstructura {

    /* Crea un circulo con las coordenadas especificadas */
    public static String creaCirculo(double x, double y, String colorMain, String colorExterior) {
        String circulo = "\n<circle cx=\"%1$f\" cy=\"%2$f\" r=\"20\" stroke=\"%3$s\" stroke-width=\"3\" fill=\"%4$s\" />";
        return String.format(circulo, x, y, colorExterior, colorMain);
    }

    /* Crea el texto con las coordenadas especificadas */
    public static String creaTexto(double x, double y, int tamaño, String textColor, String text) {
        String textSvg = "\n<text fill='%s' font-family='sans-serif' font-size='%d' x='%f' y='%f' text-anchor='middle' dominant-baseline='middle'> %s </text>";
        return String.format(textSvg, textColor, tamaño, x, y, text);
    }

    /* Crea una linea del hijo al padre */
    public static String creaLinea(double x2, double y2, double x1, double y1) {
        String linea = "\n<line x1='%1$f' y1='%2$f' x2='%3$f' y2='%4$f' stroke='grey' stroke-width='3' />";
        return String.format(linea, x2, y2, x1, y1);
    }

}
