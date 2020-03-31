package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;

/**
 * This class represent the main game board of Santorini. It's composed by 25 "Spaces", which can be filled with a player
 * or monument (up to 4 levels),
 * @see it.polimi.ingsw.model.board.Space for more information.
 * @author Luca Pirovano
 */

public class GameBoard {
    private Space[][] grid = new Space[5][5];

    /**
     * Constructor of the board. It creates a grid of 25 spaces and set each space coordinate inside it.
     */
    public GameBoard() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                grid[i][j] = new Space();
                try{
                grid[i][j].setX(i);
                grid[i][j].setY(j);
                }
                catch(InvalidInputException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Method used in order to get a particular space in the board, identified by x and y coordinates.
     * @param row the y coordinate.
     * @param col the x coordinate.
     * @return the desired space.
     */
    public Space getSpace(int row, int col) {
        return grid[row][col];
    }
}
