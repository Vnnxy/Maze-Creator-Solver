package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Proyecto3.*;

public class RoomsSvg {

    private String svgMaze = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<svg width=\"%1$d\" height=\"%2$d\" xmlns=\"http://www.w3.org/2000/svg\" > \n";

    public RoomsSvg() {
    }

    private String northWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";
    private String southWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";
    private String westWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";
    private String eastWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";

    public StringBuffer createRooms(Cell[] graph, int col, int row) {
        int xLeft = 10;
        int xRight = 20;
        int yUp = 10;
        int yDown = 20;
        StringBuffer svgb = new StringBuffer();
        svgb.append(String.format(svgMaze, (col * 10) + 20, (row * 10) + 20));
        int level = 1;

        for (int i = 0; i < graph.length; i++) {
            if (level < graph[i].getLevel()) {
                xLeft = 10;
                xRight = 20;
                yUp += 10;
                yDown += 10;
                // System.out.println(level);
            }
            level = graph[i].getLevel();
            graph[i].setX(xRight - 5);
            graph[i].setY(yDown - 5);
            svgb.append(createCell(graph[i], xLeft, xRight, yUp, yDown));
            xLeft += 10;
            xRight += 10;

        }
        return svgb;
    }

    private StringBuffer createCell(Cell cell, int x1, int x2, int y1, int y2) {
        StringBuffer svgb = new StringBuffer();
        if (cell.isNorth())

            svgb.append(String.format(northWall, x1, y1, x2, y1));
        if (cell.isSouth())

            svgb.append(String.format(southWall, x1, y2, x2, y2));
        if (cell.isEast())

            svgb.append(String.format(eastWall, x2, y1, x2, y2));
        if (cell.isWest())

            svgb.append(String.format(westWall, x1, y1, x1, y2));

        return svgb;

    }

    public StringBuffer createPath(Lista<VerticeGrafica<Cell>> path) {
        String svgPath = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='deeppink' stroke-width='1' />\n";
        String circle = "<circle cx='%1$d' cy='%2$d' r='2' fill='%3$s' />\n";
        StringBuffer svgb = new StringBuffer();

        Cell[] arrPath = new Cell[path.getLongitud()];
        int i = 0;
        for (VerticeGrafica<Cell> cell : path) {
            arrPath[i] = cell.get();
            i++;
        }

        for (int j = 0; j < arrPath.length - 1; j++) {
            int x1 = arrPath[j].getX();
            int x2 = arrPath[j + 1].getX();
            int y1 = arrPath[j].getY();
            int y2 = arrPath[j + 1].getY();
            svgb.append(String.format(svgPath, x1, y1, x2, y2));

        }

        int entryX = arrPath[0].getX();
        int entryY = arrPath[0].getY();
        int exitX = arrPath[arrPath.length - 1].getX();
        int exitY = arrPath[arrPath.length - 1].getY();
        svgb.append(String.format(circle, entryX, entryY, "MediumPurple"));
        svgb.append(String.format(circle, exitX, exitY, "Lightseagreen"));
        return svgb;
    }

}
