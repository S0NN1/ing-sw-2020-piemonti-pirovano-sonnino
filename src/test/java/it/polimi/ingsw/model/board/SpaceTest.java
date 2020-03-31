package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    Space space = new Space();
    @Test
    void XY_positionTest() throws InvalidInputException {
        space.setX(2);
        space.setY(5);
        assertEquals(2, space.getX());
        System.out.println("XY_positionTest completed");
    }

}