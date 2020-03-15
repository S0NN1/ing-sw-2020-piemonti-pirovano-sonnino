package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.view.CardSelection;

import java.util.Observable;
import java.util.Observer;

public class GodSelectionController implements Observer {
    private CardSelectionBoard model;
    private CardSelection view;

    public GodSelectionController(CardSelectionBoard model, CardSelection view) {
        this.model = model;
        this.view = view;
    }

    public void seeDescription() throws CloneNotSupportedException {
        String out;
        out = model.getSelectedGod().godsDescription();
        model.setDescription(out);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o!=view || !(arg instanceof Card)) {
            throw new IllegalArgumentException("Expected Card Object");
        }
        model.setSelectedGod((Card)arg);
        try {
            seeDescription();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
