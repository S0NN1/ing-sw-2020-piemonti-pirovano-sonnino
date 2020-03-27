package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void setup() {
        GameBoard board = new GameBoard();
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                assertEquals(board.getSpace(i, j).getX(), i);
                assertEquals(board.getSpace(i,j).getY(), j);
            }
        }
    }

    @Test
    void getSpace() {
        GameBoard board = new GameBoard();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.getSpace(3,7), "An out of bound exception should be thrown.");
    }

}