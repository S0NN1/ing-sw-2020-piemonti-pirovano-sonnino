package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    private Game game;

    public Deck(Game game) {
        this.game = game;
    }

    /**
     * Add a card chosen by the challenger to the deck.
     * @param card the chosen card.
     * @throws OutOfBoundException if the current size of the array is equals to the number of players.
     */
    public int setCard(Card card) throws OutOfBoundException {
        if(cards.size()== game.getActivePlayers().size()) {
            throw new OutOfBoundException();
        }
        else if(cards.contains(card)) {
            return 0;
        }
        cards.add(card);
        if(cards.size()==game.getActivePlayers().size()) {
            return 2;
        }
        return 1;
    }


    /**
     * @return the content of the deck in ArrayList<Card> type.
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Remove a card afterwards a player god-power choice.
     * @param card the card selected by the player.
     * @return true if everything is ok, false otherwise.
     */
    public boolean chooseCard(Card card, VirtualClient client) {
        if(!cards.contains(card)) {
            return false;
        }
        game.getCurrentPlayer().setCard(card, client);
        cards.remove(card);
        return true;
    }

    /**
     * Remove a card afterwards a player god-power choice in case of Athena selection.
     * @param card the selected card.
     * @param client the client which has selected Athena.
     * @param controller the game controller.
     * @return true if everything is ok, false otherwise.
     */
    public boolean chooseCard(Card card, VirtualClient client, TurnController controller) {
        if (!cards.contains(card)) {
            return false;
        }
        game.getCurrentPlayer().setCard(card, client, controller);
        cards.remove(card);
        return true;
    }
}
