package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TowerTest {
    Tower tower = new Tower();

    @Test
    void addlevelIncorrectTest() {
        //TODO fix assertion
        int i = 0;
        try {
            for (i = 0; i < 1; i++) {
                tower.addLevel();
                i++;
            }
        } catch (OutOfBoundException e) {
            System.out.println(e.getMessage());
        }
        tower.setDome(true);
        OutOfBoundException e = Assertions.assertThrows(OutOfBoundException.class, () -> {
            tower.addLevel();
        });
    }
}