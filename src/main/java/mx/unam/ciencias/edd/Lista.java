package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase genérica para listas doblemente ligadas.
 * </p>
 *
 * <p>
 * Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.
 * </p>
 *
 * <p>
 * Las listas no aceptan a <code>null</code> como elemento.
 * </p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override
        public T next() {
            if (siguiente == null)
                throw new NoSuchElementException("Lista vacía");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override
        public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override
        public T previous() {
            if (anterior == null)
                throw new NoSuchElementException("Lista vacía");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override
        public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override
        public void end() {
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * 
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * 
     * @return el número elementos en la lista.
     */
    @Override
    public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * 
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean esVacia() {
        if (cabeza == null && rabo == null)
            return true;
        return false;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento vacío");
        Nodo n = new Nodo(elemento);
        if (rabo == null) {
            cabeza = n;
            rabo = n;
            longitud++;
        } else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
            longitud++;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento vacío");
        Nodo n = new Nodo(elemento);
        if (cabeza == null) {
            cabeza = n;
            rabo = n;
            longitud++;
        } else {
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
            longitud++;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * 
     * @param i        el índice dónde insertar el elemento. Si es menor que 0 el
     *                 elemento se agrega al inicio de la lista, y si es mayor o
     *                 igual
     *                 que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento vacío");
        else {
            if (i < 1)
                agregaInicio(elemento);
            else if (i > longitud - 1)
                agregaFinal(elemento);
            else {
                Nodo nodoIndice = buscaIndice(i);
                Nodo nuevoNodo = new Nodo(elemento);
                Nodo anterior = nodoIndice.anterior;

                nuevoNodo.anterior = anterior;
                anterior.siguiente = nuevoNodo;
                nuevoNodo.siguiente = nodoIndice;
                nodoIndice.anterior = nuevoNodo;
                longitud++;
            }
        }
    }

    private Nodo buscaIndice(int i) {
        if (cabeza == null)
            return null;
        int c = 0;
        Nodo n = cabeza;
        while (n != null) {
            if (c == i)
                return n;
            c++;
            n = n.siguiente;
        }
        return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Nodo nodo = buscaNodo(elemento);
        eliminaNodo(nodo);
    }

    private Nodo buscaNodo(T elemento) {
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            if (n.elemento.equals(elemento))
                return n;
        }
        return null;
    }

    private void eliminaNodo(Nodo nodo) {
        if (nodo == cabeza && nodo == rabo) {
            cabeza = rabo = null;
            longitud = 0;
        } else if (nodo == cabeza) {
            nodo.siguiente.anterior = null;
            cabeza = nodo.siguiente;
            longitud--;
        } else if (nodo == rabo) {
            nodo.anterior.siguiente = null;
            rabo = nodo.anterior;
            longitud--;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
            nodo.siguiente.anterior = nodo.anterior;
            longitud--;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * 
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (esVacia() == true)
            throw new NoSuchElementException("Lista vacía");
        T cab = cabeza.elemento;
        eliminaNodo(cabeza);
        return cab;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * 
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (esVacia() == true)
            throw new NoSuchElementException("Lista vacía");
        T rab = rabo.elemento;
        eliminaNodo(rabo);
        return rab;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * 
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        if (buscaNodo(elemento) == null)
            return false;
        return true;
    }

    /**
     * Regresa la reversa de la lista.
     * 
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reversaLista = new Lista<T>();
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            reversaLista.agregaInicio(n.elemento);
        }
        return reversaLista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * 
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copiaLista = new Lista<T>();
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            copiaLista.agregaFinal(n.elemento);
        }
        return copiaLista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override
    public void limpia() {
        cabeza = null;
        rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException("Lista es vacía");
        }
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (rabo == null) {
            throw new NoSuchElementException("Lista es vacía");
        }
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * 
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *                                 igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= longitud) {
            throw new ExcepcionIndiceInvalido("Indice invalido");
        }
        return buscaIndice(i).elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * 
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        if (buscaNodo(elemento) == null)
            return -1;
        int c = 0;
        for (Nodo n = cabeza; n.elemento != elemento; n = n.siguiente) {
            c++;
        }
        return c;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * 
     * @return una representación en cadena de la lista.
     */
    @Override
    public String toString() {
        if (cabeza == null) {
            return "[]";
        }
        String s = "[";
        Nodo n = cabeza;
        while (n != rabo) {
            s = s + n.elemento.toString() + "," + " ";
            n = n.siguiente;
        }
        s = s + rabo.elemento.toString() + "]";
        return s.toString();
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Lista<T> lista = (Lista<T>) objeto;
        if (getClass() == objeto.getClass()) {
            Nodo nodoListaUno = this.cabeza;
            Nodo nodoListaDos = lista.cabeza;

            if (lista == null)
                return false;
            if (this.longitud != lista.longitud)
                return false;
            if (this.longitud == lista.longitud) {
                while (nodoListaUno != null) {
                    if (nodoListaUno.elemento.equals(nodoListaDos.elemento)) {
                        nodoListaUno = nodoListaUno.siguiente;
                        nodoListaDos = nodoListaDos.siguiente;
                    } else
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * 
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * 
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * 
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        Lista<T> lista1 = new Lista<T>();
        Lista<T> lista2 = new Lista<T>();
        Nodo nodo = cabeza;
        int mitad = longitud / 2;
        if (longitud <= 1)
            return copia();
        for (int i = 0; i < mitad; i++) {
            lista1.agregaFinal(nodo.elemento);
            nodo = nodo.siguiente;
        }
        while (nodo != null) {
            lista2.agregaFinal(nodo.elemento);
            nodo = nodo.siguiente;
        }
        lista1 = lista1.mergeSort(comparador);
        lista2 = lista2.mergeSort(comparador);
        return mezcla(lista1, lista2, comparador);
    }

    private Lista<T> mezcla(Lista<T> listaUno, Lista<T> listaDos, Comparator<T> comparador) {
        Lista<T> nuevaLista = new Lista<T>();
        Nodo nodoUno = listaUno.cabeza;
        Nodo nodoDos = listaDos.cabeza;

        while (nodoUno != null && nodoDos != null) {
            if (comparador.compare(nodoUno.elemento, nodoDos.elemento) <= 0) {
                nuevaLista.agregaFinal(nodoUno.elemento);
                nodoUno = nodoUno.siguiente;
            } else {
                nuevaLista.agregaFinal(nodoDos.elemento);
                nodoDos = nodoDos.siguiente;
            }
        }

        while (nodoUno != null) {
            nuevaLista.agregaFinal(nodoUno.elemento);
            nodoUno = nodoUno.siguiente;
        }
        while (nodoDos != null) {
            nuevaLista.agregaFinal(nodoDos.elemento);
            nodoDos = nodoDos.siguiente;
        }
        return nuevaLista;

    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * 
     * @param <T>   tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * 
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        Nodo nodoCabeza = cabeza;
        while (nodoCabeza != null) {
            if (comparador.compare(nodoCabeza.elemento, elemento) == 0)
                return true;
            nodoCabeza = nodoCabeza.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * 
     * @param <T>      tipo del que puede ser la lista.
     * @param lista    la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
