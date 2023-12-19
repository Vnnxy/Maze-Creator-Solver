package mx.unam.ciencias.edd.Proyecto3;

public class GenCell {

    /* Our byte */
    private int info = 0xF;
    /* Index needed for parsing */
    private int index;
    /* Variable that tells us if a cell has been used/ has a door */
    private boolean hasDoor = false;

    /* Public constructor for our generator cells */
    public GenCell(int points, int index) {
        // Puntos
        info = ((points << 4) | 15);

        this.index = index;

    }

    /* Returns the index of our cell */
    public int getIndex() {
        return index;
    }

    /* Returns ture if the cell has a door leading outside */
    public Boolean hasDoor() {
        return hasDoor;
    }

    /* It gives us a representation of our cell in bytes */
    public byte getByte() {
        return (byte) info;
    }

    /* Sets the cell with a door */
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
                // System.out.println(" sur " + info);
                break;
            }
            // west
            case 1: {
                // door leading west
                info = info & 0xFB;
                // System.out.println(" oeste " + info);
                break;
            }
            // north
            case 2: {
                // door leading north
                info = info & 0xFD;
                // System.out.println(" norte " + info);
                break;
            }
            // east
            case 3: {
                // door leading east
                info = info & 0xFE;
                // System.out.println(" este " + info);
                break;
            }
        }
    }

}
