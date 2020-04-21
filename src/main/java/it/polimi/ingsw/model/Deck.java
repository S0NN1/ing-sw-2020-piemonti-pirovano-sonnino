package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;

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
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Remove a card afterwards a player god-power choice.
     * @param card the card selected by the player.
     */
    public boolean chooseCard(Card card, VirtualClient client) {
        if(!cards.contains(card)) {
            return false;
        }
        game.getCurrentPlayer().setCard(card, client);
        cards.remove(card);
        return true;
    }
}
