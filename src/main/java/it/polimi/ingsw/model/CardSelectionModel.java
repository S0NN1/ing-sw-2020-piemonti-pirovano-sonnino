package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.ChallengerMessages;

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
        setChanged();
        if (result==0) {
            notifyObservers(new ChallengerMessages(Constants.ANSI_RED + "Error: the selected god has already been added to the deck." + Constants.ANSI_RESET));
            return false;
        } else if(result==1) {
            notifyObservers(new ChallengerMessages(Constants.ANSI_YELLOW + "God " + god.name() + " has been added; choose another one!" + Constants.ANSI_RESET));
            return true;
        }
        else {
            notifyObservers(new CustomMessage(Constants.ANSI_YELLOW + "God " + god.name() +
                    " has been added!\nAll gods have been added!" + Constants.ANSI_RESET, false));
            return true;
        }
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
    public boolean setDescription(String description) {
        setChanged();
        notifyObservers(new ChallengerMessages(description));
        return true;
    }

    /**
     * Set the gods' name list and notifies the virtual client class, which sends them to the user.
     * @see it.polimi.ingsw.server.VirtualClient
     */
    public boolean setNameList() {
        setChanged();
        notifyObservers(new ChallengerMessages(Card.godsName()));
        return true;
    }

}