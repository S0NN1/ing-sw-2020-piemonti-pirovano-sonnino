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
 * Class GodSelectionController represents the controller of the god powers selection, which is performed by the
 * challenger. It also handles the card choosing phase performed by players.
 *
 * @author Luca Pirovano
 * @see PropertyChangeListener
 */
public class GodSelectionController implements PropertyChangeListener {
    private final CardSelectionModel cardModel;
    private final Controller mainController;


    /**
     * Constructor GodSelectionController receives in input the CardSelectionModel and creates a
     * new GodSelectionController instance.
     *
     * @param model of type CardSelectionModel the representation of cards (parsed from json file).
     * @param mainController of type Controller the main game controller, which coordinates the others.
     * @param challenger of type VirtualClient the virtual representation of the current player's client.
     */
    public GodSelectionController(CardSelectionModel model, Controller mainController, VirtualClient challenger) {
        this.cardModel = model;
        this.mainController = mainController;
        model.addListener("challengerPhase", challenger);
    }

    /**
     * Method add adds the chosen card to the challenger's deck, in order to be chosen from the other players.
     *
     * @param arg of type Card the chosen card to be added.
     * @return boolean true if the card is added, boolean false otherwise.
     */
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
     * Method desc triggers the card model for notifying the virtual view about the description of the selected god.
     *
     * @param arg of type Card - the selected god which description should be sent to che client.
     */
    public void desc(Card arg) {
        cardModel.setSelectedGod(arg);
        cardModel.setDescription(cardModel.getSelectedGod().godsDescription());
    }


    /**
     * Method choose triggers the card deck for selecting the requested card from the deck.
     *
     * @param arg of type Card - the card requested by the client, to be inserted in his player profile.
     * @return boolean true if everything goes fine, boolean false if the card is not present in the deck
     * (not selected by the challenger or already chosen by someone else).
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
                mainController.getGameHandler().singleSend(new ChallengerMessages(arg), mainController.getGameHandler().getCurrentPlayerID());
                mainController.getGameHandler().sendAllExcept(new CustomMessage("Player " +
                        mainController.getModel().getCurrentPlayer().getNickname() + " has selected " +
                        arg.name() + "\n\n" + arg.godsDescription() + "\n", false), clientId);
                return true;
            }
        }
        return false;
    }


    /**
     * Method lastSelection is called when there's only one card left in the deck. It inserts it in
     * the player's profile without asking him any input. It then notifies the players to communicate the "choice".
     *
     * @return boolean true if everything goes fine, boolean false if it's called outside his scope.
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
        mainController.getGameHandler().singleSend(new ChallengerMessages(card), mainController.getGameHandler().getCurrentPlayerID());
        mainController.getGameHandler().sendAll(new CustomMessage("The society decides for player " +
                mainController.getModel().getCurrentPlayer().getNickname() + "! He obtained " + card.name() +
                "\n\n" + card.godsDescription() + "\n", false));
        return true;
    }


    /**
     * Method propertyChange is the update method for MVC communication. It contains the command sent by the players for:
     * - listing all the gods present in the game;
     * - getting the description of a single god;
     * - adding a god to the match deck;
     * - choosing a god from the match deck (initial phase).
     *
     * @param evt of type PropertyChangeEvent - the couple action-arg, which represents the action performed and the chosen card.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ChallengerPhaseAction cmd = (ChallengerPhaseAction) evt.getNewValue();
        switch (cmd.action) {
            case "LIST" -> cardModel.setNameList();
            case "DESC" -> desc(cmd.arg);
            case "ADD" -> add(cmd.arg);
            case "CHOOSE" -> choose(cmd.arg);
            case "LASTSELECTION" -> lastSelection();
            default ->{
                System.err.println("No action to be performed!");
            }
        }
    }
}
