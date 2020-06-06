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
 * ControllerTest class tests Controller class.
 *
 * @author Luca Pirovano
 * @see Controller
 */
class ControllerTest {
    controllerStub controller;
    Game game;
    UserAction new0;
    UserAction new1;
    UserAction new2;
    UserAction new3;

    /**
     * Class PlayerStub defines a stub for Player class.
     */
    private class PlayerStub extends Player {
        private final List<Worker> workers = new ArrayList<>();

        /**
         * Constructor Player creates a new Player instance.
         *
         * @param nickname of type String - the user nickname.
         * @param clientID of type int - the user unique id.
         */
        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }

        /**
         * Method addWorker creates two instances of worker related to the card received from the deck.
         *
         * @param card of type Card - the card chosen by the user.
         * @param client of type VirtualClient - the user's virtual client representation.
         * @see Player#addWorker(Card, VirtualClient)
         */
        @Override
        public void addWorker(Card card, VirtualClient client) {
            workers.add(new WorkerStub(PlayerColors.GREEN));
            workers.add(new WorkerStub(PlayerColors.GREEN));
        }

        /**
         * Method getWorkers returns the workers of this Player object.
         *
         * @return the workers (type List<Worker>) of this Player object.
         * @see Player#getWorkers()
         */
        @Override
        public List<Worker> getWorkers() {
            return workers;
        }
    }

    /**
     * Class WorkerStub defines a stub for Worker class.
     */
    private static class WorkerStub extends Worker {
        private boolean value;

        /**
         * Constructor WorkerStub creates a new WorkerStub instance.
         *
         * @param color of type PlayerColors
         */
        public WorkerStub(PlayerColors color) {
            super(color);
            value = false;
        }

        /**
         * Method setPhases sets phases.
         * @see Worker#setPhases()
         */
        @Override
        public void setPhases() {}

        /**
         * Method setPosition sets the position of this WorkerStub object.
         *
         *
         *
         * @param space the position of this WorkerStub object.
         *
         * @throws IllegalArgumentException when
         * @see Worker#setPosition(Space)
         */
        @Override
        public void setPosition(Space space) throws IllegalArgumentException {
            super.setPosition(space);
            value = true;
        }
    }

    /**
     * Class controllerStub defines a stub for Controller class.
     */
    private static class controllerStub extends Controller {
        private boolean value = false;

        /**
         * Constructor Controller creates a new Controller instance.
         *
         * @param model of type Game - Game reference.
         * @param gameHandler of type GameHandler - GameHandler reference.
         */
        public controllerStub(Game model, GameHandler gameHandler) {
            super(model, gameHandler);
        }

    }

    /**
     * Class GameHandlerStub defines a stub for GameHandler class.
     */
    private static class GameHandlerStub extends GameHandler {
        Game game;
        /**
         * Constructor GameHandlerStub creates a new GameHandlerStub instance.
         *
         * @param server of type Server
         */
        public GameHandlerStub(Server server) {
            super(server);
        }

        /**
         * Method setGame sets the game of this GameHandlerStub object.
         *
         *
         *
         * @param game the game of this GameHandlerStub object.
         *
         */
        public void setGame(Game game) {
            this.game = game;
        }

        /**
         * Method getCurrentPlayerID returns the currentPlayerID of this GameHandlerStub object.
         *
         *
         *
         * @return the currentPlayerID (type int) of this GameHandlerStub object.
         * @see GameHandler#getCurrentPlayerID()
         */
        @Override
        public int getCurrentPlayerID() {
            return 1;
        }

         /** @see GameHandler#singleSend(Answer, int)*/
        @Override
        public void singleSend(Answer message, int id) {
            //NOTHING
        }

        /**@see GameHandler#sendAll(Answer) */
        @Override
        public void sendAll(Answer message) {
            //NOTHING
        }

        /**@see GameHandler#sendAllExcept(Answer, int) */
        @Override
        public void sendAllExcept(Answer message, int excludedID) {
            //NOTHING
        }
    }

    /**
     * Method initialization initializes values.
     */
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

    /**
     * Method workerPlacementStandard tests worker placement in standard conditions.
     */
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

    /**
     * Method workerPlacementNoCoordinates test worker placement with invalid coords.
     */
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

    /**
     * Method workerPlacementOccupiedCoords tests worker placement with already occupied coordinates.
     */
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

    /**
     * Method godSelectionSetting tests settings of god selection.
     */
    @DisplayName("God Selection Controller setting check")
    @Test
    void godSelectionSetting() {
        int i =1;
        assertEquals(1, i);
        controller.setSelectionController(i);
    }

    /**
     * Method listenerTest tests firePropertyChange.
     */
    @DisplayName("Listener's firing test")
    @Test
    void listenerTest() {
        assert(new0 instanceof ChallengerPhaseAction);
        assert(new1 instanceof WorkerSetupAction);
        assert(new2 instanceof EndTurnAction);
        assertNull(new3);
        controller.propertyChange(new PropertyChangeEvent(this, "godSelection",null, new0));
        controller.propertyChange(new PropertyChangeEvent(this, "workerPlacement", null,
                new1));
        controller.propertyChange(new PropertyChangeEvent(this, "turnController", null, new2));
        controller.propertyChange(new PropertyChangeEvent(this, "nonSensePhrase", null, new3));
    }
}