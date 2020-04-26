package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnActions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnActions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workerActions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workerActions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.SelectMoveAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.answers.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;

public class TurnControllerTest {
    private class TurnControllerStub extends TurnController {
        private final ActionControllerStub action;
        Server server = new Server();
        GameBoardStub board = new GameBoardStub();
        GameStub game = new GameStub();
        GameHandlerStub handler = new GameHandlerStub(server);
        Controller controller = new Controller(game, handler);
        TurnControllerStub turnController = new TurnControllerStub(new Controller(game, handler), handler);



        private TurnControllerStub(Controller controller, GameHandler gameHandler) {
            super(controller, gameHandler);
            action = new ActionControllerStub(board);
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
    public class GameStub extends Game{

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

        public boolean readMessage(SelectBuildAction action) {
            System.out.println("SelectMoveAction");
            return true;
        }

        public boolean readMessage(MoveAction action) {
            System.out.println("SelectMoveAction");
            return true;
        }

        public boolean readMessage(BuildAction action) {
            System.out.println("SelectMoveAction");
            return true;
        }
    }

    public class GameHandlerStub extends GameHandler {

        public GameHandlerStub(Server server) {
            super(server);
        }

        @Override
        public void singleSend(Answer message, int ID) {
            System.out.println(message.getMessage());
        }
    }

    public class GameBoardStub extends GameBoard {

    }

    public class WorkerStub extends Worker {
        public WorkerStub(PlayerColors color) {
            super(color);
        }

        @Override
        public void setPhases() {

        }
    }


    @BeforeEach
    public void setVariables() {
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
        PropertyChangeEvent evt1 = new PropertyChangeEvent(null, null, null, start1);
        PropertyChangeEvent evt2 = new PropertyChangeEvent(null, null, null, start2);
        PropertyChangeEvent evt3 = new PropertyChangeEvent(null, null, null, start3);
        PropertyChangeEvent evt4 = new PropertyChangeEvent(null, null, null, start3);

    }

    @Test
    public void startTurnTest() {
        turnController.propertyChange(start1);
    }
}