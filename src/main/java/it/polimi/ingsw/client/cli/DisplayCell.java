package it.polimi.ingsw.client.cli;

/**
 * Class storing a single cell for grid.
 *
 * @author Nicolò Sonnino
 */
public class DisplayCell {
  private final String[] cellRows;

  /** Constructor DisplayCell creates a new DisplayCell instance. */
  public DisplayCell() {
    cellRows = new String[11];
  }

  /**
   * Method getCellRows gets cell's row.
   *
   * @param i of type int - the number needed to identify cell.
   * @return String - the cell's row needed.
   */
  public String getCellRows(int i) {
    return cellRows[i];
  }

  /**
   * Method setCellRows sets cell's row
   *
   * @param i of type int - the number needed to identify cell.
   * @param string of type String - the cell's row inserted.
   */
  public void setCellRows(int i, String string) {
    cellRows[i] = string;
  }
}
