package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Class TowerTest tests Tower class.
 *
 * @author Nicolò Sonnino
 * @see Tower
 */
class TowerTest {
    final Tower tower = new Tower();

    /**
     * Method addLevelCorrect setups levels.
     * @throws OutOfBoundException when level is invalid.
     */
    @BeforeEach
    void addLevelCorrect() throws OutOfBoundException {
        tower.addLevel();
        tower.addLevel();
        tower.setDome(true);
    }

    /**
     * Method addLevelIncorrectTest tests the addition of a incorrect level.
     */
    @Test
    @DisplayName("testing adding incorrect level")
    void addLevelIncorrectTest() {
        OutOfBoundException e = assertThrows(OutOfBoundException.class, tower::addLevel);
        System.out.println(e.getMessage());

    }

    /**
     * Method removeDome tests the removal of a dome.
     * @throws OutOfBoundException when remove level isn't possible.
     */
    @Test
    @DisplayName("testing the removal of a dome")
    void removeDome() throws OutOfBoundException{
        tower.removeLevel();
        assertEquals(2, tower.getHeight());
    }

    /**
     * Method removeIncorrect tests the incorrect removal of a level
     * @throws OutOfBoundException when remove level isn't permitted.
     */
    @Test
    @DisplayName("testing the incorrect removal of a level")
    void removeIncorrect() throws OutOfBoundException{
        tower.removeLevel();
        tower.removeLevel();
        tower.removeLevel();
        OutOfBoundException e = assertThrows(OutOfBoundException.class, tower::removeLevel);
        System.out.println(e.getMessage());
    }
}