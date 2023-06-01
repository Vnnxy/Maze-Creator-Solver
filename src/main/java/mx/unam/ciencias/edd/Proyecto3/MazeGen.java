package mx.unam.ciencias.edd.Proyecto3;

import java.io.IOException;
import java.util.Random;
import java.util.Random.*;
import mx.unam.ciencias.edd.*;

public class MazeGen {

    private GenCell[] grid;

    private int width;

    private Random random;

    public MazeGen(int width, int height, int seed) {
        this.width = width;
        this.random = new Random(seed);
        createGrid(width, height, random);
    }

    public GenCell[] getGrid() {
        return grid;
    }

    private void createGrid(int width, int height, Random rand) {
        grid = new GenCell[width * height];
        for (int i = 0; i < grid.length; i++) {
            int points = rand.nextInt(16);
            grid[i] = new GenCell(points);
        }
        growingTree(grid);
    }

    private void growingTree(GenCell[] grid) {
        Pila<GenCell> stack = new Pila<>();
        // We pick a random cell in our grid
        int i = random.nextInt(grid.length + 1);
        // System.out.println(i + "inciio");
        stack.mete(grid[i]);

        while (!stack.esVacia()) {
            GenCell currentCell = stack.mira();

            int dir = pickDirection(random);
            int nIndex = getNeighbour(i, dir);

            if (grid[nIndex].hasDoor() == false) {
                // System.out.println("Se avanzó a una celda sin puerta" + nIndex);
                stack.mete(grid[nIndex]);
                currentCell.setDoor(dir);
                currentCell.tocToc();
                grid[nIndex].setDoor(reverseDoor(dir));
            } else {
                dir = hasUnvisitedDoors(grid, i);
                if (dir > 0) {

                    nIndex = getIndex(i, dir);
                    // System.out.println("Esta direccion no tiene puertas:" + dir + "con indice : "
                    // + nIndex);
                    stack.mete(grid[nIndex]);
                    currentCell.setDoor(dir);
                    grid[nIndex].setDoor(reverseDoor(dir));
                } else if (dir < 0) {
                    stack.saca();
                    // System.out.println("saqué");
                }
            }
            i = nIndex;
        }
    }

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

    private int pickDirection(Random random) {
        return random.nextInt(4);
    }

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
        }
        return 0;
    }

    private int hasUnvisitedDoors(GenCell[] grid, int index) {
        int w = (index - 1 > 0) ? index - 1 : -1;
        int e = (index + 1 < grid.length) ? index + 1 : -1;
        int s = (index + width < grid.length) ? index + width : -1;
        int n = (index - width) > 0 ? index - width : -1;

        if (w > 0) {
            if (!grid[w].hasDoor())
                return 1;
        }
        if (s > 0) {
            if (!grid[s].hasDoor())
                return 0;
        }
        if (n > 0) {
            if (!grid[n].hasDoor())
                return 2;
        }
        if (e > 0) {
            if (!grid[e].hasDoor())
                return 3;
        }

        return -1;

    }

    private int getNeighbour(int index, int dir) {
        int neig = 0;
        switch (dir) {
            // south
            case 0: {
                neig = index + width;
                if (neig > grid.length)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("so");
                break;
            }
            // west
            case 1: {
                neig = index - 1;
                if (neig < 0)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("we");
                break;
            }
            // north
            case 2: {
                neig = index - width;
                if (neig < 0)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("no");
                break;
            }
            // east
            case 3: {
                neig = index + 1;
                if (neig > grid.length)
                    neig = getNeighbour(index, random.nextInt(4));
                // System.out.println("ea");
                break;
            }
        }
        return neig;
    }
}
