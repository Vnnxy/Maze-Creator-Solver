package mx.unam.ciencias.edd.Proyecto3;

public class Cell {
    private Boolean north = true;
    private Boolean south = true;
    private Boolean east = true;
    private Boolean west = true;
    private int level = 0;
    private int points = 0;

    public Cell(byte b, int level) {

        this.level = level;

        int points = (b >> 4) & 15; // este si srve
        int walls = (b) & 15; // este igual

        if ((walls & 1) == 1)
            east = false;

        if (((walls >> 1) & 1) == 1)
            north = false;

        if (((walls >> 2) & 1) == 1)
            west = false;

        if (((walls >> 3) & 1) == 1)
            south = false;
    }

    public int getPoints() {
        return points;
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
