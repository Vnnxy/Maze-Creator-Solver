package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /*
         * Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario.
         */
        public Iterador() {
            indice = 0;
            while (indice < entradas.length) {
                if (entradas[indice] != null) {
                    iterador = entradas[indice].iterator();
                    return;
                }
                indice++;
            }
            iterador = null;
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            return iterador != null;
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            Entrada entry = iterador.next();
            if (!iterador.hasNext()) {
                indice++;
                while (indice < entradas.length) {
                    if (entradas[indice] != null) {
                        iterador = entradas[indice].iterator();
                        return entry;
                    }
                    indice++;
                }
                iterador = null;

            }

            return entry;
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
            implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override
        public K next() {
            return siguiente().llave;
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
            implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override
        public V next() {
            return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /*
     * Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
     * Java implementa sus genéricos; de otra forma obtenemos advertencias del
     * compilador.
     */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[]) Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * 
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * 
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * 
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        this.dispersor = dispersor;
        int capacity = (capacidad < MINIMA_CAPACIDAD) ? MINIMA_CAPACIDAD : capacidad;
        int pow = 1;
        while (pow < capacity * 2) {
            pow *= 2;
        }
        entradas = nuevoArreglo(pow);

    }

    private int dispersa(K llave) {
        return dispersor.dispersa(llave) & (entradas.length - 1);
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * 
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        if (llave == null || valor == null)
            throw new IllegalArgumentException("iae");

        int i = dispersa(llave);
        Entrada entrada = new Entrada(llave, valor);

        if (entradas[i] == null) {
            entradas[i] = new Lista<Entrada>();
            entradas[i].agrega(entrada);
            elementos++;
        } else {
            Entrada pastEntry = findEntrada(llave);
            if (pastEntry == null) {
                entradas[i].agrega(entrada);
                elementos++;
            } else
                pastEntry.valor = valor;
        }

        if (carga() >= MAXIMA_CARGA) {

            Lista<Entrada>[] temp = entradas;
            entradas = nuevoArreglo(entradas.length * 2);
            elementos = 0;

            for (int j = 0; j < temp.length; j++) {
                if (temp[j] != null) {
                    for (Entrada en : temp[j]) {
                        agrega(en.llave, en.valor);
                    }
                }

            }
        }

    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * 
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException   si la llave no está en el diccionario.
     */
    public V get(K llave) {
        if (llave == null)
            throw new IllegalArgumentException("iae");

        int i = dispersa(llave);
        if (entradas[i] == null)
            throw new NoSuchElementException("nse");
        Entrada entry = findEntrada(llave);

        if (entry == null)
            throw new NoSuchElementException("nse");

        return entry.valor;

    }

    private Entrada findEntrada(K llave) {
        int i = dispersa(llave);
        for (Entrada e : entradas[i]) {
            if (e.llave.equals(llave))
                return e;
        }
        return null;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * 
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        if (llave == null)
            return false;
        int i = dispersa(llave);
        if (llave == null || entradas[i] == null)
            return false;
        return findEntrada(llave) != null;

    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * 
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException   si la llave no se encuentra en
     *                                  el diccionario.
     */
    public void elimina(K llave) {
        if (llave == null)
            throw new IllegalArgumentException("iae");
        int i = dispersa(llave);
        if (entradas[i] == null)
            throw new NoSuchElementException("nse");

        Entrada entry = findEntrada(llave);
        if (entry == null)
            throw new NoSuchElementException("nse");

        entradas[i].elimina(entry);
        if (entradas[i].getLongitud() == 0)
            entradas[i] = null;
        elementos--;

    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * 
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        int sum = 0;
        for (Lista<Entrada> list : entradas)
            if (list != null) {
                sum += list.getLongitud() - 1;
            }
        return sum;

    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * 
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        int maxCol = 0;
        for (Lista<Entrada> list : entradas) {
            if (list != null) {
                int length = list.getLongitud();
                maxCol = (maxCol > length) ? maxCol : length;
            }
        }
        return maxCol - 1;
    }

    /**
     * Nos dice la carga del diccionario.
     * 
     * @return la carga del diccionario.
     */
    public double carga() {
        return Double.valueOf(elementos) / (entradas.length);
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * 
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * 
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        entradas = nuevoArreglo(entradas.length - 1);
        elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * 
     * @return una representación en cadena del diccionario.
     */
    @Override
    public String toString() {
        if (elementos == 0)
            return "{}";
        Iterador iterador = new Iterador();
        String cadena = "{ ";
        while (iterador.hasNext()) {
            Entrada entry = iterador.siguiente();
            cadena += "'" + entry.llave.toString() + "'" + ": " + "'" + entry.valor.toString() + "'" + ", ";
        }
        cadena += "}";
        return cadena;
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * 
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Diccionario<K, V> d = (Diccionario<K, V>) o;
        if (elementos != d.elementos)
            return false;
        Iterador iterador = new Iterador();
        while (iterador.hasNext()) {
            Entrada entradaOriginal = iterador.siguiente();
            if (!d.contiene(entradaOriginal.llave) || !d.get(entradaOriginal.llave).equals(entradaOriginal.valor))
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * 
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * 
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override
    public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
