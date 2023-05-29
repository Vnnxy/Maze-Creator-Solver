package mx.unam.ciencias.edd.Proyecto3;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Proyecto3.Cell;

public class RoomsSvg {

    private String svgMaze = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<svg width=\"%1$d\" height=\"%2$d\" xmlns=\"http://www.w3.org/2000/svg\" > \n";

    public RoomsSvg() {
    }

    private String northWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";
    private String southWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";
    private String westWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";
    private String eastWall = "<line x1='%1$d' y1='%2$d' x2='%3$d' y2='%4$d' stroke='black' stroke-width='1' />\n";

    public String createRooms(Cell[] graph, int col, int row) {
        int xLeft = 10;
        int xRight = 20;
        int yUp = 10;
        int yDown = 20;
        String svg = String.format(svgMaze, (col * 10) + 20, (row * 10) + 20);
        int level = 0;

        for (int i = 6; i < graph.length; i++) {
            if (level != graph[i].getLevel()) {
                xLeft = 10;
                xRight = 20;
                yUp += 10;
                yDown += 10;
                // System.out.println(level);
            }
            level = graph[i].getLevel();

            svg += createCell(graph[i], xLeft, xRight, yUp, yDown);
            xLeft += 10;
            xRight += 10;

        }
        return svg += "</svg>";
    }

    private String createCell(Cell cell, int x1, int x2, int y1, int y2) {
        String svg = "";
        if (cell.isNorth())
            svg += String.format(northWall, x1, y1, x2, y1);
        if (cell.isSouth())
            svg += String.format(southWall, x1, y2, x2, y2);
        if (cell.isEast())
            svg += String.format(eastWall, x2, y1, x2, y2);
        if (cell.isWest())
            svg += String.format(westWall, x1, y1, x1, y2);

        return svg;

    }

}
