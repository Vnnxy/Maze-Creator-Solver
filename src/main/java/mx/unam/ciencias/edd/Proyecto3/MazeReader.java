package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Grafica;

public class MazeReader {
    public MazeReader() {
    }

    /* Array containing the first four bytes that our program requires. */
    private byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

    private void checkFormat(byte[] content) {
        // Maze check
        int i = 0;
        while (i < 4) {
            if (content[i] != firstFour[i]) {
                System.out.println("MAZE erronea");
                throw new IllegalArgumentException("IAE");
            }
            i++;
        }
        // Falta poner limites
        if (content[i + 1] < 2
                || content[i + 1] > 0xFF) {
            System.out.println("Dimension erronea");
            throw new IllegalArgumentException("IAE");
        }

    }

    public Grafica<Cell> getCells(byte[] content) {
        int rows = content[4];
        int columns = content[5];
        int level = 0;
        Cell[] contentCell = new Cell[content.length];
        Grafica<Cell> graph = new Grafica<>();

        for (int i = 6; i < content.length; i++) {
            // increases the row
            if (i % columns == 0)
                level++;

            Cell cell = new Cell(content[i], level);
            graph.agrega(cell);
            contentCell[i] = cell;
        }

        for (int i = 6; i < contentCell.length; i++) {
            Cell currentCell = contentCell[i];
            if (currentCell.isEast() == true) {
                graph.conecta(currentCell, contentCell[i + 1],
                        currentCell.getPoints() + 1 + contentCell[i + 1].getPoints());
            }
            if (currentCell.isSouth() == true) {
                graph.conecta(currentCell, contentCell[i + columns],
                        currentCell.getPoints() + 1 + contentCell[i + columns].getPoints());
            }
        }
        return graph;

    }

    private void checkBorders(byte[] content, int column, int rows) {
        checkNorthWall(content, column, rows);
        checkSouthWall(content, column, rows);
        checkWestWall(content, column, rows);
        checkEastWall(content, column, rows);
    }

    private void checkNorthWall(byte[] content, int columns, int rows) {
        for (int i = 6; i < columns; i++) {
            Cell cell = new Cell(content[i], 0);
            if (cell.isNorth() == false)
                throw new IllegalArgumentException();
        }
    }

    private void checkSouthWall(byte[] content, int columns, int rows) {
        for (int i = (columns * rows) - columns; i < content.length; i++) {
            Cell cell = new Cell(content[i], 0);
            if (cell.isSouth() == false)
                throw new IllegalArgumentException();
        }
    }

    private void checkEastWall(byte[] content, int columns, int rows) {
        for (int i = columns - 1; i < content.length; i += columns - 1) {
            Cell cell = new Cell(content[i], 0);
            if (cell.isEast() == false)
                throw new IllegalArgumentException();
        }
    }

    private void checkWestWall(byte[] content, int columns, int rows) {
        for (int i = 0; i < content.length; i += columns) {
            Cell cell = new Cell(content[i], 0);
            if (cell.isWest() == false)
                throw new IllegalArgumentException();
        }
    }
}
