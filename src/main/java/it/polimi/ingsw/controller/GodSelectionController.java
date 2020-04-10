package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.GodRequest;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

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
                    cardModel.addToDeck(cmd.arg);
                }
                catch (OutOfBoundException e) {
                    mainController.getGameHandler().singleSend(new GodRequest("Error: no more god to be added!"),
                            mainController.getModel().getCurrentPlayer().getClientID());
                    return;
                }
                break;
            case "CHOOSE":
                if(mainController.getGameHandler().isStarted()==1) {
                    boolean result = mainController.getModel().getDeck().chooseCard(cmd.arg);
                    if (!result) {
                        mainController.getGameHandler().singleSend(new GodRequest("Error: the selected card has not been" +
                                        " chosen by the challenger or has already been taken by another player."),
                                mainController.getGameHandler().getCurrentPlayerID());
                    }
                    else {
                        mainController.getGameHandler().sendAllExcept(new CustomMessage("Player " +
                                mainController.getModel().getCurrentPlayer().getNickname() + " has selected " +
                                cmd.arg.name() + "\n\n" + cmd.arg.godsDescription() + "\n"),
                                mainController.getGameHandler().getCurrentPlayerID());
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "LASTSELECTION":
                if(mainController.getModel().getDeck().getCards().size()!=1) {
                    mainController.getGameHandler().singleSend(new GodRequest("Error: invalid input."),
                            mainController.getGameHandler().getCurrentPlayerID());
                    return;
                }
                Card card = mainController.getModel().getDeck().getCards().get(0);
                mainController.getModel().getDeck().chooseCard(card);
                mainController.getGameHandler().sendAll(new CustomMessage(Constants.ANSI_RED + "The society decides for player " +
                        mainController.getModel().getCurrentPlayer().getNickname() + "! He obtained " + card.name() +
                        Constants.ANSI_RESET + "\n\n" + card.godsDescription() + "\n"));
                break;

                //TODO: make possible to request god's description during the card choosing
        }
    }
}
