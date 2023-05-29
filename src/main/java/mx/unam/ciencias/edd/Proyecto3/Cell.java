package mx.unam.ciencias.edd.Proyecto3;

public class Cell {
    private Boolean north = false;
    private Boolean south = false;
    private Boolean east = false;
    private Boolean west = false;
    private int level;
    private int points = 0;
    private int index;

    public Cell(byte b, int level, int index) {

        this.level = level;
        this.index = index;

        int points = (b >>> 4) & 15; // este si srve
        int walls = (b) & 15; // este igual

        if ((walls & 1) == 1)
            east = true;

        if (((walls >>> 1) & 1) == 1)
            north = true;

        if (((walls >>> 2) & 1) == 1)
            west = true;

        if (((walls >>> 3) & 1) == 1)
            south = true;
    }

    public int getPoints() {
        return points;
    }

    public int getInd() {
        return index;
    }

    public int getLevel() {
        return level;
    }

    public Boolean isNorth() {
        return north;
    }

    public Boolean isSouth() {
        return south;
    }

    public Boolean isEast() {
        return east;
    }

    public Boolean isWest() {
        return west;
    }

}
