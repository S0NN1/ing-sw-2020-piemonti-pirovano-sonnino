package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.observer.CardObserver;
import it.polimi.ingsw.view.CardSelection;

/**
 * Controller of the god powers selection, made by the challenger.
 * @author Luca Pirovano
 */

public class GodSelectionController implements CardObserver<GodSelectionController> {
    private CardSelectionBoard cardModel;
    private CardSelection view;

    /**
     * Constructor of the class, it receives in input the CardSelectionBoard and the CardSelection remote view.
     * @param model the CardSelectionBoard.
     * @param view the CardSelection remote view.
     */
    public GodSelectionController(CardSelectionBoard model, CardSelection view) {
        this.cardModel = model;
        this.view = view;
    }

    /**
     * Update method for MVC communication.
     * @param cmd the command received from the RemoteView.
     * @param arg the card selected by the player.
     */
    @Override
    public void update(String cmd, Object arg) {
        cardModel.setSelectedGod((Card)arg);
        switch (cmd) {
            case "DESC":
                cardModel.setDescription(cardModel.getSelectedGod().godsDescription());
                break;
            case "ADD":
                cardModel.addToDeck(cardModel.getSelectedGod());
                break;
        }
    }
}
