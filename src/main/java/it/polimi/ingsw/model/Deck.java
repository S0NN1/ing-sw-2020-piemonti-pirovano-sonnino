package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CardNotChosenException;
import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.PlayersNumber;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();

    /**
     * Add a card chosen by the challenger to the deck.
     * @param card the chosen card.
     * @throws OutOfBoundException if the current size of the array is equals to the number of players.
     * @throws DuplicateGodException if the god is already present in the deck.
     */
    public void setCard(Card card) throws OutOfBoundException, DuplicateGodException {
        if(cards.size()== PlayersNumber.playerNumber) {
            throw new OutOfBoundException();
        }
        else if(cards.contains(card)) {
            throw new DuplicateGodException();
        }
        cards.add(card);
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
    public void removeCard(Card card) throws CardNotChosenException {
        if(!cards.contains(card)) {
            throw new CardNotChosenException();
        }
        cards.remove(card);
    }
}
