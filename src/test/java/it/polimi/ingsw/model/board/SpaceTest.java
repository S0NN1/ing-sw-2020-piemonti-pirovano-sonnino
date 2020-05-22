package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    final Space space = new Space();

    @Test
    void isEmptywithoutworker() {
        assertTrue(space.isEmpty());
    }

    @Test
    void correct_positionTest() throws InvalidInputException {
        space.setRow(2);
        space.setColumn(4);
        assertEquals(2, space.getRow());
        System.out.println("correct_positionTest completed");
    }

    /**
     * Testing InvalidInputExceptions of method setX
     */
    @Test
    void incorrect_positionTest() {
        Assertions.assertThrows(InvalidInputException.class, () -> space.setRow(5));
        assertThrows(InvalidInputException.class, () -> space.setColumn(5));
        }

}