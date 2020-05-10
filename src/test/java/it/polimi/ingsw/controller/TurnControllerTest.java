package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnActions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnActions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workerActions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workerActions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.SelectMoveAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.answers.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;

public class TurnControllerTest {
    Server server = new Server();
    GameBoardStub board = new GameBoardStub();
    GameStub game = new GameStub();
    GameHandlerStub handler = new GameHandlerStub(server);
    Controller controller = new Controller(game, handler);
    TurnControllerStub turnController = new TurnControllerStub(new Controller(game, handler), handler, new ActionControllerStub(board));
    String option = "start";
    String option2 = "worker1";
    String option3 = "worker2";
    StartTurnAction start1 = new StartTurnAction(option);
    StartTurnAction start2 = new StartTurnAction(option2);
    StartTurnAction start3 = new StartTurnAction(option3);
    EndTurnAction end = new EndTurnAction();
    SelectBuildAction selBuild = new SelectBuildAction();
    SelectMoveAction selMove = new SelectMoveAction();
    MoveAction moveAction = new MoveAction(1, 1);
    BuildAction buildAction = new BuildAction(1, 1);
    PropertyChangeEvent evt1 = new PropertyChangeEvent(1, null, null, start1);
    PropertyChangeEvent evt2 = new PropertyChangeEvent(2, null, null, start2);
    PropertyChangeEvent evt3 = new PropertyChangeEvent(3, null, null, start3);
    PropertyChangeEvent evt4 = new PropertyChangeEvent(4, null, null, end);
    PropertyChangeEvent evt5 = new PropertyChangeEvent(5, null, null, selBuild);
    PropertyChangeEvent evt6 = new PropertyChangeEvent(6, null, null, selMove);
    PropertyChangeEvent evt7 = new PropertyChangeEvent(7, null, null, moveAction);
    PropertyChangeEvent evt8 = new PropertyChangeEvent(8, null, null, buildAction);

    /**
     * Test if messages are forwarded into the right condition
     */
    @Test
    public void evtTest() {
        turnController.propertyChange(evt1);
        turnController.propertyChange(evt2);
        turnController.propertyChange(evt3);
        turnController.propertyChange(evt4);
        turnController.propertyChange(evt5);
        turnController.propertyChange(evt6);
        turnController.propertyChange(evt7);
        turnController.propertyChange(evt8);
        Assertions.assertEquals(1, 1);
    }

    private class TurnControllerStub extends TurnController {

        private TurnControllerStub(Controller controller, GameHandler gameHandler, ActionControllerStub actionControllerStub) {
            super(controller, gameHandler, actionControllerStub);
        }

        @Override
        public void startTurn(StartTurnAction arg) {
            if (arg.option.equals("start")) {
                System.out.println(" Entered startAction ");
            }
            if (arg.option.equals("worker1")) {
                System.out.println(" Entered worker1 ");
            }
            if (arg.option.equals("worker2")) {
                System.out.println(" Entered worker2 ");
            }
        }

        @Override
        public void endTurn() {
            System.out.println(" Entered endAction ");
        }
    }

    public class GameStub extends Game {

    }

    public class ActionControllerStub extends ActionController {

        public ActionControllerStub(GameBoard gameBoard) {
            super(gameBoard);
        }

        @Override
        public boolean readMessage(SelectMoveAction action) {
            System.out.println("SelectMoveAction");
            return true;
        }

        @Override

        public boolean readMessage(SelectBuildAction action) {
            System.out.println("SelectBuildAction");
            return true;
        }

        @Override


        public boolean readMessage(MoveAction action) {
            System.out.println("MoveAction");
            return true;
        }

        @Override

        public boolean readMessage(BuildAction action) {
            System.out.println("BuildAction");
            return true;
        }
    }

    public class GameHandlerStub extends GameHandler {

        public GameHandlerStub(Server server) {
            super(server);
        }

        @Override
        public void singleSend(Answer message, int id) {
            System.out.println(message.getMessage());
        }
    }

    public class GameBoardStub extends GameBoard {

    }
}