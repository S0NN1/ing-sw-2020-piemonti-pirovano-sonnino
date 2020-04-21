package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.GameHandler;

import java.util.Observable;
import java.util.Observer;

/**
 * Main controller class, it calls several "game phase" controllers, like the turn one, or the action one.
 * @author Luca Pirovano
 */
public class Controller extends Observable implements Observer{
    private Game model;
    private GameHandler gameHandler;
    private GodSelectionController selectionController;

    public Controller(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
    }

    public void setColor(PlayerColors color, String nickname) {
        model.getPlayerByNickname(nickname).setColor(color);
    }

    public synchronized Game getModel() {
        return model;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setSelectionController(int clientID) {
        selectionController = new GodSelectionController(new CardSelectionModel(model.getDeck()), this, gameHandler.getServer().getClientByID(clientID));
        this.addObserver(selectionController);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof GodSelectionAction) {
            setChanged();
            notifyObservers(arg);
        }
    }
}
