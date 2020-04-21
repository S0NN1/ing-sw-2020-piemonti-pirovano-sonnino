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

class TurnControllerTest {
    Server server = new Server();
    GameBoardStub board = new GameBoardStub();
    GameStub game = new GameStub();
    GameHandlerStub handler = new GameHandlerStub(server);
    Controller controller = new Controller(game, handler);
    ActionController action = new ActionController(board);
    TurnControllerStub turnController = new TurnControllerStub(new Controller(game, handler), action, handler);
    StartTurnAction start1;
    StartTurnAction start2;
    StartTurnAction start3;


    private class TurnControllerStub extends TurnController{

        public TurnControllerStub(Controller controller, ActionController actionController, GameHandler gameHandler) {
            super(controller, actionController, gameHandler);
        }

        
    }

    private class GameHandlerStub extends GameHandler {

        public GameHandlerStub(Server server) {
            super(server);
        }

        @Override
        public void singleSend(Answer message, int ID) {
            System.out.println(message.getMessage());
            //return (String)message.getMessage();
        }
    }

    private class GameBoardStub extends GameBoard {

    }

    private class WorkerStub extends Worker {
        private String name;

        public WorkerStub() {
            super(PlayerColors.RED);
        }

        public WorkerStub(PlayerColors color) {
            super(color);
        }

        @Override
        public void setPhases() {
            System.out.println("set phases");
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private class PlayerStub extends Player {
        /**
         * constructor
         *
         * @param nickname player's univocal name
         * @param clientID
         */
        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }

        @Override
        public ArrayList<Worker> getWorkers() {
            ArrayList<Worker> workers = new ArrayList<Worker>();
            WorkerStub worker1 = new WorkerStub();
            worker1.setName("worker1");
            WorkerStub worker2 = new WorkerStub();
            worker2.setName("worker2");
            workers.add(worker1);
            workers.add(worker2);
            return workers;
        }
    }

    private class GameStub extends Game {
        @Override
        public Player getCurrentPlayer() {
            PlayerStub player = new PlayerStub("kekko", 1);
            return player;
        }
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