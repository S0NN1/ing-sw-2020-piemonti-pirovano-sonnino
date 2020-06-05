package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.WorkerSetupAction;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.answers.ErrorsType;
import it.polimi.ingsw.server.answers.GameError;
import it.polimi.ingsw.server.answers.SetWorkersMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


/**
 * Class Controller is the main controller class, it calls several "game phase" controllers,
 * like the turn one, or the action one.
 *
 * @author Luca Pirovano
 * @see PropertyChangeListener
 */
public class Controller implements PropertyChangeListener {
    public static final String TURN_CONTROLLER = "turnController";
    private final Game model;
    private final GameHandler gameHandler;
    private final TurnController turnController;
    private final PropertyChangeSupport controllerListeners = new PropertyChangeSupport(this);


    /**
     * Constructor Controller creates a new Controller instance.
     *
     * @param model of type Game - Game reference.
     * @param gameHandler of type GameHandler - GameHandler reference.
     */
    public Controller(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
        this.turnController = new TurnController(this, gameHandler, new ActionController(model.getGameBoard())
        );
        controllerListeners.addPropertyChangeListener(TURN_CONTROLLER, turnController);
    }


    /**
     * Method getTurnController returns the turnController of this Controller object.
     *
     * @return the turnController (type TurnController) of this Controller object.
     */
    public TurnController getTurnController() {
        return turnController;
    }

    /**
     * Method setColor sets  a player color inside the model after receiving it from the client.
     * @param color of type PlayersColors - the color to be set.
     * @param nickname of type String - the player's nickname.
     */
    public void setColor(PlayerColors color, String nickname) {
        model.getPlayerByNickname(nickname).setColor(color);
    }


    /**
     * Method getModel returns the model of this Controller object.
     *
     * @return the model (type Game) of this Controller object.
     */
    public synchronized Game getModel() {
        return model;
    }


    /**
     * Method getGameHandler returns the gameHandler of this Controller object.
     *
     * @return the gameHandler (type GameHandler) of this Controller object.
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }


    /**
     * Method setSelectionController sets the god selection controller for a specific player.
     *
     * @param clientID - the ID of the challenger client.
     *
     */
    public void setSelectionController(int clientID) {
        GodSelectionController selectionController = new GodSelectionController(new CardSelectionModel(model.getDeck()),
                this, gameHandler.getServer().getClientByID(clientID));
        controllerListeners.addPropertyChangeListener("GODSELECTION", selectionController);
    }


    /**
     * Method placeWorkers handles the workers placement inside the game grid. It performs a check on the meaningfulness
     * of the request (like cell [6,6], etc) and also if the desired cell is either free or occupied by someone else.
     * If the check goes fine, the worker is placed and the player notified; otherwise, an INVALIDINPUT error
     * is sent and a new input is requested to the user.
     *
     * @param msg of type WorkerSetupMessage - the worker setup message type, which contains information
     *            about the position of player's workers.
     * @return boolean true if everything goes fine, boolean false otherwise.
     */
    public boolean placeWorkers(WorkerSetupAction msg) {
        for(int i=0; i<2; i++) {
            if(msg.getXPosition(i)<0 || msg.getXPosition(i)>4 || msg.getYPosition(i)<0 || msg.getYPosition(i)>4) {
                gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT,
                        "Error: coordinates out of range!"), getModel().getCurrentPlayer().getClientID());
                return false;
            }
        }
        Space space1 = getModel().getGameBoard().getSpace(msg.getXPosition(0), msg.getYPosition(0));
        Space space2 = getModel().getGameBoard().getSpace(msg.getXPosition(1), msg.getYPosition(1));
        if(space1==space2) {
            gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT,
                    "Error: position cannot be the same for the two workers!"),
                    getModel().getCurrentPlayer().getClientID());
        }
        else if(space1.isEmpty() && space2.isEmpty()) {
            getModel().getCurrentPlayer().getWorkers().get(0).setPosition(space1);
            getModel().getCurrentPlayer().getWorkers().get(1).setPosition(space2);
            gameHandler.sendAll(new SetWorkersMessage(getModel().getCurrentPlayer().getWorkers().get(0).getWorkerColor()
                    ,space1.getRow(), space1.getColumn(), space2.getRow(), space2.getColumn()));
        } else {
            ArrayList<int[]> invalidWorker = new ArrayList<>();
            int[] coords = new int[2];
            int[] coords2 = new int[2];
            if(!space1.isEmpty()) {
                coords[0] = space1.getRow();
                coords[1] = space1.getColumn();
                invalidWorker.add(coords);
            }
            if(!space2.isEmpty()) {
                coords2[0] = space2.getRow();
                coords2[1] = space2.getColumn();
                invalidWorker.add(coords2);
            }
            gameHandler.singleSend(new GameError(ErrorsType.CELLOCCUPIED, null, invalidWorker),
                    getModel().getCurrentPlayer().getClientID());
            return false;
        }
        return true;
    }

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent) */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()) {
            case "godSelection" -> controllerListeners.firePropertyChange("GODSELECTION", null,
                    evt.getNewValue());
            case "workerPlacement" -> placeWorkers((WorkerSetupAction) evt.getNewValue());
            case TURN_CONTROLLER -> controllerListeners.firePropertyChange(TURN_CONTROLLER, null,
                    evt.getNewValue());
            default -> {
                System.err.println("Unrecognized message!");
            }
        }
    }
}
