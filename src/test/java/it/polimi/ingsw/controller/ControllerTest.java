package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Pirovano
 */
class ControllerTest {
    controllerStub controller;
    GameStub game;

    private class GameStub extends Game {
        private final ArrayList<PlayerStub> players = new ArrayList<>();
        private PlayerStub currentplayer;

        public void createNewPlayer(PlayerStub player) {
            players.add(player);
        }
    }

    private class PlayerStub extends Player {
        private final ArrayList<WorkerStub> workers = new ArrayList<>();

        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }

        @Override
        public void addWorker(Card card, VirtualClient client) {
            workers.add(new WorkerStub(PlayerColors.GREEN));
            workers.add(new WorkerStub(PlayerColors.GREEN));
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
            value = true;
        }
    }

    private class controllerStub extends Controller {
        private boolean value = false;

        public controllerStub(Game model, GameHandler gameHandler) {
            super(model, gameHandler);
        }

        public void placeWorkers(WorkerSetupMessage msg) {
            for(int i=0; i<2; i++) {
                if(msg.getXPosition(i)<0 || msg.getXPosition(i)>6 || msg.getYPosition(i)<0 || msg.getYPosition(i)>6) {
                    value = false;
                    return;
                }
            }
            Space space1 = getModel().getGameBoard().getSpace(msg.getXPosition(0), msg.getYPosition(0));
            Space space2 = getModel().getGameBoard().getSpace(msg.getXPosition(1), msg.getYPosition(1));
            if(space1==space2 ) {
                value = false;
            }
            else if(space1.isEmpty() && space2.isEmpty()) {
                game.currentplayer.workers.get(0).setPosition(space1);
                game.currentplayer.workers.get(1).setPosition(space2);
                value = true;
            } else {
                ArrayList<int[]> invalidWorker = new ArrayList<>();
                int[] coords = new int[2];
                int[] coords2 = new int[2];
                if(!space1.isEmpty()) {
                    coords[0] = space1.getX();
                    coords[1] = space1.getY();
                    invalidWorker.add(coords);
                }
                if(!space2.isEmpty()) {
                    coords2[0] = space2.getX();
                    coords2[1] = space2.getY();
                    invalidWorker.add(coords2);
                }
                value = false;
            }
        }
    }

    @BeforeEach
    void initialization() {
        game = new GameStub();
        game.createNewPlayer(new PlayerStub("Piro", 1));
        game.createNewPlayer(new PlayerStub("Ali", 2));
        game.players.get(0).addWorker(Card.APOLLO, new VirtualClient());
        game.players.get(1).addWorker(Card.ATLAS, new VirtualClient());
        game.currentplayer = game.players.get(0);
        controller = new controllerStub(game, new GameHandler(new Server()));
    }

    @DisplayName("Worker placement test in standard condition, with no errors expected")
    @Test
    void workerPlacementTestStandard() {
        String[] input = new String[5];
        input[0] = null;
        input[1] = "0";
        input[2] = "1";
        input[3] = "3";
        input[4] = "0";
        controller.placeWorkers(new WorkerSetupMessage(input));
        assertTrue(game.currentplayer.workers.get(0).value);
    }

    @DisplayName("Worker placement test with an invalid coordinates")
    @Test
    void workerPlacementTestNoCoordinates() {
        String[] input = new String[5];
        input[0] = null;
        input[1] = "0";
        input[2] = "-1";
        input[3] = "8";
        input[4] = "0";
        controller.placeWorkers(new WorkerSetupMessage(input));
        assertFalse(controller.value);
    }

}