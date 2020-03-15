package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.view.CardSelection;

import java.util.Observable;

public class CardSelectionBoard extends Observable implements Cloneable{
    private Card selectedGod;
    private String description;

    public void setSelectedGod(Card god) {
        this.selectedGod = god;
    }

    public Card getSelectedGod() {
        return selectedGod;
    }

    public void setDescription(String description) throws CloneNotSupportedException {
        this.description = description;
        setChanged();
        notifyObservers(this.clone());
    }

    public String getDescription() {
        return description;
    }

    @Override
    protected CardSelectionBoard clone() throws CloneNotSupportedException {
        CardSelectionBoard modelCopy = new CardSelectionBoard();
        modelCopy.description = description;
        modelCopy.selectedGod = selectedGod;
        return modelCopy;
    }
}
