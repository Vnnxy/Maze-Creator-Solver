package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * 
     * @return una representación en cadena de la cola.
     */
    @Override
    public String toString() {
        String representacionCola = "";
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            representacionCola += n.elemento.toString() + ",";
        }
        return representacionCola;
    }

    /**
     * Agrega un elemento al final de la cola.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void mete(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("iae");

        Nodo nodoAMeter = new Nodo(elemento);
        if (rabo == null)
            cabeza = rabo = nodoAMeter;
        else {
            rabo.siguiente = nodoAMeter;
            rabo = nodoAMeter;
        }
    }
}
