package it.polimi.ingsw.model.player;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.SerializedAnswer;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import it.polimi.ingsw.server.answers.worker.SelectSpacesMessage;
import it.polimi.ingsw.server.answers.worker.WinMessage;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

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

    /**
     * test if player's colors are correctly displayed in CLI
     */
    @Test
    void colorTest(){
        Worker workerBlue = new WorkerForTest(PlayerColors.BLUE);
        Worker workerRed = new WorkerForTest(PlayerColors.RED);
        Worker workerGreen = new WorkerForTest(PlayerColors.GREEN);
        System.out.println(workerRed.getWorkerColor() + "red");
        System.out.println(workerGreen.getWorkerColor() + "green");
        System.out.println(workerBlue.getWorkerColor() + "blue");
    }

    /**
     * test if the constructor set all values correctly
     * a worker must start without a position and not blocked
     */
    @Test
    void constructorTest(){
        Boolean blockExpected = false;
        Space positionExpected = null;
        assertEquals(blockExpected, worker.isBlocked(), "if the worker isn't blocked");
        assertEquals(positionExpected, worker.getPosition(), "if the worker hasn't got a position yet");
    }

    /**
     * test the method setPosition
     * @throws InvalidInputException if the argument passed to the method is null
     */
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

    /**
     * test the method move
     * it must throw an IllegalArgumentException if the argument passed is null
     * set the new position of the worker
     * set the worker into space's attribute "worker"
     * set old space's attribute "worker" to null
     * test win condition
     */
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

    /**
     * test the method selectMoves
     */
    @Nested
    @DisplayName("getMoves tests")
    class getMovesTest{
        GameBoard gameBoard;

        @BeforeEach
        void init(){
            gameBoard = new GameBoard();
        }

        /**
         * test without any tower
         * worker positioned in the center of the gameboard
         * worker positioned on borders
         * worker positioned on corners
         */
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

        /**
         * test with towers of different height around the worker
         * test with worker positioned on towers with different height
         * @throws OutOfBoundException if too many levels are added
         */
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

        /**
         * test notifyWithMoves when worker is blocked
         * it must throw an IllegalStateException
         * @throws OutOfBoundException if too many levels are added
         */
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

        /**
         * test notifyWithMoves when worker can't move up
         */
        @Test
        @DisplayName("worker can't move up")
        void getMovesNotMoveUp(){
            gameBoard = new GameBoard();
            worker.setPosition(gameBoard.getSpace(1,1));
            worker.setCanMoveUp(false);
            for(int i=0; i<5; i++){
                try {
                    gameBoard.getSpace(i, 0).getTower().addLevel();
                } catch (OutOfBoundException e) {
                    e.printStackTrace();
                }
            }

            assertEquals(5,worker.selectMoves(gameBoard).size(),"1");
        }
    }

    /**
     * test the method getBuildableSpaces
     */
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

        /**
         * test with the worker positioned in the center of the gameboard
         */
        @Test
        @DisplayName("from central space")
        void centralBuild() {
            worker.setPosition(gameBoard.getSpace(1,1));
            int expectedMoves = 8;
            assertEquals(expectedMoves, worker.getBuildableSpaces(gameBoard).size());
        }

        /**
         * test with the worker positioned on borders
         */
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

        /**
         * test with the worker positioned on corners
         */
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

        /**
         * test with the worker positioned on a tower with different height
         * and with towers with different height around it
         * @throws OutOfBoundException if too many level are added
         */
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

    VirtualClientStub client;

    /**
     * test all the listeners associated to worker
     */
    @Nested
    @DisplayName("listeners tests")
    class Listeners{

        GameBoard gameBoard;

        @BeforeEach
        void init() throws OutOfBoundException {
            gameBoard = new GameBoard();
            client = new VirtualClientStub();
            worker.createListeners(client);
            worker.setPosition(gameBoard.getSpace(3,1));

            gameBoard.getSpace(3,2).getTower().addLevel();
            gameBoard.getSpace(3,2).getTower().addLevel();

        }

        /**
         * test if the selectSpacesListener send the message properly to the virtual client
         * which should receive a list of couples
         */
        @Test
        @DisplayName("selectSpacesListener test")
        void selectSpacesListenerTest(){
            worker.notifyWithMoves(gameBoard);
            ArrayList<Space> moves = worker.selectMoves(gameBoard);
            for(int i=0; i<moves.size(); i++){
                assertEquals(moves.get(i).getX(), client.getSelectMoves().get(i).getX(),"x"+ i);
                assertEquals(moves.get(i).getY(), client.getSelectMoves().get(i).getY(), "y" + i);
            }
            worker.notifyWithBuildable(gameBoard);
            ArrayList<Space> build = worker.getBuildableSpaces(gameBoard);
            for(int i=0; i<build.size(); i++){
                assertEquals(build.get(i).getX(), client.getSelectMoves().get(i).getX(),"x"+ i);
                assertEquals(build.get(i).getY(), client.getSelectMoves().get(i).getY(), "y" + i);
            }
       }

        /**
         * test if the moveListener send the old position and the new position (Move type) properly
         * to the virtual client during move method
         */
       @Test
        @DisplayName("moveListener test")
        void moveListenerTest(){
            Space oldPosition = worker.getPosition();
            Space nextPosition = gameBoard.getSpace(3,0);
            assertTrue(worker.isSelectable(nextPosition),"1");
            worker.move(nextPosition);
            assertEquals(oldPosition.getX(),client.move.getOldPosition().getX(),"2");
            assertEquals(oldPosition.getY(),client.move.getOldPosition().getY(),"3");
            assertEquals(nextPosition.getX(),client.move.getNewPosition().getX(),"4");
            assertEquals(nextPosition.getY(),client.move.getNewPosition().getY(),"5");
       }

        /**
         * test if the buildListener send the position (Move type) where to build properly
         * to the virtual client during build method
         */
       @Test
        @DisplayName("buildListener test")
        void buildListenerTest(){
            Space build = gameBoard.getSpace(3,2); //build 3rd level
            assertTrue(worker.isBuildable(build),"1");
            worker.build(build);
            assertEquals(build.getX(),client.build.getX(),"2");
            assertEquals(build.getY(),client.build.getY(),"3");
            assertFalse(client.isDome());
       }

        /**
         * test if the winListener send the current worker (this) properly to the virtual client
         * when winCondition method returns true
         * @throws OutOfBoundException if too many level are added
         */
       @Test
        @DisplayName("winListener test")
        void winListener() throws OutOfBoundException {
            worker.getPosition().getTower().addLevel();
            worker.getPosition().getTower().addLevel(); //2nd level
           Space nextPosition = gameBoard.getSpace(3,2);
           nextPosition.getTower().addLevel(); // 3rd level
           assertTrue(worker.isSelectable(nextPosition),"1");
           worker.move(nextPosition);
           assertTrue(client.wins);
       }
    }

    /**
     * this class receives messages from different listeners
     */
    private class VirtualClientStub extends VirtualClient {

        private ArrayList<Couple> selectMoves;
        private Move move;
        private Couple build;
        private Worker winWorker;
        public boolean wins = false;
        private boolean dome;

        /**
         * save the message received in an appropriate field
         */
        @Override
        public void send(Answer serverAnswer) {

            if(serverAnswer instanceof SelectSpacesMessage){
                selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
            }
            else if(serverAnswer instanceof MoveMessage){
                move = ((MoveMessage) serverAnswer).getMessage();
            }
            else if(serverAnswer instanceof BuildMessage){
                build = ((BuildMessage) serverAnswer).getMessage();
                dome = ((BuildMessage) serverAnswer).getDome();
            }
            else if(serverAnswer instanceof WinMessage){
                winWorker = ((WinMessage) serverAnswer).getMessage();
            }
            else fail("unknown message");
        }

        @Override
        public void win(Answer win) {
            SerializedAnswer winner = new SerializedAnswer();
            winner.setServerAnswer(win);
            wins = true;
        }

        @Override
        public void sendAll(Answer serverAnswer) {
            if(serverAnswer instanceof SelectSpacesMessage){
                selectMoves = ((SelectSpacesMessage) serverAnswer).getMessage();
            }
            else if(serverAnswer instanceof MoveMessage){
                move = ((MoveMessage) serverAnswer).getMessage();
            }
            else if(serverAnswer instanceof BuildMessage){
                build = ((BuildMessage) serverAnswer).getMessage();
                dome = ((BuildMessage) serverAnswer).getDome();
            }
            else if(serverAnswer instanceof WinMessage){
                winWorker = ((WinMessage) serverAnswer).getMessage();
            }
            else fail("unknown message");
        }

        public ArrayList<Couple> getSelectMoves() {
            return selectMoves;
        }

        public Move getMove() {
            return move;
        }

        public Couple getBuild() {
            return build;
        }

        public boolean isDome() {
            return dome;
        }



        public Worker getWinWorker() {
            return winWorker;
        }

    }

}