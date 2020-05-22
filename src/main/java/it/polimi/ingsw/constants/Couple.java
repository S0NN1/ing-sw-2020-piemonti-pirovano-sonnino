package it.polimi.ingsw.constants;

import java.io.Serializable;

/**
 * Class Couple contains a couple of integers representing a row and a column.
 *
 * @author Alice Piemonti
 * @see Serializable
 */
public class Couple implements Serializable {

    private int row;
    private int column;

    /**
     * Method setRow sets the row of this Couple object.
     *
     *
     *
     * @param row the row of this Couple object.
     *
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Method setColumn sets the column of this Couple object.
     *
     *
     *
     * @param column the column of this Couple object.
     *
     */
    public void setColumn(int column) {
        this.column = column; //TODO ALI PLOX
    }

    /**
     * Constructor Couple creates a new Couple instance.
     *
     * @param row of type int
     * @param column of type int
     */
    public Couple(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Method getRow returns the row of this Couple object.
     *
     *
     *
     * @return the row (type int) of this Couple object.
     */
    public int getRow() {
        return row;
    }

    /**
     * Method getColumn returns the column of this Couple object.
     *
     *
     *
     * @return the column (type int) of this Couple object.
     */
    public int getColumn() {
        return column;
    }
}
