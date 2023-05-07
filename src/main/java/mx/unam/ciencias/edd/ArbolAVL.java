package mx.unam.ciencias.edd;

/**
 * <p>
 * Clase para árboles AVL.
 * </p>
 *
 * <p>
 * Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.
 * </p>
 */
public class ArbolAVL<T extends Comparable<T>>
        extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * 
         * @return la altura del vértice.
         */
        @Override
        public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * 
         * @return una representación en cadena del vértice AVL.
         */
        @Override
        public String toString() {
            int balance = getBalance(this);
            return elemento.toString() + " " + altura + "/" + balance;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeAVL vertice = (VerticeAVL) objeto;
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
        super();
    }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    private VerticeAVL avl(Vertice v) {
        return (VerticeAVL) v;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeAVL vertice = avl(ultimoAgregado);
        if (vertice.padre == null)
            return;
        rebalenceoAvl(avl(vertice.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        VerticeAVL aELiminar = (VerticeAVL) busca(elemento);
        if (aELiminar == null)
            return;
        elementos--;
        if (aELiminar.derecho != null && aELiminar.izquierdo != null) {
            aELiminar = avl(intercambiaEliminable(aELiminar));
        }
        eliminaVertice(aELiminar);
        rebalenceoAvl(avl(aELiminar.padre));
    }

    private void setAltura(VerticeAVL vertice) {
        int alturaIzq = getAltura(vertice.izquierdo);
        int alturaDer = getAltura(vertice.derecho);
        vertice.altura = Math.max(alturaDer, alturaIzq) + 1;
    }

    private void rebalenceoAvl(VerticeAVL vertice) {
        if (vertice == null)
            return;
        setAltura(vertice);
        int balance = getBalance(vertice);

        if (balance == -2) {
            VerticeAVL derecho = avl(vertice.derecho);
            VerticeAVL nietoDer = avl(derecho.derecho);
            VerticeAVL nietoIzq = avl(derecho.izquierdo);

            if (getBalance(derecho) == 1) {
                super.giraDerecha(derecho);
                setAltura(nietoIzq);
                setAltura(derecho);
                derecho = avl(vertice.derecho);
                nietoDer = avl(derecho.derecho);
                nietoIzq = avl(derecho.izquierdo);

            }
            super.giraIzquierda(vertice);
            setAltura(derecho);
            setAltura(vertice);
        }
        if (balance == 2) {
            VerticeAVL izquierdo = avl(vertice.izquierdo);
            VerticeAVL nietoIzq = avl(izquierdo.izquierdo);
            VerticeAVL nietoDer = avl(izquierdo.derecho);

            if (getBalance(izquierdo) == -1) {
                super.giraIzquierda(izquierdo);
                setAltura(izquierdo);
                setAltura(nietoDer);
                izquierdo = avl(vertice.izquierdo);
                nietoDer = avl(izquierdo.derecho);
                nietoIzq = avl(izquierdo.izquierdo);

            }
            super.giraDerecha(vertice);
            setAltura(izquierdo);
            setAltura(vertice);
        }
        rebalenceoAvl(avl(vertice.padre));

    }

    private int getAltura(Vertice v) {
        return v == null ? -1 : v.altura();
    }

    private int getBalance(VerticeAVL vertice) {
        int alturaIzq = getAltura(vertice.izquierdo);
        int alturaDer = getAltura(vertice.derecho);
        return alturaIzq - alturaDer;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                "girar a la izquierda por el " +
                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                "girar a la derecha por el " +
                "usuario.");
    }
}
