package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.board.Tower;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker;

    @BeforeEach
    void init(){
         worker = new Worker(PlayerColors.RED);
    }

    @Test
    void colorTest(){
        Worker workerBlue = new Worker(PlayerColors.BLUE);
        Worker workerRed = new Worker(PlayerColors.RED);
        Worker workerGreen = new Worker(PlayerColors.GREEN);
        System.out.println(workerRed.getWorkerColor() + "red");
        System.out.println(workerGreen.getWorkerColor() + "green");
        System.out.println(workerBlue.getWorkerColor() + "blue");
    }

    @Test
    void constructorTest(){
        Boolean blockExpected = false;
        Space positionExpected = null;
        assertEquals(blockExpected, worker.isBlocked(), "if the worker isn't blocked");
        assertEquals(positionExpected, worker.getPosition(), "if the worker hasn't got a position yet");
    }

    @Test
    @DisplayName("setPosition method and exceptions")
    void setPositionTest() {

        Space space = new Space();
        space.setX(2);
        space.setY(1);
        worker.setPosition(space);
        int expX = 2;
        int expY = 1;
        assertEquals(expX, worker.getPosition().getX());
        assertEquals(expY, worker.getPosition().getY());

        Space nullSpace = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {worker.setPosition(nullSpace);});

    }

    @Test
    @DisplayName("move method, exception and winning condition")
    void move() {
        Space nullSpace = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {worker.move(nullSpace);});

        Space Space = new Space();
        worker.setPosition(Space);
        Space.setX(1);
        Space.setY(4);
        int expectedX = 1;
        int expectedY = 4;

        assertEquals(expectedX, worker.getPosition().getX() );
        assertEquals(expectedY, worker.getPosition().getY());

        Space space2 = new Space();
        Space space3 = new Space();
        space2.setTower(new Tower());

        for(int i = 0; i < 2; i++) {
            try {
                space2.getTower().addLevel();
            } catch (OutOfBoundException e) {
                e.printStackTrace();
            }
        }
        space3.setTower(new Tower());
        for(int i = 0; i < 3; i++){
            try {
                space3.getTower().addLevel();
            } catch (OutOfBoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("you should win:");
        worker.setPosition(space2);
        worker.move(space3);
    }

    @Test
    void getMoves() {
    }

    @Test
    void getBuildableSpaces() {
    }
}