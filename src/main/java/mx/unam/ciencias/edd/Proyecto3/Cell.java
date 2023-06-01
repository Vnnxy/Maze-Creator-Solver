package mx.unam.ciencias.edd.Proyecto3;

public class Cell {
    private Boolean north = false;
    private Boolean south = false;
    private Boolean east = false;
    private Boolean west = false;
    private int level;
    private int points = 0;
    private int index;
    private int x1 = 0;
    private int y1 = 0;

    /* This will be used when generating a maze */
    private Boolean used = false;

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

    public void setX(int x) {
        this.x1 = x;
    }

    public void setY(int y) {
        this.y1 = y;
    }

    public int getX() {
        return x1;
    }

    public int getY() {
        return y1;
    }

    public Boolean isUsed() {
        return used;
    }

    public void setUsed() {
        used = true;
    }

}
