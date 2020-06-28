package it.polimi.ingsw.constants;

import java.io.Serializable;

/**
 * Class Couple contains a couple of integers representing a row and a column.
 *
 * @author Alice Piemonti
 * @see Serializable
 */
public class Couple implements Serializable {

  private final int row;
  private final int column;

  /**
   * Constructor Couple creates a new Couple instance.
   *
   * @param row of type int - the row of the cell.
   * @param column of type int - the column of the cell.
   */
  public Couple(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Method getRow returns the row of this Couple object.
   *
   * @return the row (type int) of this Couple object.
   */
  public int getRow() {
    return row;
  }

  /**
   * Method getColumn returns the column of this Couple object.
   *
   * @return the column (type int) of this Couple object.
   */
  public int getColumn() {
    return column;
  }
}
