package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.workeractions.*;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import it.polimi.ingsw.model.player.gods.simplegods.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionControllerTest {

    GameBoard gameBoard;
    ActionControllerStub actionController;
    Worker worker;

    @BeforeEach
    void init(){
        gameBoard = new GameBoard();
        actionController = new ActionControllerStub(gameBoard);
    }

    /**
     * test the method readMessage with a SelectMoveAction and different phase's values
     */
    @Test
    @DisplayName("select move action test")
    void selectMoveActionTest(){
        worker = new Prometheus(PlayerColors.RED);
        worker.setPosition(gameBoard.getSpace(1,1));
        actionController.startAction(worker);
        SelectMoveAction message = new SelectMoveAction();

        //test correct behaviour: right message on right phase
        assertTrue(actionController.readMessage(message),"1");
        assertEquals(Action.MOVE,worker.getPhase(actionController.getPhase()).getAction(),"2");

        //test wrong behaviour: message on wrong phase
        actionController.setPhase(5);
        assertFalse(actionController.readMessage(message),"3");
        assertEquals(5, actionController.getPhase(),"4");

        //test wrong behaviour: message when turn should be ended
        actionController.setPhase(7);
        assertFalse(actionController.readMessage(message),"5");
    }

    /**
     * test the method readMessage with a SelectBuildAction and different phase's values
     */
    @Test
    @DisplayName("select build action test")
    void selectBuildActionTest(){
        worker = new Artemis(PlayerColors.GREEN);
        worker.setPosition(gameBoard.getSpace(4,4));
        actionController.startAction(worker);
        SelectBuildAction message = new SelectBuildAction();

        //test wrong behavior: message on wrong phase
        assertFalse(actionController.readMessage(message), "1");

       //test correct behavior: right message on the right phase
        actionController.setPhase(4);
        assertTrue(actionController.readMessage(message),"2");
        assertEquals(Action.BUILD,worker.getPhase(actionController.getPhase()).getAction(),"3");

       //test wrong behavior: message when turn should be ended
        actionController.setPhase(7);
        assertFalse(actionController.readMessage(message), "4");
    }

    /**
     * test the method readMessage with a MoveAction and different phase's value
     */
    @Test
    @DisplayName("move action test")
    void moveActionTest(){
        worker = new Prometheus(PlayerColors.BLUE);
        worker.setPosition(gameBoard.getSpace(4,3));
        actionController.startAction(worker);
        MoveAction messageWrong = new MoveAction(2,3);
        MoveAction messageRight = new MoveAction(4,2);

        //test wrong behavior: message on wrong phase
        assertFalse(actionController.readMessage(messageRight),"1");

        //test wrong behavior: wrong message on right phase
        actionController.setPhase(3); //phase move for Prometheus
        assertFalse(actionController.readMessage(messageWrong),"5");
        assertEquals(worker.getPosition(),gameBoard.getSpace(4,3),"6");
        assertEquals(3,actionController.getPhase(),"7");

        //test right behavior: right message on right phase (space where to move is empty -> normal move)
        assertTrue(actionController.readMessage(messageRight),"2");
        assertEquals(gameBoard.getSpace(4,2), worker.getPosition(),"3");
        assertEquals(4, actionController.getPhase(),"4");

        //test right behavior: right message on right phase (space where to move in not empty ->
        // Minotaur's powered move)
        worker = new Minotaur(PlayerColors.BLUE);
        worker.setPosition(gameBoard.getSpace(4,3));
        gameBoard.getSpace(4,2).setWorker(new Minotaur(PlayerColors.RED));
        actionController.startAction(worker);
        actionController.setPhase(1);
        assertTrue(actionController.readMessage(messageRight),"8");
        assertEquals(gameBoard.getSpace(4,2), worker.getPosition(),"9");
        assertFalse(gameBoard.getSpace(4,1).isEmpty(),"10");
    }

    @Test
    @DisplayName("build action test")
    void buildActionTest(){
        worker = new Atlas(PlayerColors.BLUE);
        worker.setPosition(gameBoard.getSpace(2,3));
        actionController.startAction(worker);
        BuildAction message = new BuildAction(2,4);

        //test wrong behaviour: message on wrong phase
        assertFalse(actionController.readMessage(message),"1");

        //test right behaviour: message on right phase (build a block -> normal build)
        actionController.setPhase(3);
        assertTrue(actionController.readMessage(message),"2");
        assertEquals(1,gameBoard.getSpace(2,4).getTower().getHeight(),"3");
        assertFalse(gameBoard.getSpace(2,4).getTower().isCompleted(),"4");
        assertEquals(4, actionController.getPhase(),"5");

        //test right behaviour: message on right phase (build a dome -> Atlas' powered build)
        actionController.setPhase(3);
        AtlasBuildAction messageDome = new AtlasBuildAction(2,4,true);
        assertTrue(actionController.readMessage(messageDome),"6");
        assertEquals(1,gameBoard.getSpace(2,4).getTower().getHeight(),"7");
        assertTrue(gameBoard.getSpace(2,4).getTower().isCompleted(),"8");
        assertEquals(4, actionController.getPhase(),"9");

        //test wrong behaviour: wrong message on right phase (AtlasBuildAction from a non-Atlas worker)
        WorkerForTest workerForTest = new WorkerForTest(PlayerColors.GREEN);
        workerForTest.setPosition(gameBoard.getSpace(1,4));
        actionController.startAction(workerForTest);
        actionController.setPhase(3);
        assertFalse(actionController.readMessage(messageDome),"10");
        assertEquals(3, actionController.getPhase(),"11");
    }

    /**
     * test a sequence of actions: SELECTMOVES, MOVE, SELECTBUILD, BUILD
     */
    @Test
    @DisplayName("test normal sequence of actions")
    void sequenceActionsTest(){
        worker = new Prometheus(PlayerColors.RED);
        worker.setPosition(gameBoard.getSpace(2,4));
        actionController.startAction(worker);
        SelectMoveAction message1 = new SelectMoveAction();
        MoveAction message2 = new MoveAction(3,4);
        SelectBuildAction message3 = new SelectBuildAction();
        BuildAction message4 = new BuildAction(3,3);

       //test correct behaviour: sequence of actions
        assertTrue(actionController.readMessage(message1),"1");
        assertTrue(actionController.readMessage(message2),"2");
        assertFalse(actionController.readMessage(message1),"2b"); //try to make a forbidden action for phase's value
        assertTrue(actionController.readMessage(message3),"3");
        assertTrue(actionController.readMessage(message4),"4");
    }

    /**
     * test the method endAction with different phase's values
     */
    @Test
    @DisplayName("end action test")
    void endActionTest(){
        worker = new Demeter(PlayerColors.BLUE);
        worker.setPosition(gameBoard.getSpace(3,3));
        actionController.startAction(worker);

        //test wrong behaviour: phase = 0, turn can't end now
        assertFalse(actionController.endAction(),"1");

        //test right behaviour: there are other two phases, but they're not a must -> turn can end now
        actionController.setPhase(4);
        assertTrue(actionController.endAction(),"2");

        //test wrong behaviour: after read a SelectBuildAction, BuildAction is a must -> turn can't end now
        actionController.setPhase(4);
        SelectBuildAction action = new SelectBuildAction();
        actionController.readMessage(action);
        assertFalse(actionController.endAction(),"2b");

       //test right behaviour: there are no phases left -> turn must end now
        actionController.setPhase(7);
        assertTrue(actionController.endAction(),"3");
    }

    /**
     * class used to test ActionController methods (which are all inherited) with the help of
     * the setter and getter of phase attribute
     */
    private static class ActionControllerStub extends ActionController {

        public ActionControllerStub(GameBoard gameBoard) {
            super(gameBoard);
        }
        public void setPhase(int phase){
            this.phase = phase;
        }
        public int getPhase(){
            return this.phase;
        }
    }
}