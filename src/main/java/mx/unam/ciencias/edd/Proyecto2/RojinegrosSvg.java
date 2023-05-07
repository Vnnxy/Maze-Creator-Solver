package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/*Clase para Árboles Rojinegros que extiende de Arboles */
public class RojinegrosSvg extends Arboles {

    /* Constructor para RojinegrosSvg */
    public RojinegrosSvg(ArbolRojinegro<Integer> rojinegro) {
        super(rojinegro);
    }

    /*
     * Sobrecargamos nuestro método creaVértice para poder agregar el color que
     * queramos a nuestro vértice
     */
    @Override
    protected String creaVertice(VerticeArbolBinario<Integer> vertice, double x, double y) {
        ArbolRojinegro<Integer> arn = (ArbolRojinegro<Integer>) arbol;
        Color colorVertex = arn.getColor(vertice);
        String color = colorVertex == Color.NEGRO ? "Black" : "Red";
        String colorText = "White";
        String text = vertice.toString();
        text = text.substring(text.indexOf("{") + 1);
        text = text.substring(0, text.indexOf("}"));
        String svg = creaCirculo(x, y, color, colorBorde);
        svg += creaTexto(x, y, textTamaño, colorText, text);
        return svg;
    }
}
