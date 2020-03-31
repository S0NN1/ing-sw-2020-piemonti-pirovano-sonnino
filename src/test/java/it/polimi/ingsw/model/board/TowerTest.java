package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TowerTest {
    Tower tower = new Tower();

    @BeforeEach
    void addlevelCorrect() throws OutOfBoundException {
        tower.addLevel();
        tower.addLevel();
        tower.setDome(true);
    }

    @Test
    void addlevelIncorrectTest() throws OutOfBoundException {
        OutOfBoundException e = Assertions.assertThrows(OutOfBoundException.class, () -> tower.addLevel());
        System.out.println(e.getMessage());

    }

    @Test
    void removeDome() throws OutOfBoundException{
        tower.removeLevel();
        Assertions.assertEquals(2, tower.getHeight());
    }

    @Test
    void removeIncorrect() throws OutOfBoundException{
        tower.removeLevel();
        tower.removeLevel();
        tower.removeLevel();
        OutOfBoundException e = Assertions.assertThrows(OutOfBoundException.class, () -> tower.removeLevel());
        System.out.println(e.getMessage());
    }
}