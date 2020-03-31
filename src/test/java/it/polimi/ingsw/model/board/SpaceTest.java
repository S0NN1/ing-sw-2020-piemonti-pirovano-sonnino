package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpaceTest {
    Space space = new Space();

    @Test
    void isEmptywithoutworker() {
        assertTrue(space.isEmpty());
    }

    @Test
    void correct_positionTest() throws InvalidInputException {
        space.setX(2);
        space.setY(4);
        assertEquals(2, space.getX());
        System.out.println("correct_positionTest completed");
    }

    /**
     * Testing InvalidInputExceptions of method setX
     */
    @Test
    void incorrect_positionTest() {
        InvalidInputException e = Assertions.assertThrows(InvalidInputException.class, () -> {
            space.setX(5);
        });
        }

}