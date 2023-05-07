package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>
 * Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.
 * </p>
 *
 * <p>
 * Un árbol instancia de esta clase siempre cumple que:
 * </p>
 * <ul>
 * <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 * descendientes por la izquierda.</li>
 * <li>Cualquier elemento en el árbol es menor o igual que todos sus
 * descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
        extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<>();
            for (Vertice v = raiz; v != null; v = v.izquierdo)
                pila.mete(v);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            Vertice v = pila.saca();
            T elem = v.elemento;
            if (v.derecho != null) {
                pila.mete(v.derecho);
                v = v.derecho;
                while (v != null) {
                    if (v.izquierdo != null)
                        pila.mete(v.izquierdo);
                    v = v.izquierdo;
                }
            }
            return elem;

        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("IAE");
        Vertice vertice = nuevoVertice(elemento);
        elementos++;
        ultimoAgregado = vertice;
        if (raiz == null) {
            raiz = vertice;
            return;
        }
        agregaReferencias(raiz, vertice);

    }

    private void agregaReferencias(Vertice actual, Vertice nuevo) {
        if (nuevo.elemento.compareTo(actual.elemento) <= 0) {
            if (actual.izquierdo == null) {
                actual.izquierdo = nuevo;
                nuevo.padre = actual;
                return;
            } else
                agregaReferencias(actual.izquierdo, nuevo);
        } else {
            if (actual.derecho == null) {
                actual.derecho = nuevo;
                nuevo.padre = actual;
            } else
                agregaReferencias(actual.derecho, nuevo);
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Vertice v = vertice(busca(elemento));
        if (v == null)
            return;
        if (v.izquierdo != null && v.derecho != null) {
            v = intercambiaEliminable(v);
            eliminaVertice(v);
        } else
            eliminaVertice(v);
        elementos--;

    }

    private Vertice maximoEnSubarbol(Vertice v) {
        if (v.derecho == null)
            return v;
        return maximoEnSubarbol(v.derecho);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * 
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice aIntercambiar = maximoEnSubarbol(vertice.izquierdo);
        T leafElement = aIntercambiar.elemento;
        aIntercambiar.elemento = vertice.elemento;
        vertice.elemento = leafElement;
        return aIntercambiar;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * 
     * 
     * 
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice son = vertice.derecho == null ? vertice.izquierdo : vertice.derecho;
        Vertice p = vertice.padre;
        if (p != null) {
            if (p.izquierdo == vertice)
                p.izquierdo = son;
            else
                p.derecho = son;
        } else if (p == null)
            raiz = son;
        if (son != null)
            son.padre = vertice.padre;

    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * 
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override
    public VerticeArbolBinario<T> busca(T elemento) {
        return buscaAux(raiz, elemento);

    }

    private VerticeArbolBinario<T> buscaAux(Vertice v, T elemento) {
        if (v == null)
            return null;
        if (elemento.compareTo(v.elemento) == 0)
            return v;
        else if (elemento.compareTo(v.elemento) < 0)
            return buscaAux(v.izquierdo, elemento);
        else
            return buscaAux(v.derecho, elemento);

    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * 
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice q = vertice(vertice);
        if (q.izquierdo == null)
            return;
        Vertice p = q.izquierdo;
        Vertice padre = q.padre;
        p.padre = padre;

        if (q == raiz) {
            raiz = p;
        } else {
            if (padre.izquierdo == q)
                padre.izquierdo = p;
            else
                padre.derecho = p;
        }

        q.izquierdo = p.derecho;

        if (p.derecho != null) {
            p.derecho.padre = q;
        }

        q.padre = p;
        p.derecho = q;

    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice p = vertice(vertice);
        if (p.derecho == null)
            return;
        Vertice q = p.derecho;
        Vertice padre = p.padre;
        q.padre = padre;

        if (p == raiz) {
            raiz = q;
        } else {
            if (padre.izquierdo == p)
                padre.izquierdo = q;
            else
                padre.derecho = q;

        }

        p.derecho = q.izquierdo;
        if (q.izquierdo != null) {
            q.izquierdo.padre = p;
        }
        p.padre = q;
        q.izquierdo = p;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        accionDfsPreOrder(accion, raiz);

    }

    private void accionDfsPreOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
        if (v == null)
            return;
        accion.actua(v);
        accionDfsPreOrder(accion, v.izquierdo);
        accionDfsPreOrder(accion, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        accionDfsInOrder(accion, raiz);
    }

    private void accionDfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
        if (v == null)
            return;

        accionDfsInOrder(accion, v.izquierdo);
        accion.actua(v);
        accionDfsInOrder(accion, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        accionDfsPostOrder(accion, raiz);
    }

    private void accionDfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
        if (v == null)
            return;

        accionDfsPostOrder(accion, v.izquierdo);
        accionDfsPostOrder(accion, v.derecho);
        accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
