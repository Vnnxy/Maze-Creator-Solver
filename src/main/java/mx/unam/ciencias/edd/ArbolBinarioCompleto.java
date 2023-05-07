package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>
 * Clase para árboles binarios completos.
 * </p>
 *
 * <p>
 * Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.
 * </p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<>();
            if (raiz != null)
                cola.mete(raiz);

        }

        /*
         * Nos di
         * e si hay un elemento siguiente.
         */
        @Override
        public boolean hasNext() {
            return !cola.esVacia();

        }

        /*
         * Regres
         * el siguiente elemento en orden BFS.
         */
        @Override
        public T next() {
            Vertice vertice = cola.saca();
            if (vertice.izquierdo != null)
                cola.mete(vertice.izquierdo);
            if (vertice.derecho != null)
                cola.mete(vertice.derecho);
            return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() {
        super();
    }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * 
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("IAE");
        Vertice nuevoVertice = nuevoVertice(elemento);
        elementos++;
        if (raiz == null) {
            raiz = nuevoVertice;
            return;
        }

        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        bfsAgrega(cola, nuevoVertice);

    }

    private void bfsAgrega(Cola<Vertice> cola, Vertice verticeAMeter) {
        Vertice vertice = cola.saca();
        if (!vertice.hayIzquierdo()) {
            vertice.izquierdo = verticeAMeter;
            verticeAMeter.padre = vertice;
            return;
        } else
            cola.mete(vertice.izquierdo);
        if (!vertice.hayDerecho()) {
            vertice.derecho = verticeAMeter;
            verticeAMeter.padre = vertice;
            return;
        } else
            cola.mete(vertice.derecho);
        bfsAgrega(cola, verticeAMeter);

    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Vertice aEliminar = vertice(busca(elemento));
        if (aEliminar == null)
            return;
        elementos--;
        if (elementos == 0) {
            raiz = null;
            return;
        }
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        Vertice hoyo = bfsElimina(cola, aEliminar);
        T elementoHoyo = hoyo.elemento;
        hoyo.elemento = aEliminar.elemento;
        aEliminar.elemento = elementoHoyo;

        if (hoyo == hoyo.padre.izquierdo)
            hoyo.padre.izquierdo = null;
        else
            hoyo.padre.derecho = null;
    }

    private Vertice bfsElimina(Cola<Vertice> cola, Vertice vertice) {
        Vertice v = cola.saca();
        if (v.hayIzquierdo())
            cola.mete(v.izquierdo);
        if (v.hayDerecho())
            cola.mete(v.derecho);
        if (cola.esVacia())
            return v;
        return bfsElimina(cola, vertice);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * 
     * @return la altura del árbol.
     */
    @Override
    public int altura() {
        return log(elementos);
    }

    private int log(int n) {
        if (n == 0)
            return -1;
        int alt = 0;
        while (n > 1) {
            n /= 2;
            alt++;
        }
        return alt;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()) {
            Vertice frente = cola.saca();
            accion.actua(frente);
            if (frente.izquierdo != null)
                cola.mete(frente.izquierdo);
            if (frente.derecho != null)
                cola.mete(frente.derecho);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
