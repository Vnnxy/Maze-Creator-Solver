package mx.unam.ciencias.edd.Proyecto3;

import java.io.IOException;
import java.util.Random;
import java.util.Random.*;

import mx.unam.ciencias.edd.*;

public class MazeGen {
    /* Our array representing the grid in which the maze will be displayed */
    private GenCell[] grid;
    /* The number of columns in our maze */
    private int width;
    /* The total number of cells in our maze */
    private int total;
    /* Our random number generator */
    private Random random;

    /* Our public constructor, it will create a maze, with a given size and seed */
    public MazeGen(int width, int height, int seed) {
        this.width = width;
        this.random = new Random(seed);
        createGrid(width, height, random);
    }

    /* Returns our current grid */
    public GenCell[] getGrid() {
        return grid;
    }

    /* As counterintuitive as it may sound, it creates a maze */
    private void createGrid(int width, int height, Random rand) {
        total = width * height;
        grid = new GenCell[total];

        for (int i = 0; i < grid.length; i++) {
            int points = rand.nextInt(16);
            grid[i] = new GenCell(points, i);
        }
        growingTree(grid);
    }

    /*
     * The algorithm we will be using for the creation of our maze, this algorithm
     * is made by jb, you can check all of his maze algorithms in:
     */
    private void growingTree(GenCell[] grid) {
        Pila<GenCell> stack = new Pila<>();
        // We pick a random cell in our grid
        int i = random.nextInt(grid.length - 1);
        System.out.println(i + "inciio");
        stack.mete(grid[i]);

        while (!stack.esVacia()) {
            GenCell currentCell = stack.mira();
            int dir;
            int j = currentCell.getIndex();

            // We check for borders
            dir = borderPatrol(j);
            int nIndex = getNeighbour(j, dir);

            if (grid[nIndex].hasDoor() == false) {
                System.out.println("Se avanzó a una celda sin puerta" + nIndex);
                stack.mete(grid[nIndex]);

                currentCell.setDoor(dir);
                currentCell.tocToc();
                grid[nIndex].setDoor(reverseDoor(dir));
            } else {
                dir = hasUnvisitedDoors(grid, j);
                System.out.println("else dir: " + dir);
                nIndex = getNeighbour(j, dir);
                if (dir > -1 && grid[nIndex].hasDoor() == false) { // Si pongo dir != 1 si funciona pero no se por que
                    System.out.println("Else Se avanzó a una celda sin puerta" + nIndex);
                    stack.mete(grid[nIndex]);
                    currentCell.setDoor(dir);
                    currentCell.tocToc();
                    grid[nIndex].setDoor(reverseDoor(dir));
                } else if (dir < 0) {
                    stack.saca();
                    System.out.println("saqué");
                }
            }
        }
    }

    private int borderPatrol(int ind) {
        int dir;
        Boolean north = false;
        Boolean south = false;
        Boolean east = false;
        Boolean west = false;

        // We first check if is north or south, if the answer is neither, it will fall
        // through the statements
        // North check
        if (ind < width) {
            System.out.println("north is border");
            north = true;
            dir = getControlledRandom(2);// only 0 1 3 north

            // NorthEast 0 1
            if (isEastBorder(ind)) {
                dir = random.nextInt(2);
            }
            // NorthWest 0 3
            else if (isWestBorder(ind)) {
                int h = random.nextInt(2);
                if (h == 0)
                    dir = h;
                else
                    dir = 3;
            }
        }
        // South check
        if (ind > total - width) {
            System.out.println("south is border");
            south = true;
            dir = random.nextInt(3 + 1);// only 1 2 3 south

            // SouthEast 1 2
            if (isEastBorder(ind)) {
                dir = random.nextInt(2) + 1;
            }
            // SouthWest 2 3
            else if (isWestBorder(ind)) {
                dir = random.nextInt(2) + 2;
            }
        }

        // <We now check if it is west or east border.

        else if (ind % width == 0) {
            System.out.println("west is border");
            dir = getControlledRandom(1); // only 0 2 3 west
        }

        else if ((ind + 1) % (width) == 0 && ind + 1 < total) {
            System.out.println("east is border");
            dir = random.nextInt(3); // only 0 1 2 east
        }
        // It is not a border
        else {
            dir = random.nextInt(4);
        }
        return dir;
    }

    private boolean isEastBorder(int i) {
        return (i + 1 % (width) == 0 && +i + 1 < total);
    }

    private boolean isWestBorder(int i) {
        return (i % (width) == 0);
    }

    private int getControlledRandom(int i) {
        int dir = random.nextInt(4);
        if (dir == i) {
            dir = getControlledRandom(i);
        }
        return dir;

    }

    /*
     * Private method that gives us the opposite direction of a door, this will be
     * used for creating the neighbours door
     */
    private int reverseDoor(int dir) {
        switch (dir) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
        }
        return -1;
    }

    /*
     * Auxiliary method we will use to get the index of the neighbour, depending on
     * the direction we are facing
     */
    private int getIndex(int index, int dir) {
        switch (dir) {
            case 0:
                return index + width;
            case 1:
                return index - 1;
            case 2:
                return index - width;
            case 3:
                return index + 1;
            default:
                return -1;
        }
    }

    /*
     * Auxiliary method, it will tell us if a given cell has any unvisited
     * neighbours
     */
    private int hasUnvisitedDoors(GenCell[] grid, int index) {
        int w = (index - 1 > 0) ? index - 1 : -1;
        int e = (index + 1 < grid.length) ? index + 1 : -1;
        int s = (index + width < grid.length) ? index + width : -1;
        int n = (index - width) > 0 ? index - width : -1;

        System.out.println("w: " + w + "e: " + e + "s: " + s + "n: " + n);

        if (w > 0 && (index - 1) % (width - 1) != 0) {
            if (!grid[w].hasDoor())
                return 1;
        }
        if (s > 0 && (index + width) <= total - width) {
            if (!grid[s].hasDoor())
                return 0;
        }
        if (n > 0 && (index - width) >= (width)) { // Falla, no se por que
            if (!grid[n].hasDoor())
                return 2;
        }
        if (e > 0 && (index + 1) % (width) != 0) {
            if (!grid[e].hasDoor())
                return 3;
        }

        return -1;

    }

    /*
     * It will give us the neighbour index depending the direction this may be
     * repeated
     */
    private int getNeighbour(int index, int dir) {
        int neig = 0;
        switch (dir) {
            // south
            case 0: {
                // if (index > total - width)
                // break;
                neig = index + width;
                if (neig > grid.length)
                    neig = getNeighbour(index, random.nextInt(4));
                System.out.println("so");
                break;
            }
            // west
            case 1: {
                // if (index % (width - 1) == 0)
                // break;
                neig = index - 1;
                if (neig < 0)
                    neig = getNeighbour(index, random.nextInt(4));
                System.out.println("we");
                break;
            }
            // north
            case 2: {
                // if (index < width)
                // break;
                neig = index - width;
                if (neig < 0)
                    neig = getNeighbour(index, random.nextInt(4));
                System.out.println("no");
                break;
            }
            // east
            case 3: {
                // if (index % (width) == 0)
                // break;
                neig = index + 1;
                if (neig > grid.length)
                    neig = getNeighbour(index, random.nextInt(4));
                System.out.println("ea");
                break;
            }
        }
        return neig;
    }

}
