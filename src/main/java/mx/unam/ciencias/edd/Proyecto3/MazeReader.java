package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

public class MazeReader {
    public MazeReader() {
    }

    /* Array containing the first four bytes that our program requires. */
    private byte[] firstFour = { 0x4d, 0x41, 0x5a, 0x45 };

    /* Method we will use to check the format of the given file */
    public void checkFormat(byte[] content) {
        // Maze check
        int i = 0;
        while (i < 4) {
            if (content[i] != firstFour[i]) {
                System.out.println("Please use a valid format");
                throw new IllegalArgumentException("IAE");
            }
            i++;
        }
        // Falta poner limites
        int w = content[4] & 0xff;
        int h = content[5] & 0xff;

        if (w < 2
                || w > 0xff || h < 2
                || h > 0xff) {
            System.out.println(content[4] + "y" + content[5]);
            System.out.println("Please use valid dimensions");
            throw new IllegalArgumentException("IAE");
        }

        checkBorders(content, w, h);

    }

    /* Method we will use to make the layout of the maze */
    public Grafica<Cell> getCells(byte[] content) {
        int rows = content[4] & 0xff;
        int columns = content[5] & 0xff;
        int level = 0;
        Cell[] contentCell = new Cell[rows * columns];
        Grafica<Cell> graph = new Grafica<>();
        int j = 0;
        Cell destination = null;
        Cell entry = null;

        for (int i = 6; i < content.length; i++) {
            // increases the row
            if (j % columns == 0)
                level++;

            Cell cell = new Cell(content[i], level, j);
            try {
                graph.agrega(cell);
            } catch (IllegalArgumentException iae) {
                System.out.println("There is a discrepancy coming from the rooms");
                System.exit(1);
            }
            contentCell[j] = cell;
            j++;
        }

        int ec = columns - 1;
        int wc = 0;

        for (int i = 0; i < contentCell.length; i++) {
            Cell currentCell = contentCell[i];

            if (i == ec) {
                ec += columns;
                if (currentCell.isEast() == false) {
                    destination = currentCell;
                }
                if (currentCell.isSouth() == false) {
                    if (i + columns > contentCell.length) {
                        throw new IllegalArgumentException("iae");
                    }
                    graph.conecta(currentCell, contentCell[i + columns],
                            currentCell.getPoints() + 1 + contentCell[i + columns].getPoints());
                }
                continue;
            }

            if (i == wc) {
                wc += columns;
                if (currentCell.isWest() == false) {
                    entry = currentCell;
                }

            }

            if (currentCell.isEast() == false) {
                if (i + 1 > contentCell.length) {
                    throw new IllegalArgumentException("iae");
                }
                graph.conecta(currentCell, contentCell[i + 1],
                        currentCell.getPoints() + 1 + contentCell[i + 1].getPoints());
            }

            if (currentCell.isSouth() == false) {
                if (i + columns > contentCell.length) {
                    throw new IllegalArgumentException("iae");
                }
                graph.conecta(currentCell, contentCell[i + columns],
                        currentCell.getPoints() + 1 + contentCell[i + columns].getPoints());
            }

        }
        RoomsSvg rm = new RoomsSvg();
        StringBuffer a = rm.createRooms(contentCell, columns, rows);
        try {
            a.append(solveMaze(graph, entry, destination) + "</svg>");
        } catch (IllegalArgumentException iae) {
            System.out.println("Please use a valid maze, this maze has no solution");
            System.exit(1);
        }
        System.out.println(a);

        return graph;

    }

    /* Solves and creates an svg representation of the path */
    public StringBuffer solveMaze(Grafica<Cell> graph, Cell origin, Cell destination) {
        Lista<VerticeGrafica<Cell>> path = graph.dijkstra(origin, destination);
        if (path.esVacia())
            throw new IllegalArgumentException("iae");
        RoomsSvg rm = new RoomsSvg();
        return rm.createPath(path);
    }

    /* Method we will use to check the borders, probably broken ftm */
    private void checkBorders(byte[] content, int column, int rows) {
        try {
            checkNorthWall(content, column, rows);
            checkSouthWall(content, column, rows);
            checkWestWall(content, column, rows);
            checkEastWall(content, column, rows);
        } catch (IllegalArgumentException iae) {
            System.out.println("Please use a valid format. Borders are invalid");
            System.exit(1);
        }
    }

    private void checkNorthWall(byte[] content, int columns, int rows) {
        for (int i = 6; i < columns + 6; i++) {
            Cell cell = new Cell(content[i], 0, i);
            if (cell.isNorth() == false) {
                throw new IllegalArgumentException("iae");
            }
        }
    }

    private void checkSouthWall(byte[] content, int columns, int rows) {
        for (int i = ((columns * rows) - columns) + 6; i < content.length; i++) {
            Cell cell = new Cell(content[i], 0, i);
            if (cell.isSouth() == false) {
                throw new IllegalArgumentException("iae");
            }
        }
    }

    private void checkEastWall(byte[] content, int columns, int rows) {
        int entry = 0;
        for (int i = columns + 5; i < content.length; i += columns) {
            Cell cell = new Cell(content[i], 0, i);
            if (cell.isEast() == false)
                entry++;
        }
        if (entry != 1) {
            throw new IllegalArgumentException("iae");
        }
    }

    private void checkWestWall(byte[] content, int columns, int rows) {
        int exit = 0;
        for (int i = 6; i < content.length; i += columns) {
            Cell cell = new Cell(content[i], 0, i);
            if (cell.isWest() == false)
                exit++;
        }
        if (exit != 1) {
        }
    }
}
