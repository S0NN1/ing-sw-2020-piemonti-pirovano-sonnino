package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.board.Tower;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class WorkerTest {
    Worker worker;

    @BeforeEach
    void init(){
         worker = new WorkerForTest(PlayerColors.RED);
    }

    @Test
    void colorTest(){
        Worker workerBlue = new WorkerForTest(PlayerColors.BLUE);
        Worker workerRed = new WorkerForTest(PlayerColors.RED);
        Worker workerGreen = new WorkerForTest(PlayerColors.GREEN);
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
    void setPositionTest() throws InvalidInputException {

        Space space = new Space();
        space.setX(2);
        space.setY(1);
        worker.setPosition(space);
        int expX = 2;
        int expY = 1;
        assertEquals(expX, worker.getPosition().getX());
        assertEquals(expY, worker.getPosition().getY());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {worker.setPosition(null);});

    }

    @Test
    @DisplayName("move method, exception and winning condition")
    void move() {
        Space nullSpace = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {worker.move(nullSpace);});

        Space init = new Space();
        Space spaceFin = new Space();
        worker.setPosition(init);
        worker.move(spaceFin);

        assertEquals(spaceFin, worker.getPosition(),"1");
        assertNull(init.getWorker(),"2");
        assertEquals(worker, spaceFin.getWorker(),"3");

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
        worker.move(space2);
        worker.move(space3);
    }

    @Nested
    @DisplayName("getMoves tests")
    class getMovesTest{
        GameBoard gameBoard;

        @BeforeEach
        void init(){
            gameBoard = new GameBoard();
        }


        @Test
        @DisplayName("without towers")
        void getMoves(){

            worker.setPosition(gameBoard.getSpace(2, 2));
            int expectedMovesCenter = 8;
            assertEquals(expectedMovesCenter, worker.selectMoves(gameBoard).size());

            int expectedMovesBorder = 5;
            worker.move(gameBoard.getSpace(0, 3));
            assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(),"1");
            worker.move(gameBoard.getSpace(4, 3));
            assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(),"2");
            worker.move(gameBoard.getSpace(3, 0));
            assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(),"3");
            worker.move(gameBoard.getSpace(1, 4));
            assertEquals(1, worker.getPosition().getX(),"x");
            assertEquals(4, worker.getPosition().getY(),"y");
            assertEquals(expectedMovesBorder, worker.selectMoves(gameBoard).size(),"4");

            int expectedMovesCorner = 3;
            worker.move(gameBoard.getSpace(0, 0));
            assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(),"5");
            worker.move(gameBoard.getSpace(0, 4));
            assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(),"6");
            worker.move(gameBoard.getSpace(4, 0));
            assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(),"7");
            worker.move(gameBoard.getSpace(4, 4));
            assertEquals(expectedMovesCorner, worker.selectMoves(gameBoard).size(), "the worker moves correctly without towers around");
        }

        @Test
        @DisplayName("from different height")
        void getMovesHeightTest() throws OutOfBoundException {

            gameBoard.getSpace(1, 1).getTower().addLevel();
            gameBoard.getSpace(1, 1).getTower().addLevel();
            gameBoard.getSpace(1, 2).getTower().addLevel();
            gameBoard.getSpace(2, 1).getTower().addLevel();
            gameBoard.getSpace(2, 1).getTower().addLevel();
            gameBoard.getSpace(2, 1).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(3, 1).getTower().addLevel();
            gameBoard.getSpace(3, 3).getTower().addLevel();
            gameBoard.getSpace(3, 3).getTower().addLevel();
            gameBoard.getSpace(3, 3).getTower().addLevel();

            int expected0 = 4;
            int expected1 = 5;
            int expected2 = 7;
            int expected3 = 7;

            worker.setPosition(gameBoard.getSpace(2, 2));
            assertEquals(expected0, worker.selectMoves(gameBoard).size());

            worker.move(gameBoard.getSpace(2, 2));
            gameBoard.getSpace(2, 2).getTower().addLevel();
            assertEquals(expected1, worker.selectMoves(gameBoard).size());

            worker.move(gameBoard.getSpace(2, 2));
            gameBoard.getSpace(2, 2).getTower().addLevel();
            assertEquals(expected2, worker.selectMoves(gameBoard).size());

            worker.move(gameBoard.getSpace(2, 2));
            gameBoard.getSpace(2, 2).getTower().addLevel();
            assertEquals(expected3, worker.selectMoves(gameBoard).size(), "the worker moves correctly from any height to any height");
        }

        @Test
        @DisplayName("exception: worker blocked")
        void getMovesButBlocked() throws OutOfBoundException {
            GameBoard gameBoard = new GameBoard();
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    gameBoard.getSpace(i,j).setTower(new Tower());
                    if(i != 1 || j != 1){
                        for(int k=0; k<4; k++){
                            gameBoard.getSpace(i,j).getTower().addLevel();
                        }
                    }
                }
            }
            worker.setPosition(gameBoard.getSpace(1,1));
            Exception exception = assertThrows(IllegalStateException.class, () -> {worker.notifyWithMoves(gameBoard);});
        }
    }

    @Nested
    @DisplayName("getBuildableSpaces tests")
    class getBuildableSpaces{

        GameBoard gameBoard;

        @BeforeEach
        void init(){
            gameBoard = new GameBoard();
            for(int i=1; i<4; i++){
                for(int j=1; j<4; j++){
                    gameBoard.getSpace(i,j).setTower(new Tower());
                }
            }
        }

        @Test
        @DisplayName("from central space")
        void centralBuild() {
            worker.setPosition(gameBoard.getSpace(1,1));
            int expectedMoves = 8;
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
        }

        @Test
        @DisplayName("from boundary space")
        void boundaryBuild() {
            int expectedMoves = 5;

            worker.setPosition(gameBoard.getSpace(0,1));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
            worker.move(gameBoard.getSpace(4,1));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
            worker.move(gameBoard.getSpace(1,0));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
            worker.move(gameBoard.getSpace(1,4));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
        }

        @Test
        @DisplayName("from corners")
        void cornerBuild(){
            int expectedMoves = 3;

            worker.setPosition(gameBoard.getSpace(0,0));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
            worker.setPosition(gameBoard.getSpace(4,0));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
            worker.setPosition(gameBoard.getSpace(0,4));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
            worker.setPosition(gameBoard.getSpace(4,4));
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
        }

        @Test
        @DisplayName("from different heights")
        void differentHeightBuild() throws OutOfBoundException {
            gameBoard.getSpace(1, 1).getTower().addLevel();
            gameBoard.getSpace(1, 1).getTower().addLevel();
            gameBoard.getSpace(1, 2).getTower().addLevel();
            gameBoard.getSpace(2, 1).getTower().addLevel();
            gameBoard.getSpace(2, 1).getTower().addLevel();
            gameBoard.getSpace(2, 1).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(2, 3).getTower().addLevel();
            gameBoard.getSpace(3, 1).getTower().addLevel();
            gameBoard.getSpace(3, 3).getTower().addLevel();
            gameBoard.getSpace(3, 3).getTower().addLevel();
            gameBoard.getSpace(3, 3).getTower().addLevel();

            int expected = 7;

            worker.setPosition(gameBoard.getSpace(2, 2));
            assertEquals(expected, worker.getBuildableSpaces(gameBoard).size());

            worker.move(gameBoard.getSpace(2, 2));
            gameBoard.getSpace(2, 2).getTower().addLevel();
            assertEquals(expected, worker.getBuildableSpaces(gameBoard).size());

            worker.move(gameBoard.getSpace(2, 2));
            gameBoard.getSpace(2, 2).getTower().addLevel();
            assertEquals(expected, worker.getBuildableSpaces(gameBoard).size());

            worker.move(gameBoard.getSpace(2, 2));
            gameBoard.getSpace(2, 2).getTower().addLevel();
            assertEquals(expected, worker.getBuildableSpaces(gameBoard).size(), "the worker build correctly from any height to any height");

        }
    }


}