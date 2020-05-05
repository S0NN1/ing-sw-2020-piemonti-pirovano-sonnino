package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.ChallengerMessages;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Controller of the god powers selection, made by the challenger, and the card choosing made by players.
 *
 * @author Luca Pirovano
 */
public class GodSelectionController implements PropertyChangeListener {
    private CardSelectionModel cardModel;
    private Controller mainController;

    /**
     * Constructor of the class, it receives in input the CardSelectionBoard.
     *
     * @param model the CardSelectionBoard.
     */
    public GodSelectionController(CardSelectionModel model, Controller mainController, VirtualClient challenger) {
        this.cardModel = model;
        this.mainController = mainController;
        model.addObserver(challenger);
    }

    public boolean add(Card arg) {
        try {
            return cardModel.addToDeck(arg);
        } catch (OutOfBoundException e) {
            mainController.getGameHandler().singleSend(new ChallengerMessages("Error: no more god to be added!"),
                    mainController.getModel().getCurrentPlayer().getClientID());
            return false;
        }
    }

    /**
     * Triggers the card model for notifying the virtual view about the description of the selected god.
     *
     * @param arg the selected god which description should be sent to che client.
     */
    public void desc(Card arg) {
        cardModel.setSelectedGod(arg);
        cardModel.setDescription(cardModel.getSelectedGod().godsDescription());
    }

    /**
     * Triggers the card deck for selecting the requested card from the deck.
     *
     * @param arg the card requested by the client, to be inserted in his player profile.
     * @return true if everything goes fine, false if the card is not present in the deck (not selected by the challenger
     * or already chosen by someone else).
     */
    public boolean choose(Card arg) {
        if (mainController.getGameHandler().isStarted() == 2) {
            int clientId = mainController.getModel().getCurrentPlayer().getClientID();
            VirtualClient client = mainController.getGameHandler().getServer().getClientByID(clientId);
            boolean result;
            if (arg.equals(Card.ATHENA)) {
                result = mainController.getModel().getDeck().chooseCard(arg, client, mainController.getTurnController());
            } else {
                result = mainController.getModel().getDeck().chooseCard(arg, client);
            }
            if (!result) {
                mainController.getGameHandler().singleSend(new ChallengerMessages("Error: the selected card has not been" +
                        " chosen by the challenger or has already been taken by another player."), clientId);
                return false;
            } else {
                mainController.getGameHandler().sendAllExcept(new CustomMessage("Player " +
                        mainController.getModel().getCurrentPlayer().getNickname() + " has selected " +
                        arg.name() + "\n\n" + arg.godsDescription() + "\n", false), clientId);
                return true;
            }
        }
        return false;
    }

    /**
     * This method triggers when there's only one card left in the deck. It inserts it in the player's deck without
     * asking him any input. It then notifies the players to communicate the "choice".
     *
     * @return true if everything goes fine, false if it's called outside his scope.
     */
    public boolean lastSelection() {
        int clientId = mainController.getModel().getCurrentPlayer().getClientID();
        VirtualClient client = mainController.getGameHandler().getServer().getClientByID(clientId);
        if (mainController.getModel().getDeck().getCards().size() != 1) {
            mainController.getGameHandler().singleSend(new ChallengerMessages("Error: invalid input."),
                    clientId);
            return false;
        }
        Card card = mainController.getModel().getDeck().getCards().get(0);
        if (card.equals(Card.ATHENA)) {
            mainController.getModel().getDeck().chooseCard(card, client, mainController.getTurnController());
        } else {
            mainController.getModel().getDeck().chooseCard(card, client);
        }
        mainController.getGameHandler().sendAll(new CustomMessage("The society decides for player " +
                mainController.getModel().getCurrentPlayer().getNickname() + "! He obtained " + card.name() +
                "\n\n" + card.godsDescription() + "\n", false));
        return true;
    }

    /**
     * Update method for MVC communication. It contains the command sent by the players for:
     * - listing all the gods present in the game;
     * - getting the description of a single god;
     * - adding a god to the match deck;
     * - choosing a god from the match deck (initial phase).
     *
     * @param evt the couple action-arg, which represents the case direction and the chosen card.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ChallengerPhaseAction cmd = (ChallengerPhaseAction) evt.getNewValue();
        switch (cmd.action) {
            case "LIST":
                cardModel.setNameList();
                break;
            case "DESC":
                desc(cmd.arg);
                break;
            case "ADD":
                add(cmd.arg);
                break;
            case "CHOOSE":
                choose(cmd.arg);
                break;
            case "LASTSELECTION":
                lastSelection();
                break;
            default:
                System.err.println("No action to be performed!");
        }
    }
}
