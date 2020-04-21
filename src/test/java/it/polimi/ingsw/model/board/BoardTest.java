package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Pirovano
 */
class BoardTest {

    /**
     * This test measures the instantiation of the game board, and checks if each space is set-up correctly.
     */
    @Test
    void setup() {
        GameBoard board = new GameBoard();
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                assertEquals(i, board.getSpace(i, j).getX());
                assertEquals(j, board.getSpace(i,j).getY());
            }
        }
    }

    /**
     * This test checks the correct throwing of the ArrayOutOfBoundException, if there's a trying of getting a cell out
     * of board limits.
     */
    @Test
    void getSpaceException() {
        GameBoard board = new GameBoard();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.getSpace(3,7), "An out of bound exception should be thrown.");
    }

}