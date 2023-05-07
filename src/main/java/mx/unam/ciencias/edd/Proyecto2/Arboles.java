package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Cola;

/*Clase abstracta para árboles */
public abstract class Arboles extends EstructurasCirculares {

    /* Nuestro Árbol */
    protected ArbolBinario<Integer> arbol;

    /* La raiz de nuestro árbol */
    protected VerticeArbolBinario<Integer> raiz;

    /* La altura de nuestro Árbol */
    protected int alturaArbol;

    /* Constructor público para nuestra clase Arboles */
    public Arboles(ArbolBinario<Integer> arbol) {
        this.arbol = arbol;
        this.raiz = arbol.raiz();
        this.alturaArbol = arbol.altura();
    }

    /* Cola para realizar BFS sobre el árbol */
    protected Cola<VerticeArbolBinario<Integer>> elementos = new Cola<>();

    /* Cola para obtener las coordenadas */
    protected Cola<Coordenadas> coordenadas = new Cola<>();

    /* Coordenadas iniciales para cualquier vértice */
    protected double x = 100;

    /* Coordenadas para el balance en el caso de arboles AVL */
    protected double xAvl;

    /* Tamaño de nuestro texto */
    protected int textTamaño = 20;

    /* El color de nuestros vértices */
    protected String color = "White";
    /* El color del borde de nuestros vértices */
    protected String colorBorde = "Black";
    /* El color de nuestro texto */
    protected String colorText = "Black";

    /* El código svg con el que trabajaremos */
    protected String svgCode = "";

    /* Método que recorre por medio de BFS a nuestro árbol y crea el svg */
    public String crea() {
        if (raiz == null)
            return "";

        int height = 100 + alturaArbol * 100;
        int width = 100 * (int) (Math.pow(2, alturaArbol));
        elementos.mete(raiz);
        double cxPadre = width;
        double cyPadre = 0;
        double base = width / 2;
        double yCambio = 50;
        double x = 0;
        while (!elementos.esVacia()) {
            VerticeArbolBinario<Integer> verticeActual = elementos.saca();
            double y = (verticeActual.profundidad() * 100) + 50;

            if (y != yCambio) {
                base = base / 2;
            }
            yCambio = y;

            // Caso para la raiz
            if (!esDerecho(verticeActual) && !esIzquierdo(verticeActual)) {
                x = cxPadre / 2;
                xAvl = x;
                svgCode += creaVertice(verticeActual, x, y);
            } else {
                cxPadre = coordenadas.saca().getX();
                cyPadre = y - 100;

                if (esIzquierdo(verticeActual)) {
                    x = cxPadre - base;
                    xAvl = x - 10;
                    svgCode = creaLinea(cxPadre, cyPadre, x, y) + svgCode;
                    svgCode += creaVertice(verticeActual, x, y);

                } else {
                    x = cxPadre + base;
                    xAvl = x + 10;
                    svgCode = creaLinea(cxPadre, cyPadre, x, y) + svgCode;
                    svgCode += creaVertice(verticeActual, x, y);

                }

            }
            // Metemos el vertice izquierdo más las coordenadas de su padre
            if (verticeActual.hayIzquierdo()) {
                elementos.mete(verticeActual.izquierdo());
                coordenadas.mete(new Coordenadas(x, y));
            }
            // Metemos el vertice derecho más las coordenadas de su padre
            if (verticeActual.hayDerecho()) {
                elementos.mete(verticeActual.derecho());
                coordenadas.mete(new Coordenadas(x, y));
            }
        }
        svgCode = creaSvg(height, width) + svgCode;
        return svgCode;
    }

    /*
     * Método protegido para crear un vértice, el cual consiste de crear un círculo
     * y el texto dentro de el
     */
    protected String creaVertice(VerticeArbolBinario<Integer> vertice, double x, double y) {
        String svg = creaCirculo(x, y, color, colorBorde);
        svg += creaTexto(x, y, textTamaño, colorText, vertice.toString());
        return svg;
    }

    /*
     * Método para saber si el vértice es izquierdo (necesitamos ambos casos para
     * poder ver el caso de la raiz
     */
    private boolean esIzquierdo(VerticeArbolBinario<Integer> v) {
        if (!v.hayPadre())
            return false;
        VerticeArbolBinario<Integer> verticePadre = v.padre();
        if (verticePadre.hayIzquierdo())
            if (verticePadre.izquierdo() == v)
                return true;
        return false;
    }

    /*
     * Método para saber si el vértice es derecho (necesitamos ambos casos para
     * poder ver el caso de la raiz)
     */
    private boolean esDerecho(VerticeArbolBinario<Integer> v) {
        if (!v.hayPadre())
            return false;
        VerticeArbolBinario<Integer> verticePadre = v.padre();
        if (verticePadre.hayDerecho())
            if (verticePadre.derecho() == v)
                return true;
        return false;
    }

    /* Clase para saber las coordenadas de cada vértice */
    private class Coordenadas {
        double x;
        double y;

        /* Constructor público para nuestra clase interna */
        public Coordenadas(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /* Nos regresa la coordenada x */
        public double getX() {
            return x;
        }

        /* Nos regresa la coordenada y */
        public double getY() {
            return y;
        }

    }
}
