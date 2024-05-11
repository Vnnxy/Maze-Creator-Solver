# Maze Builder/Solver

## Description

This project was part of the Data Structures course by Canek Peláez Valdés
We use graphs and other data structures to create a maze. Every maze is different and it is built by a given seed, meaning that if a specific seed is used, the same maze will be created. Aditionally, a solution with the lowest cost made by using the Dijkstra algorrithm will be given. All of this is presented as an svg.

## Demo of a 40 x 40 Maze:
![Maze40x40](/solution.svg)

## To start the application

### Requirements:

This project uses Maven 3.8.6 and Java 17.0.5.

### To install the project:

```bash
$ mvn install
```

### Generating a maze:

The .mze extension contains data in binary form.

```bash
$ java -jar target/proyecto3.jar -g -s 1234 -w 100 -h 100 > test.mze
```

In this example, it will create a .mze file for a maze with 40x40 dimensions.

Where:

- -g indicates that we are generating a maze.
- -s followed by a number is optional. It indicates the seed we will be using.
- -w Followed by a number will indicate the number of columns of our maze (Max 255)
- -h Followed by a number will indicate the number of rows of our maze (Max 255)
- \> test.mze will create the file with the name "test.mze"

### Solving the maze:

```bash
$ java -jar target/proyecto3.jar < test.mze > solution.svg
```

### or:

```bash
$ cat test.mze | java -jar target/proyecto3.jar > solution.svg
```

This will create an svg file with the soulution.

- \> indicates the name of the file where we want to save the solution.

## Additional notes:

- All mazes have the .mze extension.
- The input is computed in O(nlogn).
- To make a solution it is required that columns and rows are equal.
- Each room of the maze contains points which cost the player. Therefore, the given solution will be the one with the lowest score.
