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

    /* Public constructor for our cells */
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

    /* Returns the points of our cell */
    public int getPoints() {
        return points;
    }

    /* Returns the index of our cell */
    public int getInd() {
        return index;
    }

    /* Returns the level of our cell */
    public int getLevel() {
        return level;
    }

    /* Returns if our cell has a north wall */
    public Boolean isNorth() {
        return north;
    }

    /* Returns if our cell has a south wall */
    public Boolean isSouth() {
        return south;
    }

    /* Returns if our cell has a east wall */
    public Boolean isEast() {
        return east;
    }

    /* Returns if our cell has a west wall */
    public Boolean isWest() {
        return west;
    }

    /* Sets the x coordinate of our cell */
    public void setX(int x) {
        this.x1 = x;
    }

    /* Sets the y coordinate of our cell */
    public void setY(int y) {
        this.y1 = y;
    }

    /* Returns the x coordinate */
    public int getX() {
        return x1;
    }

    /* Returns the y coordinate */
    public int getY() {
        return y1;
    }

    /* It will tell us if a cell has been used */
    public Boolean isUsed() {
        return used;
    }

    /* Sets used as true */
    public void setUsed() {
        used = true;
    }

}
