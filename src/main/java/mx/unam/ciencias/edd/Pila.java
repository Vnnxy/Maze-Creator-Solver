package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * 
     * @return una representación en cadena de la pila.
     */
    @Override
    public String toString() {
        String representacionPila = "";
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            representacionPila += n.elemento.toString() + "\n";
        }
        return representacionPila;
    }

    /**
     * Agrega un elemento al tope de la pila.
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
        if (cabeza == null) {
            cabeza = nodoAMeter;
            rabo = nodoAMeter;
        } else {
            nodoAMeter.siguiente = cabeza;
            cabeza = nodoAMeter;
        }
    }
}
