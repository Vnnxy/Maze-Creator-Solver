package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;

public class Aplicacion {

    /* Constructor de la aplicación */
    public Aplicacion(String[] bandera) {
        try {
            ejecuta(bandera);
        } catch (IllegalArgumentException iae) {
            System.exit(0);
        }
    }

    /*
     * Método para obtener la estructura de la entrada y posteriormente crear y
     * llamar el método correspondiente a dicha estructura
     */
    public void obtenEstructura(Lista<String> entrada) {
        String estructura = entrada.eliminaPrimero();
        try {
            switch (estructura) {
                case "ArbolRojinegro":
                    ArbolRojinegro<Integer> arn = new ArbolRojinegro<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        arn.agrega(e);
                    }
                    RojinegrosSvg rn = new RojinegrosSvg(arn);
                    SvgEstructura.ensambla(rn.crea());

                    break;
                case "ArbolAVL":
                    ArbolAVL<Integer> avl = new ArbolAVL<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        avl.agrega(e);
                    }
                    Avl svgAvl = new Avl(avl);
                    SvgEstructura.ensambla(svgAvl.crea());
                    break;

                case "Cola":
                    Cola<Integer> cola = new Cola<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        cola.mete(e);
                    }
                    ColaSvg svgCola = new ColaSvg();
                    SvgEstructura.ensambla(svgCola.crea(entrada));

                    break;
                case "Pila":
                    Pila<Integer> pila = new Pila<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        pila.mete(e);
                    }
                    PilaSvg svgPila = new PilaSvg();
                    SvgEstructura.ensambla(svgPila.crea(entrada));
                    break;
                case "Lista":
                    Lista<Integer> lista = new Lista<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        lista.agregaFinal(e);
                    }
                    ListaSvg svgLista = new ListaSvg();
                    SvgEstructura.ensambla(svgLista.crea(entrada));

                    break;
                case "ArbolBinarioCompleto":
                    ArbolBinarioCompleto<Integer> abc = new ArbolBinarioCompleto<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        abc.agrega(e);
                    }
                    Abc complete = new Abc(abc);
                    SvgEstructura.ensambla(complete.crea());
                    break;
                case "ArbolBinarioOrdenado":
                    ArbolBinarioOrdenado<Integer> abo = new ArbolBinarioOrdenado<>();
                    for (String elem : entrada) {
                        Integer e = Integer.parseInt(elem);
                        abo.agrega(e);
                    }
                    Abo ordered = new Abo(abo);
                    SvgEstructura.ensambla(ordered.crea());
                    break;
                case "Grafica":
                    Grafica<Integer> grafica = new Grafica<>();
                    Lista<Integer> elementosAgregar = new Lista<>();
                    for (String i : entrada) {
                        if (!elementosAgregar.contiene(Integer.parseInt(i))) {
                            elementosAgregar.agrega(Integer.parseInt(i));
                            grafica.agrega(Integer.parseInt(i));
                        }
                    }
                    graficasCrea(grafica, entrada);
                    GraficasSvg gSvg = new GraficasSvg();
                    SvgEstructura.ensambla(gSvg.CreaSvg(grafica));
                    break;
                case "Arreglos":
                    ArreglosSvg arrSvg = new ArreglosSvg();
                    SvgEstructura.ensambla(arrSvg.crea(entrada));
                    break;

                default:
                    throw new IllegalArgumentException("Por favor especifica una estructura válida");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Por favor especifica una estructura válida");
            System.exit(1);
        }

    }

    private void graficasCrea(Grafica<Integer> grafica, Lista<String> vertices) {
        int cont = 1;
        Lista<String> solitarios = new Lista<>();
        Lista<String> conexos = new Lista<>();
        String previous = vertices.getPrimero();
        try {
            for (String i : vertices) {
                if (vertices.getElementos() % 2 != 0) {
                    System.out.println("La cantidad de elementos debe de ser par");
                    throw new IllegalArgumentException("iae");
                }
                if (cont % 2 == 0 && !previous.equals(i)) {
                    grafica.conecta(Integer.parseInt(previous), Integer.parseInt(i));
                    conexos.agregaFinal(i);
                    conexos.agregaFinal(previous);
                } else if (cont % 2 == 0 && previous.equals(i)) {
                    solitarios.agregaFinal(i);
                }

                if (solitarios.contiene(i) && conexos.contiene(i)) {
                    System.out.println("No puedes tener elementos desconectados y que sean conexos a la vez");
                    throw new IllegalArgumentException("IAE");
                }

                previous = i;
                cont++;

            }

        } catch (IllegalArgumentException iae) {
            System.exit(1);
        }

    }

    /* Ejecuta la aplicación. */
    public void ejecuta(String[] bandera) {
        if (bandera.length == 0 || bandera[0] == null) {
            obtenEstructura(Entradas.estandar());
        } else {
            obtenEstructura(Entradas.leelineas(bandera));
        }
    }

}
