package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void createNewPlayer() {
    }

    @Test
    void getSpace() {
        Board board = new Board();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.getSpace(3,7), "An out of bound exception should be thrown.");
    }

    @Test
    void cardChoice() {
    }

    @Test
    void setup() {
    }
}