package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * BoardTest class tests GameBoard class.
 *
 * @author Luca Pirovano
 * @see GameBoard
 */
class BoardTest {

  /**
   * Method setup measures the instantiation of the game board, and checks if each space is set-up
   * correctly.
   */
  @Test
  @DisplayName("test setup")
  void setup() {
    GameBoard board = new GameBoard();
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        assertEquals(i, board.getSpace(i, j).getRow());
        assertEquals(j, board.getSpace(i, j).getColumn());
      }
    }
  }

  /**
   * Method getSpaceException checks the correct throwing of the ArrayOutOfBoundException, if
   * there's a trying of getting a cell out of board limits.
   */
  @Test
  @DisplayName("Testing ArrayIndexOutOfBoundsException")
  void getSpaceException() {
    GameBoard board = new GameBoard();
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> board.getSpace(3, 7),
        "An out of" + " bound exception should be thrown.");
  }
}
