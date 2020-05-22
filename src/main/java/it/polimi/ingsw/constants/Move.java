package it.polimi.ingsw.constants;

import java.io.Serializable;

/**
 * Class Move represents move action and contains information about it.
 *
 * @author Alice Piemonti
 * @see Serializable
 */
public class Move implements Serializable {

    private final Couple oldPosition;
    private final Couple newPosition;

    /**
     * Constructor Move creates a new Move instance.
     *
     * @param oldRow of type int the old row.
     * @param oldColumn of type int the old column.
     * @param newRow of type int the new row.
     * @param newColumn of type int the new column.
     */
    public Move(int oldRow, int oldColumn, int newRow, int newColumn){
        oldPosition = new Couple(oldRow, oldColumn);
        newPosition = new Couple(newRow, newColumn);
    }

    /**
     * Method getOldPosition returns the oldPosition of this Move object.
     *
     *
     *
     * @return the oldPosition (type Couple) of this Move object.
     */
    public Couple getOldPosition() {
        return oldPosition;
    }

    /**
     * Method getNewPosition returns the newPosition of this Move object.
     *
     *
     *
     * @return the newPosition (type Couple) of this Move object.
     */
    public Couple getNewPosition() {
        return newPosition;
    }
}
