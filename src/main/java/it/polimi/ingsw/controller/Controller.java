package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.ErrorsType;
import it.polimi.ingsw.server.answers.GameError;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * Main controller class, it calls several "game phase" controllers, like the turn one, or the action one.
 *
 * @author Luca Pirovano
 */
public class Controller implements PropertyChangeListener {
    private Game model;
    private GameHandler gameHandler;
    private GodSelectionController selectionController;
    private TurnController turnController;
    private PropertyChangeSupport controllerListeners = new PropertyChangeSupport(this);


    public Controller(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
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

    public void placeWorkers(WorkerSetupMessage msg) {
        for(int i=0; i<2; i++) {
            if(msg.getXPosition(i)<0 || msg.getXPosition(i)>6 || msg.getYPosition(i)<0 || msg.getYPosition(i)>6) {
                gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "Error: coordinates out of range!"), getModel().getCurrentPlayer().getClientID());
            }
        }
        Space space1 = getModel().getGameBoard().getSpace(msg.getXPosition(0), msg.getYPosition(0));
        Space space2 = getModel().getGameBoard().getSpace(msg.getXPosition(1), msg.getYPosition(1));
        if(space1==space2 ) {
            gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "Error: position cannot be the same for the two workers!"), getModel().getCurrentPlayer().getClientID());
        }
        else if(space1.isEmpty() && space2.isEmpty()) {
            getModel().getCurrentPlayer().getWorkers().get(0).setPosition(space1);
            getModel().getCurrentPlayer().getWorkers().get(1).setPosition(space2);
        } else {
            ArrayList<int[]> invalidWorker = new ArrayList<>();
            int[] coords = new int[2];
            int[] coords2 = new int[2];
            if(!space1.isEmpty()) {
                coords[0] = space1.getX();
                coords[1] = space1.getY();
                invalidWorker.add(coords);
            }
            if(!space2.isEmpty()) {
                coords2[0] = space2.getX();
                coords2[1] = space2.getY();
                invalidWorker.add(coords2);
            }
            gameHandler.singleSend(new GameError(ErrorsType.CELLOCCUPIED, null, invalidWorker), getModel().getCurrentPlayer().getClientID());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("godSelection")) {
            controllerListeners.firePropertyChange("GODSELECTION", null, evt.getNewValue());
        }
        else if(evt.getPropertyName().equals("workerPlacement")) {
            placeWorkers((WorkerSetupMessage)evt.getNewValue());
        }
    }
}
