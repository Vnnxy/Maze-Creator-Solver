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
    /* The number of rows in our maze */
    private int height;
    /* The total number of cells in our maze */
    private int total;
    /* Our random number generator */
    private Random random;

    /* Our public constructor, it will create a maze, with a given size and seed */
    public MazeGen(int width, int height, int seed) {
        this.width = width;
        this.height = height;
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
        createInNOut();
    }

    /*
     * The algorithm we will be using for the creation of our maze, this algorithm
     * is made by jb, you can check all of his maze algorithms in:
     */
    private void growingTree(GenCell[] grid) {
        Pila<GenCell> stack = new Pila<>();
        // We pick a random cell in our grid
        int i = random.nextInt(grid.length - 1);
        stack.mete(grid[i]);
        // System.out.println("start " + i);
        int dir;

        while (!stack.esVacia()) {
            GenCell currentCell = stack.mira();
            currentCell.tocToc();
            int j = currentCell.getIndex();
            // We check for borders
            dir = borderPatrol(j);
            // System.out.println("Actual: " + j);
            // System.out.println("direccion: " + dir);
            int nIndex = getNeighbour(j, dir);

            if (grid[nIndex].hasDoor() == false) {
                // System.out.println("Me muevo a " + nIndex);

                stack.mete(grid[nIndex]);
                // System.out.println("Puerta actual");
                currentCell.setDoor(dir);
                // System.out.println("vecino indice: " + nIndex);
                grid[nIndex].setDoor(reverseDoor(dir));
            } else {
                dir = hasUnvisitedDoors(grid, j);
                nIndex = getNeighbour(j, dir);
                if (dir != -1 && grid[nIndex].hasDoor() == false) {
                    // System.out.println("Me muevo a " + nIndex);
                    // System.out.println("Me muevo a hh " + nIndex);
                    stack.mete(grid[nIndex]);
                    // System.out.println("Abro puerta");
                    currentCell.setDoor(dir);
                    // System.out.println("vecino indice: " + nIndex);
                    grid[nIndex].setDoor(reverseDoor(dir));
                } else {
                    stack.saca();

                }
            }
        }
    }

    private void createInNOut() {
        int entryIndex = width * random.nextInt(height);
        int exitIndex = (width - 1) + (width * random.nextInt(height));

        grid[entryIndex].setDoor(1);
        // System.out.println("Entrada: " + entryIndex);
        grid[exitIndex].setDoor(3);
        // System.out.println("Salida: " + exitIndex);
    }

    private int borderPatrol(int ind) {
        int dir;
        Boolean north = false;
        Boolean south = false;

        // We first check if is north or south, if the answer is neither, it will fall
        // through the statements
        // North check
        if (ind < width) {
            north = true;
            dir = getControlledRandom(2);// only 0 1 3 north
            // System.out.println("north border");

            // NorthEast 0 1
            if (isEastBorder(ind)) {
                return dir = random.nextInt(2);

            }
            // NorthWest 0 3
            else if (isWestBorder(ind)) {
                int h = random.nextInt(2);
                if (h == 0)
                    return dir = h;
                else
                    return dir = 3;

            }
            return dir;
        }
        // South check
        if (ind >= total - width) {

            south = true;
            dir = random.nextInt(3) + 1;// only 1 2 3 south
            // System.out.println("South border");

            // SouthEast 1 2
            if (isEastBorder(ind)) {
                // System.out.println("South east border");
                return dir = random.nextInt(2) + 1;

            }
            // SouthWest 2 3
            else if (isWestBorder(ind)) {
                return dir = random.nextInt(2) + 2;

            }

            return dir;
        }

        // <We now check if it is west or east border.

        else if (ind % width == 0) {
            return dir = getControlledRandom(1); // only 0 2 3 west

        }

        else if ((ind + 1) % (width) == 0 && ind + 1 < total) {
            return dir = random.nextInt(3); // only 0 1 2 east

        }
        // It is not a border
        else {
            dir = random.nextInt(4);
        }
        return dir;
    }

    private boolean isEastBorder(int i) {
        return ((i + 1) % (width) == 0);
    }

    private boolean isWestBorder(int i) {
        return (i % (width) == 0);
    }

    private int getControlledRandom(int i) {
        int dir = random.nextInt(4);
        if (dir != i) {
            return dir;
        }
        return getControlledRandom(i);

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
     * Auxiliary method, it will tell us if a given cell has any unvisited
     * neighbours
     */
    private int hasUnvisitedDoors(GenCell[] grid, int index) {
        int w = (index - 1 > 0) ? index - 1 : -1;
        int e = (index + 1 < grid.length) ? index + 1 : -1;
        int s = (index + width < grid.length) ? index + width : -1;
        int n = (index - width) > 0 ? index - width : -1;
        if (w > 0 && (index) % (width) != 0) {
            if (!grid[w].hasDoor())
                return 1;
        }
        if (s > 0) {
            if (!grid[s].hasDoor())
                return 0;
        }
        if (n > 0) { // Falla, no se por que
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
                if (neig > total)
                    neig = getNeighbour(index, random.nextInt(4) + 1);
                // System.out.println("neigbour is south");
                break;
            }
            // west
            case 1: {
                // if (index % (width - 1) == 0)
                // break;
                neig = index - 1;
                if (neig < 0)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("neigbour is west");
                break;
            }
            // north
            case 2: {
                // if (index < width)
                // break;
                neig = index - width;
                if (neig < 0)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("neigbour is north");
                break;
            }
            // east
            case 3: {
                // if (index % (width) == 0)
                // break;
                neig = index + 1;
                if (neig > grid.length)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("neigbour is east");
                break;
            }
        }
        return neig;
    }

}
