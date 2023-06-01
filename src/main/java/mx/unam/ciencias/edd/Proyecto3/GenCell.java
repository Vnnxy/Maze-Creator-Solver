package mx.unam.ciencias.edd.Proyecto3;

public class GenCell {

    /*
     * I'll probably use this instead of strings. I just don´t remember how to use
     * enums
     */
    private enum direction {
        north,
        south,
        west,
        east
    }

    private int info;

    private boolean hasDoor = false;

    private Boolean east;
    private Boolean west;
    private Boolean north;
    private Boolean south;

    private int base = 0xFF;

    public GenCell(int points) {
        int p = points;
        info = base; // need to add points
    }

    public Boolean hasDoor() {
        return hasDoor;
    }

    public byte getByte() {
        return (byte) info;
    }

    public void tocToc() {
        this.hasDoor = true;
    }

    /* Method we will use to set the corresponding door to our cell */
    public void setDoor(int direction) {
        switch (direction) {
            // south
            case 0: {
                // door leading south
                info = info & 0xF7;
                break;
            }
            // west
            case 1: {
                // door leading west
                info = info & 0xFB;
                break;
            }
            // north
            case 2: {
                // door leading north
                info = info & 0xFD;
                break;
            }
            // east
            case 3: {
                // door leading east
                info = info & 0xFE;
                break;
            }
        }
    }

}
