package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnActions.StartTurnAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.answers.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TurnControllerTest {
    Server server = new Server();
    GameBoardStub board = new GameBoardStub();
    GameStub game = new GameStub();
    GameHandlerStub handler = new GameHandlerStub(server);
    Controller controller = new Controller(game, handler);
    ActionController action = new ActionController(board);
    TurnControllerStub turnController = new TurnControllerStub(new Controller(game, handler), handler);
    StartTurnAction start1;
    StartTurnAction start2;
    StartTurnAction start3;


    private class TurnControllerStub extends TurnController{

        public TurnControllerStub(Controller controller, GameHandler gameHandler) {
            super(controller, gameHandler);
        }

    }

    private class GameHandlerStub extends GameHandler {

        public GameHandlerStub(Server server) {
            super(server);
        }

        @Override
        public void singleSend(Answer message, int ID) {
            System.out.println(message.getMessage());
        }
    }

    private class GameBoardStub extends GameBoard {

    }

    private class WorkerStub extends Worker {
        /**
         * Constructor
         *
         * @param color player color
         */
        public WorkerStub(PlayerColors color) {
            super(color);
        }

        @Override
        public void setPhases() {

        }
    }

    private class PlayerStub extends Player {
    public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }
    }

    private class GameStub extends Game {
    }

    @BeforeEach
    public void setVariables() {
        String option = new String("start");
        String option2 = new String("worker1");
        String option3 = new String("worker2");
        StartTurnAction start1 = new StartTurnAction(option);
        StartTurnAction start2 = new StartTurnAction(option2);
        StartTurnAction start3 = new StartTurnAction(option3);


    }

    @Test
    public void startTurnTest() {
        turnController.startTurn(start3);
    }


}