package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
        implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return indice < elementos;

        }

        /* Regresa el siguiente elemento. */
        @Override
        public T next() {
            if (indice >= elementos)
                throw new NoSuchElementException("nsee");
            return arbol[indice++];

        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T extends Comparable<T>>
            implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
            indice = -1;

        }

        /* Regresa el índice. */
        @Override
        public int getIndice() {
            return indice;
        }

        /* Define el índice. */
        @Override
        public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override
        public int compareTo(Adaptador<T> adaptador) {
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /*
     * Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
     * Java implementa sus genéricos; de otra forma obtenemos advertencias del
     * compilador.
     */
    @SuppressWarnings("unchecked")
    private T[] nuevoArreglo(int n) {
        return (T[]) (new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * 
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * 
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n        el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        arbol = nuevoArreglo(n);
        int i = 0;
        elementos = n;

        for (T elemento : iterable) {
            arbol[i] = elemento;
            elemento.setIndice(i);
            i++;
        }

        for (int h = n / 2 - 1; h >= 0; h--)
            acomodaAbajo(arbol, h);

    }

    private void acomodaArriba(T[] arreglo, int indice) {
        int indicePadre = (indice - 1) / 2;
        if (indice > 0 && arreglo[indice].compareTo(arreglo[indicePadre]) < 0) {
            intercambia(arreglo, indice, indicePadre);
            acomodaArriba(arreglo, indicePadre);
        }

    }

    private void acomodaAbajo(T[] arreglo, int indice) {
        int indexHijoI = 2 * indice + 1;
        int indexHijoD = 2 * indice + 2;
        int indexHijoMenor = indice;

        if (indexHijoI < elementos && arreglo[indexHijoMenor].compareTo(arreglo[indexHijoI]) > 0)
            indexHijoMenor = indexHijoI;

        if (indexHijoD < elementos && arreglo[indexHijoMenor].compareTo(arreglo[indexHijoD]) > 0)
            indexHijoMenor = indexHijoD;

        if (indexHijoMenor != indice) {
            intercambia(arreglo, indice, indexHijoMenor);
            acomodaAbajo(arreglo, indexHijoMenor);
        }
    }

    private void intercambia(T[] arr, int i, int m) {
        T elementoTemporal = arr[i];

        arr[i] = arr[m];
        arr[m] = elementoTemporal;

        arr[i].setIndice(i);
        arr[m].setIndice(m);
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * 
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override
    public void agrega(T elemento) {
        if (arbol.length == elementos) {
            T[] arbolViejo = arbol;
            arbol = nuevoArreglo(2 * elementos);
            for (int i = 0; i < elementos; i++) {
                arbol[i] = arbolViejo[i];
            }
        }

        arbol[elementos] = elemento;
        elemento.setIndice(elementos);
        acomodaArriba(arbol, elementos);
        elementos++;
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * 
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override
    public T elimina() {
        if (elementos == 0)
            throw new IllegalStateException("Monticulo vacío");

        T elementoMin = arbol[0];
        intercambia(arbol, 0, elementos - 1);
        elementoMin.setIndice(-1);
        arbol[elementos - 1] = null;
        elementos--;
        acomodaAbajo(arbol, 0);
        return elementoMin;
    }

    /**
     * Elimina un elemento del montículo.
     * 
     * @param elemento a eliminar del montículo.
     */
    @Override
    public void elimina(T elemento) {
        int index = elemento.getIndice();
        if (index < 0 || index >= elementos)
            return;

        intercambia(arbol, index, elementos - 1);
        arbol[elementos - 1].setIndice(-1);
        arbol[elementos - 1] = null;

        elementos--;

        if (index < elementos)
            reordena(arbol[index]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * 
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        int i = elemento.getIndice();
        if (i < 0 || i > elementos - 1)
            return false;
        if (arbol[i] == elemento)
            return true;
        return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * 
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override
    public void limpia() {
        for (int i = 0; i < elementos - 1; i++)
            arbol[i] = null;
        elementos = 0;
    }

    /**
     * Reordena un elemento en el árbol.
     * 
     * @param elemento el elemento que hay que reordenar.
     */
    @Override
    public void reordena(T elemento) {
        int indice = elemento.getIndice();
        acomodaAbajo(arbol, indice);
        acomodaArriba(arbol, indice);
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * 
     * @return el número de elementos en el montículo mínimo.
     */
    @Override
    public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * 
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *                                que el número de elementos.
     */
    @Override
    public T get(int i) {
        if (i < 0 || i > elementos - 1)
            throw new NoSuchElementException("nse");
        return arbol[i];

    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * 
     * @return una representación en cadena del montículo mínimo.
     */
    @Override
    public String toString() {
        String cadena = "";
        for (int i = 0; i < elementos; i++) {
            cadena += arbol[i] + ", ";
        }
        return cadena;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        MonticuloMinimo<T> monticulo = (MonticuloMinimo<T>) objeto;
        int min = arbol.length;
        if (arbol.length > monticulo.arbol.length)
            min = monticulo.arbol.length;
        for (int i = 0; i < min; i++) {
            if (!arbol[i].equals(monticulo.arbol[i]))
                return false;
        }
        return true;

    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * 
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * 
     * @param <T>       tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>> Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> l1 = new Lista<>();
        Lista<T> l2 = new Lista<>();
        for (T e : coleccion)
            l1.agregaFinal(new Adaptador<T>(e));

        MonticuloMinimo<Adaptador<T>> minHeap = new MonticuloMinimo<>(l1);

        while (!minHeap.esVacia()) {
            Adaptador<T> adap = minHeap.elimina();
            l2.agrega(adap.elemento);
        }
        return l2;
    }
}
