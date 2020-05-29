package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.player.Action;

/**
 * Method ClientBoard is a simplified representation of the gameBoard in model.
 *
 * @author Alice Piemonti
 */
public class ClientBoard {
  private final Cell[][] grid;

  /** Constructor ClientBoard creates a new ClientBoard instance. */
  public ClientBoard() {
    grid = new Cell[5][5];
    for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
      for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
        grid[i][j] = new Cell();
      }
    }
  }

  /**
   * Method getGrid returns the grid of this ClientBoard object.
   *
   * @return the grid (type Cell[][]) of this ClientBoard object.
   */
  public Cell[][] getGrid() {
    return grid;
  }

  /**
   * Method setWorkerNum sets the worker's number into the selected cell.
   *
   * @param row of type int - the cell's row.
   * @param col of type int - the cell's column.
   * @param num of type int - the worker's number
   */
  public void setWorkerNum(int row, int col, int num) {
    grid[row][col].setWorkerNum(num);
  }

  /**
   * Method getWorkerNum returns the worker's number of the selected cell.
   *
   * @param row of type int - the cell's row.
   * @param col of type int - the cell's column.
   * @return of type int - the worker's number
   */
  public int getWorkerNum(int row, int col) {
    return grid[row][col].getWorkerNum();
  }

  /**
   * Method setColor sets the color of worker into the selected cell.
   *
   * @param row of type int - the cell's row.
   * @param col of type int - the cell's column.
   * @param color of type String - the player's color.
   */
  public void setColor(int row, int col, String color) {
    grid[row][col].setColor(color);
  }

    /**
     * Method getWorkerPosition gets worker's position from his color and workerNum.
     * @param color of type String - worker's color
     * @param workerNum of type int - the number identifying worker.
     * @return of type Couple - worker's position.
     */
    public Couple getWorkerPosition(String color, int workerNum) {
        for (int row = Constants.GRID_MIN_SIZE; row < Constants.GRID_MAX_SIZE; row++) {
            for (int col = Constants.GRID_MIN_SIZE; col < Constants.GRID_MAX_SIZE; col++) {
                if (getColor(row,col)!= null && getColor(row, col).equals(color) && getWorkerNum(row, col) ==
                        workerNum) {
                    return new Couple(row, col);
                }
            }
        }
        return null;
    }

    /**
     * Method getHeight gets cell's level.
     *
     * @param row of type int - the row of the cell
     * @param col of type int - the column of the cell.
     * @return int - selected level.
     */
    public int getHeight(int row, int col) {
        return grid[row][col].getLevel();
    }

  /**
   * Method getColor returns the worker's color of the selected cell.
   *
   * @param row of type int the cell's row.
   * @param col of type int the cell's column.
   * @return the worker's color (type String).
   */
  public String getColor(int row, int col) {
    return grid[row][col].getColor();
  }

  /**
   * Method move updates modelView grid after a move action.
   *
   * @param oldRow of type int the row of previous worker's position.
   * @param oldCol of type int the column of previous worker's position.
   * @param newRow of type int the row of the actual worker's position.
   * @param newCol of type int the column of the actual worker's position.
   */
  public void move(int oldRow, int oldCol, int newRow, int newCol) {
    String color = grid[oldRow][oldCol].getColor();
    int num = grid[oldRow][oldCol].getWorkerNum();
    grid[oldRow][oldCol].setColor(null);
    grid[oldRow][oldCol].setWorkerNum(0);
    grid[newRow][newCol].setColor(color);
    grid[newRow][newCol].setWorkerNum(num);
  }

  /**
   * Method apolloDoubleMove updates modelView grid after a move action, opponent's worker switches
   * place with APOLLO (APOLLO ONLY).
   *
   * @param oldRow1 of type int - the row of previous Apollo's position.
   * @param oldCol1 of type int - the column of previous Apollo's position.
   * @param oldRow2 of type int - the row of previous other worker's position.
   * @param oldCol2 of type int - the column of previous other worker's position.
   */
  public void apolloDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2) {
    setWorkerNumColors(oldRow1, oldCol1, oldRow2, oldCol2, oldRow1, oldCol1);
  }

  /**
   * Method minotaurDoubleMove updates modelView grid after a move action, opponent's worker is
   * forced to next space.
   *
   * @param oldRow1 of type int the row of Minotaur's previous position.
   * @param oldCol1 of type int the column of Minotaur's previous position.
   * @param oldRow2 of type int the row of the other worker's previous position.
   * @param oldCol2 of type int the column of the other worker's previous position.
   * @param newRow2 of type int the row of the other worker's actual position.
   * @param newCol2 of type int the column of the other worker's actual position.
   */
  public void minotaurDoubleMove(
      int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2) {
    setWorkerNumColors(oldRow1, oldCol1, oldRow2, oldCol2, newRow2, newCol2);
    grid[oldRow1][oldCol1].setColor(null);
    grid[oldRow1][oldCol1].setWorkerNum(0);
  }

  /**
   * Set workers' numbers and colors for designated cells
   *
   * @param oldRow1 of type int - the previous row of the first worker.
   * @param oldCol1 of type int - the previous column of the first worker.
   * @param oldRow2 of type int - the previous row of the second worker.
   * @param oldCol2 of type int - the previous column of the second worker.
   * @param newRow2 of type int - the new row of the second worker.
   * @param newCol2 of type int - the column of the second worker.
   */
  private void setWorkerNumColors(
      int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2) {
    String color1 = grid[oldRow1][oldCol1].getColor();
    String color2 = grid[oldRow2][oldCol2].getColor();
    int num1 = grid[oldRow1][oldCol1].getWorkerNum();
    int num2 = grid[oldRow2][oldCol2].getWorkerNum();
    grid[newRow2][newCol2].setColor(color2);
    grid[newRow2][newCol2].setWorkerNum(num2);
    grid[oldRow2][oldCol2].setColor(color1);
    grid[oldRow2][oldCol2].setWorkerNum(num1);
  }

  /**
   * Method build updates modelView grid after a build action.
   * @param row of type int - the row of the selected row.
   * @param col of type int - the column of the selected column.
   * @param dome of type boolean true if worker wants to build a dome instead of a block. (Only for Atlas workers)
   */
  public void build(int row, int col, boolean dome, Action action) {
    if(!action.equals(Action.BUILD)){
      grid[row][col].removeLevel();
    }
    else if (dome || grid[row][col].getLevel() == 3) {
      grid[row][col].setDome(true);
    } else grid[row][col].addLevel();
  }
}
