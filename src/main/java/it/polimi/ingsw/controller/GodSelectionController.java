package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.server.VirtualClient;

import java.util.Observable;
import java.util.Observer;

/**
 * Controller of the god powers selection, made by the challenger.
 * @author Luca Pirovano
 */

public class GodSelectionController implements Observer {
    private CardSelectionModel cardModel;
    private Controller mainController;

    /**
     * Constructor of the class, it receives in input the CardSelectionBoard.
     * @param model the CardSelectionBoard.
     */
    public GodSelectionController(CardSelectionModel model, Controller mainController, VirtualClient challenger) {
        this.cardModel = model;
        this.mainController = mainController;
        model.addObserver(challenger);
    }

    /**
     * Update method for MVC communication.
     * @param arg the card selected by the player.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof GodSelectionAction)) {
            throw new IllegalArgumentException();
        }
        GodSelectionAction cmd = (GodSelectionAction)arg;
        switch (cmd.action) {
            case "LIST":
                cardModel.setNameList();
                break;
            case "DESC":
                cardModel.setSelectedGod(cmd.arg);
                cardModel.setDescription(cardModel.getSelectedGod().godsDescription());
                break;
            case "ADD":
                try {
                    cardModel.addToDeck(cardModel.getSelectedGod());
                }
                catch (OutOfBoundException e) {
                    //TODO
                }
                catch (DuplicateGodException e) {
                    //TODO
                }
                break;
        }
    }
}
