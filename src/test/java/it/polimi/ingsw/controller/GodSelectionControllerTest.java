package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.GodRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static org.junit.jupiter.api.Assertions.*;

class GodSelectionControllerTest {

    private class VirtualClientStub extends VirtualClient {
        private boolean notified = false;
        private List<String> gods;
        private String message;

        public String getMessage() {
            return message;
        }
        @Override
        public void update(Observable o, Object arg) {
            notified = true;
            if (arg instanceof GodRequest) {
                if (((GodRequest)arg).message!=null) message = ((GodRequest) arg).message;
                else gods = ((GodRequest) arg).godList;
            }
            else {
                message = ((CustomMessage)arg).getMessage();
            }
        }
    }

    private class GameHandlerStub extends GameHandler {
        public int started = 1;
        public GameHandlerStub(Server server) {
            super(server);
        }

        @Override
        public void singleSend(Answer message, int ID) {
            System.out.println(Constants.ANSI_RED + "\nSingle message \"" + Constants.ANSI_RESET + message.getMessage() +
                    Constants.ANSI_RED + "\" sent to "+ Constants.ANSI_RESET + controller.getModel().getPlayerByID(ID).getNickname());
        }

        @Override
        public void sendAllExcept(Answer message, int excludedID) {
            System.out.println(Constants.ANSI_RED + "\nSemi-broadcast message: " + Constants.ANSI_RESET + message.getMessage());
        }

        @Override
        public void sendAll(Answer message) {
            System.out.println(Constants.ANSI_RED + "\nFull-broadcast message: " + Constants.ANSI_RESET + message.getMessage());
        }

        @Override
        public int isStarted() {
            return started;
        }

    }

    private class GameStub extends Game {
        DeckStub deck;
        public GameStub() {
            this.deck = new DeckStub(this);
        }

        @Override
        public Deck getDeck() {
            return deck;
        }
    }

    private class DeckStub extends Deck {
        public DeckStub(Game game) {
            super(game);
        }

        @Override
        public boolean chooseCard(Card card) {
            if(!super.getCards().contains(card)) {
                return false;
            }
            super.getCards().remove(card);
            return true;
        }
    }

    Server server = new Server();
    GameStub game = new GameStub();
    Controller controller = new Controller(game, new GameHandlerStub(server));
    VirtualClientStub virtualClient = new VirtualClientStub();
    GodSelectionController selectionController = new GodSelectionController(new CardSelectionModel(game.getDeck()), controller, virtualClient);

    @BeforeEach
    void setUp() {
        controller.getModel().createNewPlayer(new Player("Luca", 0));
        controller.getModel().createNewPlayer(new Player("Alice", 1));
    }

    @Test
    @DisplayName("God Selection flow management test, all cases")
    void selectionFlowTest() throws IOException {
        controller.getModel().setCurrentPlayer(controller.getModel().getActivePlayers().get(0));
        //God list and description testing

        selectionController.update(controller, new GodSelectionAction("LIST", null));
        assertTrue(virtualClient.notified);
        assertEquals(Card.godsName(), virtualClient.gods);
        virtualClient.notified = false;
        selectionController.update(controller, new GodSelectionAction("DESC", Card.APOLLO));
        assertTrue(virtualClient.notified);
        assertEquals(virtualClient.message, Card.APOLLO.godsDescription());

        //God deck addition test
        assertTrue(selectionController.add(Card.APOLLO));
        assertTrue(virtualClient.message.contains("God APOLLO has been added!"));
        assertFalse(selectionController.add(Card.APOLLO));

        assertTrue(selectionController.add(Card.PAN));
        assertTrue(virtualClient.message.contains("God PAN has been added!"));
        assertFalse(selectionController.add(Card.PROMETHEUS));

        //God choosing test
        assertFalse(selectionController.lastSelection());
        assertTrue(selectionController.choose(Card.APOLLO));
        assertFalse(selectionController.choose(Card.APOLLO));
        assertFalse(selectionController.choose(Card.ATHENA));
        assertTrue(selectionController.lastSelection());
    }
}