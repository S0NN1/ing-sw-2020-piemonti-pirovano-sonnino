package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class SpaceTest tests Space class.
 *
 * @author Nicolò Sonnino
 * @see Space
 */
class SpaceTest {
  final Space space = new Space();

  /** Method isEmptyWithoutWorker tests isEmpty method. */
  @Test
  @DisplayName("Testing is empty method")
  void isEmptyWithoutWorker() {
    assertTrue(space.isEmpty());
  }

  /**
   * Method correctPositionTest tests correct position input.
   *
   * @throws InvalidInputException when space is invalid.
   */
  @Test
  @DisplayName("testing correct position")
  void correctPositionTest() throws InvalidInputException {
    space.setRow(2);
    space.setColumn(4);
    assertEquals(2, space.getRow());
  }

  /** Method correctPositionTest tests incorrect position input. */
  @Test
  @DisplayName("testing incorrect position")
  void incorrectPositionTest() {
    assertThrows(InvalidInputException.class, () -> space.setRow(5));
    assertThrows(InvalidInputException.class, () -> space.setColumn(5));
  }
}
