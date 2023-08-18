package mx.unam.ciencias.edd.Proyecto3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.Lista;

/*Clase para leer la entrada de dos formas */
public class Entrada {

    /* Lee por entrada estándar */
    public static Lista<String> estandar() {
        Lista<String> l = new Lista<>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            l = formato(in);
        } finally {
        }
        return l;
    }

    /* Lee los contenidos de los archivos */
    public static Lista<String> leelineas(String[] rutas) {
        Lista<String> l = new Lista<>();
        for (int i = 0; i < rutas.length && rutas[i] != null; i++) {

            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(rutas[i])));
                l = formato(in);
            } catch (FileNotFoundException fnfe) {
                System.exit(1);
            }

        }
        return l;
    }

    /*
     * Metodo que elimina los comentarios que son denotados por "#", además, revisa
     * que la entrada solo contenga dígitos.
     * Nos regresa una lista con la estructura y los elementos.
     */
    private static Lista<String> formato(BufferedReader in) {
        Lista<String> lista = new Lista<>();
        String linea;
        try {
            while ((linea = in.readLine()) != null) {
                lista.agregaFinal(linea);

            }
        } catch (IOException ioe) {
        }
        return lista;
    }

    /* Método auxiliar para revisar si el string dado contiene un entero */
    private static boolean revisaNumeros(String entrada) {
        try {
            int i = Integer.parseInt(entrada);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
