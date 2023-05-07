package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 * <li>Todos los vértices son NEGROS o ROJOS.</li>
 * <li>La raíz es NEGRA.</li>
 * <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la
 * raíz).</li>
 * <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 * <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 * mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
        extends ArbolBinarioOrdenado<T> {

    private boolean esRojo(VerticeRojinegro vertice) {
        return (vertice != null &&
                vertice.color == Color.ROJO);
    }

    private boolean esNegro(VerticeRojinegro vertice) {
        return (vertice == null ||
                vertice.color == Color.NEGRO);
    }

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override
        public String toString() {
            if (esNegro(this))
                return "N{" + elemento + "}";
            else
                return "R{" + elemento + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         * 
         * 
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro) objeto;
            return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *                            VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v.color;
    }

    private VerticeRojinegro verticeRn(Vertice vertice) {
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v;
    }

    private VerticeRojinegro obtenTio(VerticeRojinegro vertice) {
        if (vertice.padre.izquierdo == vertice)
            return verticeRn(vertice.padre.derecho);
        else
            return verticeRn(vertice.padre.izquierdo);
    }

    private Boolean esCruzado(VerticeRojinegro vertice, VerticeRojinegro padre, VerticeRojinegro abuelo) {
        if (abuelo.izquierdo == padre && padre.derecho == vertice
                || abuelo.derecho == padre && padre.izquierdo == vertice)
            return true;
        return false;
    }

    private Boolean esIzquierdo(VerticeRojinegro vertice) {
        VerticeRojinegro padre = verticeRn(vertice.padre);
        if (padre.izquierdo == vertice)
            return true;
        return false;
    }

    private VerticeRojinegro getPadre(VerticeRojinegro vertice) {
        if (vertice.padre == null)
            return null;
        return verticeRn(vertice.padre);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro vertice = verticeRn(ultimoAgregado);
        vertice.color = Color.ROJO;
        rebalanceaAgrega(vertice);

    }

    private void rebalanceaAgrega(VerticeRojinegro vertice) {

        if (vertice.padre == null) {
            vertice.color = Color.NEGRO;
            return;
        }

        VerticeRojinegro padre = getPadre(vertice);
        if (esNegro(padre))
            return;

        VerticeRojinegro tio = obtenTio(padre);
        VerticeRojinegro abuelo = getPadre(padre);
        if (esRojo(tio)) {
            padre.color = Color.NEGRO;
            tio.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalanceaAgrega(abuelo);
            return;
        }
        if (esCruzado(vertice, padre, abuelo)) {
            VerticeRojinegro temp = vertice;
            giraDireccion(padre);
            vertice = padre;
            padre = temp;
        }
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        giraReves(vertice, abuelo);
        return;
    }

    private void giraDireccion(VerticeRojinegro vertice) {
        if (esIzquierdo(vertice))
            super.giraIzquierda(vertice);

        else
            super.giraDerecha(vertice);

    }

    private void giraReves(VerticeRojinegro vertice, VerticeRojinegro verticeGiratorio) {
        if (esIzquierdo(vertice))
            super.giraDerecha(verticeGiratorio);

        else
            super.giraIzquierda(verticeGiratorio);

    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        VerticeRojinegro fantasma = null;
        VerticeRojinegro hijo;
        VerticeRojinegro aEliminar = (VerticeRojinegro) busca(elemento);
        elementos--;
        if (aEliminar == null)
            return;

        if (aEliminar.izquierdo != null && aEliminar.derecho != null) {
            aEliminar = verticeRn(intercambiaEliminable(aEliminar));
        }
        if (aEliminar.izquierdo == null && aEliminar.derecho == null) {
            fantasma = verticeRn(nuevoVertice(null));
            fantasma.color = Color.NEGRO;
            aEliminar.derecho = fantasma;
            fantasma.padre = aEliminar;
            hijo = fantasma;
        } else {
            hijo = obtenHijo(aEliminar);
        }
        eliminaVertice(aEliminar);

        if (esRojo(hijo) && esNegro(aEliminar)) {
            hijo.color = Color.NEGRO;
            return;
        }

        if (esNegro(aEliminar) && esNegro(hijo)) {
            rebalanceaElimina(hijo);
        }
        if (fantasma != null)
            eliminaVertice(fantasma);

    }

    private void rebalanceaElimina(VerticeRojinegro vertice) {
        if (vertice.padre == null)
            return;
        VerticeRojinegro padre = verticeRn(vertice.padre);
        VerticeRojinegro hermano = obtenHermano(vertice);

        if (esRojo(hermano)) {
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;
            giraDireccionDoble(vertice, padre);
            hermano = obtenHermano(vertice);
            padre = verticeRn(vertice.padre);
        }
        VerticeRojinegro hi = verticeRn(hermano.izquierdo);
        VerticeRojinegro hd = verticeRn(hermano.derecho);
        if (esNegro(padre) && esNegro(hermano) && esNegro(hi) && esNegro(hd)) {
            hermano.color = Color.ROJO;
            rebalanceaElimina(padre);
            return;
        }
        if (esRojo(padre) && esNegro(hermano) && esNegro(hi) && esNegro(hd)) {
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }
        if (esIzquierdo(vertice) && esRojo(hi) && esNegro(hd)) {
            hermano.color = Color.ROJO;
            hi.color = Color.NEGRO;
            giraReves(vertice, hermano);
            hermano = obtenHermano(vertice);
            hi = verticeRn(hermano.izquierdo);
            hd = verticeRn(hermano.derecho);

        } else if ((!esIzquierdo(vertice)) && esNegro(hi) && esRojo(hd)) {
            hermano.color = Color.ROJO;
            hd.color = Color.NEGRO;
            giraReves(vertice, hermano);
            hermano = obtenHermano(vertice);
        }
        hi = verticeRn(hermano.izquierdo);
        hd = verticeRn(hermano.derecho);

        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        if (esIzquierdo(vertice))
            hd.color = Color.NEGRO;
        else {
            hi.color = Color.NEGRO;
        }
        giraDireccionDoble(vertice, padre);

    }

    private VerticeRojinegro obtenHermano(VerticeRojinegro vertice) {
        if (esIzquierdo(vertice))
            return verticeRn(vertice.padre.derecho);
        else
            return verticeRn(vertice.padre.izquierdo);
    }

    private VerticeRojinegro obtenHijo(VerticeRojinegro vertice) {
        if (vertice.derecho != null)
            return verticeRn(vertice.derecho);
        return verticeRn(vertice.izquierdo);
    }

    private void giraDireccionDoble(VerticeRojinegro vertice, VerticeRojinegro verticeAgirar) {
        if (esIzquierdo(vertice))
            super.giraIzquierda(verticeAgirar);

        else
            super.giraDerecha(verticeAgirar);

    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                "pueden girar a la izquierda " +
                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                "pueden girar a la derecha " +
                "por el usuario.");
    }
}
