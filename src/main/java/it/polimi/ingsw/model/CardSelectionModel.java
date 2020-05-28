package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.ChallengerMessages;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class provide a model for card selection phase, which is made by the challenger.
 * @author Luca Pirovano
 */
public class CardSelectionModel {
    public static final String CHALLENGER_PHASE = "challengerPhase";
    private Card selectedGod;
    private final Deck deck;
    private final PropertyChangeSupport virtualClient = new PropertyChangeSupport(this);

    /**
     * Constructor of the class; it receives the deck from the Game, in order to put the chosen cards inside it.
     * @param deck the cards deck.
     */
    public CardSelectionModel(Deck deck) {
        this.deck = deck;
    }

    /**
     * Set the god selected by the challenger.
     * @param god selection made by the challenger.
     */
    public void setSelectedGod(Card god) {
        this.selectedGod = god;
    }

    /**
     * Add a chosen god (with command ADD) to the deck.
     * @param god the chosen god.
     */
    public boolean addToDeck(Card god) throws OutOfBoundException {
        int result=deck.setCard(god);
        if (result==0) {
            virtualClient.firePropertyChange(CHALLENGER_PHASE, null, new ChallengerMessages("Error: the selected god has already been added to the deck."));
            return false;
        } else if(result==1) {
            virtualClient.firePropertyChange(CHALLENGER_PHASE, null, new ChallengerMessages("God " + god.name() + " has been added; choose another one!"));
            return true;
        }
        else {
            virtualClient.firePropertyChange(CHALLENGER_PHASE, null, new ChallengerMessages("God " + god.name() + " has been added!\nAll gods have been added!"));
            return true;
        }
    }

    /**
     * Add the virtual client listener to the card selection model, in order to fire event changes.
     * @param propertyName the virtual client property reference.
     * @param listener the listener to be added.
     */
    public void addListener(String propertyName, PropertyChangeListener listener) {
        virtualClient.addPropertyChangeListener(propertyName, listener);
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
        virtualClient.firePropertyChange(CHALLENGER_PHASE, null, new ChallengerMessages(description));
    }

    /**
     * Set the gods' name list and notifies the virtual client class, which sends them to the user.
     * @see it.polimi.ingsw.server.VirtualClient
     */
    public void setNameList() {
        virtualClient.firePropertyChange(CHALLENGER_PHASE, null, new ChallengerMessages(Card.godsName()));
    }

}