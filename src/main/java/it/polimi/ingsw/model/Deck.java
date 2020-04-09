package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CardNotChosenException;
import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.PlayersNumber;

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
     * @throws CardNotChosenException if the card was not chosen by the challenger or if it's been selected by someone else.
     */
    public boolean removeCard(Card card) {
        if(!cards.contains(card)) {
            return false;
        }
        cards.remove(card);
        return true;
    }
}
