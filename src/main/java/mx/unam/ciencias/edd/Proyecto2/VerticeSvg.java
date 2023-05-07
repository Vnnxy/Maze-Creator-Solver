package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.VerticeArbolBinario;

/*Clase para cada vertice en un árbol AVL */
public class VerticeSvg {
    private String color;
    private String balance;
    private Boolean izquierdo;

    /* Constructor de nuestra clase */
    public VerticeSvg(VerticeArbolBinario<Integer> vertice) {
        vertice.altura();
    }

    /* Método para obtener el color */
    public String getColor() {
        return color;
    }

    /* Método para obtener el balance */
    public String getBalance() {
        return balance;
    }

    /* Método para saber si es izquierdo */
    public Boolean esIzquierdo() {
        return izquierdo;
    }
}
