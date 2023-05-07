package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSortArray(arreglo, comparador, 0, arreglo.length - 1);
    }

    private static <T> void quickSortArray(T[] arreglo, Comparator<T> comparador, int index, int last) {
        int i = index + 1, j = last;
        if (last <= index)
            return;
        while (i < j) {
            if (comparador.compare(arreglo[i], arreglo[index]) > 0
                    && comparador.compare(arreglo[j], arreglo[index]) <= 0) {
                intercambia(arreglo, i++, j--);
            } else if (comparador.compare(arreglo[i], arreglo[index]) <= 0) {
                i++;
            } else {
                j--;
            }

        }
        if (comparador.compare(arreglo[i], arreglo[index]) > 0)
            i--;
        intercambia(arreglo, i, index);
        quickSortArray(arreglo, comparador, index, i - 1);
        quickSortArray(arreglo, comparador, i + 1, last);
    }

    private static <T> void intercambia(T[] arr, int i, int m) {
        T te = arr[i];
        arr[i] = arr[m];
        arr[m] = te;
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * 
     * @param <T>     tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length; i++) {
            int minimo = i;
            for (int j = i + 1; j < arreglo.length; j++)
                if (comparador.compare(arreglo[j], arreglo[minimo]) < 0)
                    minimo = j;
            intercambia(arreglo, i, minimo);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * 
     * @param <T>     tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo dónde buscar.
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinariaInt(arreglo, elemento, 0, arreglo.length - 1, comparador);
    }

    private static <T> int busquedaBinariaInt(T[] arreglo, T elemento, int a, int b, Comparator<T> comparador) {
        if (b < a)
            return -1;
        int mitad = (a + b) / 2;
        if (comparador.compare(arreglo[mitad], elemento) == 0)
            return mitad;
        else if (comparador.compare(elemento, arreglo[mitad]) < 0)
            return busquedaBinariaInt(arreglo, elemento, a, mitad - 1, comparador);
        else
            return busquedaBinariaInt(arreglo, elemento, mitad + 1, b, comparador);

    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>      tipo del que puede ser el arreglo.
     * @param arreglo  un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
