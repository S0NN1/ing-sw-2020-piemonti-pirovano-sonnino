package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;


/**
 * @author Alice Piemonti
 * This class is a simplified representation of the gameboard in model.
 * It allows to have the status of the gameboard on client side.
 */
public class ClientBoard {
    private final Cell[][] grid;

    /**
     * Constructor ClientBoard creates a new ClientBoard instance.
     */
    public ClientBoard(){
        grid = new Cell[5][5];
        for (int i = Constants.GRID_MIN_SIZE; i< Constants.GRID_MAX_SIZE; i++){
            for(int j=Constants.GRID_MIN_SIZE; j<Constants.GRID_MAX_SIZE; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    /**
     * @return two-dimensional array which represents the status of the game board.
     */
    public Cell[][] getGrid(){
        return grid;
    }

    /**
     *  the number of player's worker (1 or 2)
     * @param row of the grid
     * @param col of the grid
     * @param num of the worker into player's array
     */
    public void setWorkerNum(int row, int col, int num){
        grid[row][col].setWorkerNum(num);
    }

    /**
     * return the number of the worker in the cell at row/col
     * @param row of cell
     * @param col of cell
     * @return int workerNum
     */
    public int getWorkerNum(int row, int col) {
        return grid[row][col].getWorkerNum();
    }

    /**
     * it's a representation of Worker's setWorker method.
     * @param row of the grid.
     * @param col of the grid.
     * @param color of the player/worker.
     */
    public void setColor(int row, int col, String color){
        grid[row][col].setColor(color);
    }

    /**
     * return the color of the worker into the specified cell of the grid
     * @param row of the grid.
     * @param col of the grid.
     * @return the color of the worker
     */
    public String getColor(int row, int col){
        return grid[row][col].getColor();
    }

    /**
     * get worker's position from his color and workerNum
     * @param color string
     * @param workerNum int
     * @return worker's position (Couple of int)
     */
    public Couple getWorkerPosition(String color, int workerNum) {
        for (int row = Constants.GRID_MIN_SIZE; row < Constants.GRID_MAX_SIZE; row++) {
            for (int col = Constants.GRID_MIN_SIZE; col < Constants.GRID_MAX_SIZE; col++) {
                if (getColor(row,col)!= null && getColor(row, col).equals(color) && getWorkerNum(row, col) == workerNum) {
                    return new Couple(row, col);
                }
            }
        }
        return null;
    }

    public int getHeight(int row, int col) {
        return grid[row][col].getLevel();
    }
    /**
     * it's a representation of Worker's move method
     * @param oldRow the row of previous worker's position.
     * @param oldCol the column of previous worker's position.
     * @param newRow the row of the actual worker's position.
     * @param newCol the column of the actual worker's position.
     */
    public void move(int oldRow, int oldCol, int newRow, int newCol){
        String color = grid[oldRow][oldCol].getColor();
        int num = grid[oldRow][oldCol].getWorkerNum();
        grid[oldRow][oldCol].setColor(null);
        grid[oldRow][oldCol].setWorkerNum(0);
        grid[newRow][newCol].setColor(color);
        grid[newRow][newCol].setWorkerNum(num);

    }

    /**
     * it's a representation of Apollo's move method
     * @param oldRow1 the row of previous Apollo's position (which is other worker's actual position).
     * @param oldCol1 the column of previous Apollo's position (which is other worker's actual position).
     * @param oldRow2 the row of previous other worker's position (which is Apollo's actual position).
     * @param oldCol2 the column of previous other worker's position (which is Apollo's actual position).
     */
    public void apolloDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2){
        setWorkerNumColors(oldRow1, oldCol1, oldRow2, oldCol2, oldRow1, oldCol1);
    }

    /**
     * it's a representation of Minotaur's move method
     * @param oldRow1 the row of Minotaur's previous position.
     * @param oldCol1 the column of Minotaur's previous position.
     * @param oldRow2 the row of the other worker's previous position (which is Minotaur's actual position).
     * @param oldCol2 the column of the other worker's previous position (which is Minotaur's actual position).
     * @param newRow2 the row of the other worker's actual position.
     * @param newCol2 the column of the other worker's actual position.
     */
    public void minotaurDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2){
        setWorkerNumColors(oldRow1, oldCol1, oldRow2, oldCol2, newRow2, newCol2);
        grid[oldRow1][oldCol1].setColor(null);
        grid[oldRow1][oldCol1].setWorkerNum(0);
    }

    /**
     * Set workers' numbers and colors for designated cells
     *
     * @param oldRow1 of type int
     * @param oldCol1 of type int
     * @param oldRow2 of type int
     * @param oldCol2 of type int
     * @param newRow2 of type int
     * @param newCol2 of type int
     */
    private void setWorkerNumColors(int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2) {
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
     * it's a representation of worker's build method
     * @param row the row of build position.
     * @param col the column of build position.
     * @param dome true if worker wants to build a dome instead of a block. (Only for Atlas workers)
     */
    public void build(int row, int col, boolean dome){
        if(dome || grid[row][col].getLevel() == 3){
            grid[row][col].setDome(true);
        }
        else grid[row][col].addLevel();
    }
}