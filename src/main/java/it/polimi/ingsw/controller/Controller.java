package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.GameHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;
import java.util.Observer;

/**
 * Main controller class, it calls several "game phase" controllers, like the turn one, or the action one.
 * @author Luca Pirovano
 */
public class Controller implements PropertyChangeListener {
    private Game model;
    private GameHandler gameHandler;
    private GodSelectionController selectionController;
    private PropertyChangeSupport controllerListeners  = new PropertyChangeSupport(this);

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
        controllerListeners.addPropertyChangeListener("GODSELECTION", selectionController);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getNewValue() instanceof GodSelectionAction) {
            controllerListeners.firePropertyChange("GODSELECTION", null, evt.getNewValue());
        }
    }
}
