package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupAction;
import it.polimi.ingsw.client.messages.actions.turnactions.EndTurnAction;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Pirovano
 */
class ControllerTest {
    controllerStub controller;
    Game game;
    UserAction new0;
    UserAction new1;
    UserAction new2;
    UserAction new3;

    private class PlayerStub extends Player {
        private final List<Worker> workers = new ArrayList<>();

        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }

        @Override
        public void addWorker(Card card, VirtualClient client) {
            workers.add(new WorkerStub(PlayerColors.GREEN));
            workers.add(new WorkerStub(PlayerColors.GREEN));
        }

        @Override
        public List<Worker> getWorkers() {
            return workers;
        }
    }

    private static class WorkerStub extends Worker {
        private boolean value;

        public WorkerStub(PlayerColors color) {
            super(color);
            value = false;
        }

        @Override
        public void setPhases() {}

        @Override
        public void setPosition(Space space) throws IllegalArgumentException {
            super.setPosition(space);
            value = true;
        }
    }

    private class controllerStub extends Controller {
        private boolean value = false;

        public controllerStub(Game model, GameHandler gameHandler) {
            super(model, gameHandler);
        }

    }

    private class GameHandlerStub extends GameHandler {
        Game game;
        public GameHandlerStub(Server server) {
            super(server);
        }

        public void setGame(Game game) {
            this.game = game;
        }

        @Override
        public int getCurrentPlayerID() {
            return 1;
        }

        @Override
        public void singleSend(Answer message, int id) {
            //NOTHING
        }

        @Override
        public void sendAll(Answer message) {
            //NOTHING
        }

        @Override
        public void sendAllExcept(Answer message, int excludedID) {
            //NOTHING
        }
    }

    @BeforeEach
    void initialization() {
        game = new Game();
        game.createNewPlayer(new PlayerStub("Piro", 1));
        game.createNewPlayer(new PlayerStub("Ali", 2));
        game.getActivePlayers().get(0).addWorker(Card.APOLLO, new VirtualClient());
        game.getActivePlayers().get(1).addWorker(Card.ATLAS, new VirtualClient());
        game.setCurrentPlayer(game.getActivePlayers().get(0));
        controller = new controllerStub(game, new GameHandlerStub(new Server()));
         new0 = new ChallengerPhaseAction("GODLIST");
         new1 = new WorkerSetupAction(new String[]{null, "1", "2", "3", "4"});
         new2 = new EndTurnAction();
         new3 = null;
    }

    @DisplayName("Worker placement test in standard condition, with no errors expected")
    @Test
    void workerPlacementStandard() {

        String[] input = new String[5];
        input[0] = null;
        input[1] = "0";
        input[2] = "1";
        input[3] = "3";
        input[4] = "0";
        controller.placeWorkers(new WorkerSetupAction(input));
        assertTrue(((WorkerStub)game.getCurrentPlayer().getWorkers().get(0)).value);
    }

    @DisplayName("Worker placement test with an invalid coordinates")
    @Test
    void workerPlacementNoCoordinates() {
        String[] input = new String[5];
        input[0] = null;
        input[1] = "0";
        input[2] = "-1";
        input[3] = "8";
        input[4] = "0";
        assertFalse(controller.placeWorkers(new WorkerSetupAction(input)));
    }

    @DisplayName("Worker placement test with already occupied coordinates")
    @Test
    void workerPlacementOccupiedCoords() {
        String[] input = new String[5];
        input[0] = null;
        input[1] = "0";
        input[2] = "0";
        input[3] = "1";
        input[4] = "1";
        assertTrue(controller.placeWorkers(new WorkerSetupAction(input)));
        game.nextPlayer();
        input[1] = "0";
        input[2] = "0";
        input[3] = "1";
        input[4] = "1";
        assertFalse(controller.placeWorkers(new WorkerSetupAction(input)));
    }

    @DisplayName("God Selection Controller setting check")
    @Test
    void godSelectionSetting() {
        int i =1;
        assertEquals(1, i);
        controller.setSelectionController(i);
    }

    @DisplayName("Listener's firing test")
    @Test
    void listenerTest() {
        assert(new0 instanceof ChallengerPhaseAction);
        assert(new1 instanceof WorkerSetupAction);
        assert(new2 instanceof EndTurnAction);
        assertEquals(null, new3);
        controller.propertyChange(new PropertyChangeEvent(this, "godSelection",null, new0));
        controller.propertyChange(new PropertyChangeEvent(this, "workerPlacement", null, new1));
        controller.propertyChange(new PropertyChangeEvent(this, "turnController", null, new2));
        controller.propertyChange(new PropertyChangeEvent(this, "nonSensePhrase", null, new3));
    }
}