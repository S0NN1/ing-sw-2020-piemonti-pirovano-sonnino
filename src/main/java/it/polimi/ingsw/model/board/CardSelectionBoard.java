package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.observer.CardObservable;
import it.polimi.ingsw.view.CardSelection;

import java.util.Observable;

/**
 * @author Luca Pirovano
 */
public class CardSelectionBoard extends CardObservable<CardSelection> {
    private Card selectedGod;
    private String description;
    private Deck deck;

    public CardSelectionBoard(Deck deck) {
        this.deck = deck;
    }

    public void setSelectedGod(Card god) {
        this.selectedGod = god;
    }

    public void addToDeck(Card god){
        deck.setCard(god);
    }

    public Card getSelectedGod() {
        return selectedGod;
    }

    public void setDescription(String description) {
        this.description = description;
        notify("DESC", this);
    }

    public String getDescription() {
        return description;
    }

}