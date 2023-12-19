# Proyecto 3 - Estructuras de Datos

## Descripción
Este proyecto es parte de la asignatura de Estructuras de Datos impartida por el profesor Canek Peláez Valdés en la Facultad de Ciencias de la UNAM, durante el semestre 2023-2. La aplicación genera laberintos a partir de una semilla dada y regresa su representación en formato SVG. Además, brinda la opción de resolver los laberintos y mostrar gráficamente su solución.

## Uso

### Generar un laberinto:
```bash
$ java -jar target/proyecto3.jar -g -s 1234 -w 100 -h 100 > ejemplo.mze
```
En donde:
+ -g indica que vamos a generar un laberinto
+ -s seguido de un número es opcional, este nos indica la semilla que utilizaremos.
+ -w seguido de un número nos indica el número de columnas del laberinto. (Max 255)
+ -h seguido de un número nos indica el número de renglones del laberinto. (Max 255)
+ \> ejemplo.mze Crea el archivo con nombre ejemplo.mze

### Resolver el laberinto:
```bash
$ java -jar target/proyecto3.jar < ejemplo.mze > solucion.svg
```
### o equivalentemente:
```bash
$ cat ejemplo.mze | java -jar target/proyecto3.jar > solucion.svg
```
## Notas:
- Los laberintos tienen terminación .mze
- Se procesa la entrada en tiempo O(nlogn)
- Se utilizan las estructuras de datos implementadas durante todo el curso.
- La solución solo sirve en laberinto con mismo número de columnas y renglones.
- Hay puntajes en cada habitación, por lo que el programa buscará la solución que menos puntos requiera.
