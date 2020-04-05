package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.answers.GodRequest;

import java.util.Observable;

/**
 * This class provide a model for card selection phase, which is made by the challenger.
 * @author Luca Pirovano
 */
public class CardSelectionModel extends Observable {
    private Card selectedGod;
    private Deck deck;

    /**
     * Constructor of the class; it receives the deck from the Game, in order to put the chosen cards inside it.
     * @param deck the cards deck.
     */
    public CardSelectionModel(Deck deck) {
        this.deck = deck;
    }

    /**
     * Return the god selected by the challenger.
     * @param god selection made by the challenger.
     */
    public void setSelectedGod(Card god) {
        this.selectedGod = god;
    }

    /**
     * Add a chosen god (with command ADD) to the deck.
     * @param god the chosen god.
     */
    public void addToDeck(Card god) throws OutOfBoundException, DuplicateGodException {
        deck.setCard(god);
    }

    /**
     * @return the god selected by the challenger.
     */
    public Card getSelectedGod() {
        return selectedGod;
    }

    /**
     * Set description inside the model, in order to print it in the RemoteView.
     * @param description the description of the god.
     */
    public void setDescription(String description) {
        setChanged();
        notifyObservers(new GodRequest(description));
    }

    public void setNameList() {
        setChanged();
        notifyObservers(new GodRequest(Card.godsName()));
    }

}