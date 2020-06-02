package it.polimi.ingsw.model.board;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.InvalidInputException;

/**
 * This class represent the main game board of "Santorini". It's composed by 25 "Spaces", which can be filled with a player
 * or monument (up to 4 levels),
 * @see it.polimi.ingsw.model.board.Space for more information.
 * @author Luca Pirovano
 */

public class GameBoard {
    private final Space[][] grid = new Space[Constants.GRID_MAX_SIZE][Constants.GRID_MAX_SIZE];


    /**
     * Constructor GameBoard creates a grid of 25 spaces and set each space coordinate inside it.
     */
    public GameBoard() {
        for(int i=Constants.GRID_MIN_SIZE; i<Constants.GRID_MAX_SIZE; i++) {
            for(int j=Constants.GRID_MIN_SIZE; j<Constants.GRID_MAX_SIZE; j++) {
                grid[i][j] = new Space();
                try{
                grid[i][j].setRow(i);
                grid[i][j].setColumn(j);
                }
                catch(InvalidInputException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Method getSpace is used in order to get a particular space in the board, identified by x and y coordinates.
     *
     * @param row of type int the y coordinate.
     * @param col of type int the x coordinate.
     * @return Space the desired space.
     */
    public Space getSpace(int row, int col) {
        return grid[row][col];
    }
}
