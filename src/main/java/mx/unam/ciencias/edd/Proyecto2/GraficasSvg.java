package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

/*Clase para Graficas que extiende de EstructurasCirculares */
public class GraficasSvg extends EstructurasCirculares {
    /* Constructor por omisión */
    public GraficasSvg() {
    }

    /* Lista de nuestros vertices en la gráfica */
    private Lista<VerticeGrafica<Integer>> verticesGrafica = new Lista<>();
    /* Lista de nuestras coordenadas */
    private Lista<Coordenadas> ListaCoordenadas = new Lista<>();

    /* Color de nuestro vértice */
    private String color = "White";
    /* Color de nuestro borde y texto */
    private String colorBorde = "Black";
    /* Tamaño del texto */
    private int tamañoText = 20;
    /*
     * Tamaño de nuestro svg a crear, este será un cuadrado, por lo que solo es
     * necesario un lado
     */

    private int width;

    /* Método para crear un Svg a partir de una gráfica dada */
    public String CreaSvg(Grafica<Integer> grafica) {
        // Número de vertices de nuestra gráfica
        int numVertex = grafica.getElementos();
        // Nuestro caso "Especial" para calcular el tamaño de nuestro svg por si tenemos
        // menos elementos.
        if (numVertex < 4) {
            width = 200;
            if (numVertex == 1)
                width = 100;
        }
        // Fórmula para obtener nuestro width
        else
            width = numVertex * 100 - 200;

        /* El centro de nuestro cuadrado */
        int center = width / 2;
        /* El ángulo que tendrá cada vértice al armar nuestro poligono (Gráfica) */
        double angle = 2 * Math.PI / numVertex;
        /*
         * Utilizamos una lambda y el método paraCadaVértice para poder agregar nuestros
         * vértices a la lista
         */
        grafica.paraCadaVertice((v) -> verticesGrafica.agrega(v));

        // Un contador
        int i = 0;
        // Nuestra cadena que contendrá el svg
        String svg = "";
        // Graficamos los vertices y sus elementos.
        for (VerticeGrafica<Integer> vertice : verticesGrafica) {

            Coordenadas coordenadas = getCoordenadas(vertice, i, center, angle);
            double x = coordenadas.getX();
            double y = coordenadas.getY();

            ListaCoordenadas.agrega(coordenadas);
            svg += creaCirculo(x, y, color, colorBorde);
            svg += creaTexto(x, y, tamañoText, colorBorde, vertice.get().toString());

            i++;

            for (VerticeGrafica<Integer> vecino : vertice.vecinos()) {

                for (Coordenadas coordinates : ListaCoordenadas) {
                    if (coordinates.getElemento().equals(vecino.get().toString())) {
                        svg = creaLinea(x, y, coordinates.getX(), coordinates.getY()) + svg;
                    }
                }

            }

        }
        svg = creaSvg(width, width) + svg;

        return svg;

    }

    /* Método para calcular las coordenadas de un vértice */
    private Coordenadas getCoordenadas(VerticeGrafica<Integer> vertice, int i, int center, double angle) {
        double x;
        double y;

        x = center + (center - 50) * Math.cos(angle * i);
        y = center + (center - 50) * Math.sin(angle * i);
        return new Coordenadas(x, y, vertice.get().toString());
    }

    /*
     * Clase privada para representar las coordenadas y elemento de un vértice, no
     * posee setters, pues no es necesario cambiar los valores de un vértice ya
     * existente.
     */
    private class Coordenadas {
        // Nuestra coordenada x
        double x;
        // Nuestra coordenada y
        double y;
        // El elemento de nuestro vértice
        String elemento;

        /* Constructor público de nuestra clase Coordenadas */
        public Coordenadas(double x, double y, String elemento) {
            this.x = x;
            this.y = y;
            this.elemento = elemento;
        }

        /* Método para obtener la coordenada x */
        public double getX() {
            return x;
        }

        /* Método para obtener la coordenada y */
        public double getY() {
            return y;
        }

        /* Método para obtener el elemento */
        public String getElemento() {
            return elemento;
        }
    }

}
