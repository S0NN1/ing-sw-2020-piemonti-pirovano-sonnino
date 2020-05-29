package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.turnactions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnactions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workeractions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectMoveAction;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.answers.ErrorsType;
import it.polimi.ingsw.server.answers.GameError;
import it.polimi.ingsw.server.answers.turn.EndTurnMessage;
import it.polimi.ingsw.server.answers.turn.ModifiedTurnMessage;
import it.polimi.ingsw.server.answers.turn.StartTurnMessage;
import it.polimi.ingsw.server.answers.turn.WorkersRequestMessage;
import it.polimi.ingsw.server.answers.worker.PlayerLostMessage;
import it.polimi.ingsw.server.answers.worker.WinMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Turn controller handling turn's moves and routing actions to the Action Controller
 *
 * @author Nicolò Sonnino
 */

public class TurnController implements PropertyChangeListener {
    /**
     * Controller reference required for routing model to the Turn Controller
     */
    private final Controller controller;
    /**
     * Controller reference required for routing actions
     */
    private final ActionController actionController;
    /**
     * GameHandler reference in order to use singleSend method
     */
    private final GameHandler gameHandler;


    /**
     * Constructor TurnController creates a new TurnController instance.
     *
     * @param controller of type Controller
     * @param gameHandler of type GameHandler
     * @param actionController of type ActionController
     */
    public TurnController(Controller controller, GameHandler gameHandler, ActionController actionController) {
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.actionController = actionController;
    }

    /**
     * Receive property changed from a PropertyChangeSupport and pass different arguments to the ActionController
     *
     * @param evt the property change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        int i = 0;
        if (evt.getNewValue().equals("AthenaMovedUp")) {
            while (i < controller.getModel().getActivePlayers().size()) {
                setMoveUp(i, false);
                i++;
            }
        } else if (evt.getNewValue().equals("AthenaNormalMove")) {
            while (i < controller.getModel().getActivePlayers().size()) {
                setMoveUp(i, true);
                i++;
            }
        } else {
            Object arg = evt.getNewValue();
            if (arg instanceof UserAction) {
                if (arg instanceof StartTurnAction) {
                    StartTurnAction startAction = (StartTurnAction) arg;
                    startTurn(startAction);
                } else if (arg instanceof BuildAction) {
                    BuildAction buildAction = (BuildAction) arg;
                    checkBuildAction(buildAction);
                } else if (arg instanceof MoveAction) {
                    MoveAction moveAction = (MoveAction) arg;
                    checkMoveAction(moveAction);
                } else if (arg instanceof SelectMoveAction) {
                    SelectMoveAction selectMoveAction = (SelectMoveAction) arg;
                    Phase phase = actionController.getWorker().getPhase(actionController.phase);
                    if(!actionController.readMessage(selectMoveAction)) {
                        if(phase!=null && (phase.getAction().equals(Action.SELECT_BUILD) ||
                                phase.getAction().equals(Action.BUILD) || phase.getAction().equals(Action.MOVE) ||
                                phase.getAction().equals(Action.SELECT_REMOVE) ||
                                phase.getAction().equals(Action.REMOVE) ||
                                phase.getAction().equals(Action.FORCE_WORKER))) {
                            sendMoveError();
                        }
                        else if (actionController.getWorker().getPhase(actionController.phase)!=null &&
                                actionController.getWorker().getPhase(actionController.getPhase()).isMust()) {
                            endGame();
                        }
                        else sendMoveError();
                    }
                } else if (arg instanceof SelectBuildAction) {
                    SelectBuildAction workerAction = (SelectBuildAction) arg;
                    Phase phase = actionController.getWorker().getPhase(actionController.phase);
                    if (!actionController.readMessage(workerAction)) {
                        if (phase!=null && (phase.getAction().equals(Action.SELECT_MOVE) || phase.getAction().equals(Action.MOVE) || phase.getAction().equals(Action.BUILD) || phase.getAction().equals(Action.SELECT_FORCE_WORKER) || phase.getAction().equals(Action.FORCE_WORKER))) {
                            sendBuildError();
                        }
                        else if (phase!=null && phase.isMust()) {
                            endGame();
                        } else sendBuildError();
                    }
                } else if (arg instanceof EndTurnAction) {
                    endTurn();
                }
            }
        }
    }

    private void checkMoveAction(MoveAction workerAction) {
        if (!actionController.readMessage(workerAction)) {
            sendMoveError();
        }
        else if(isPhaseRight(Action.SELECT_MOVE)) {
            sendModifiedTurnMessage("You may choose to move (no args) again or build (no args).", Action.SELECT_MOVE);
        }
    }

    private void checkBuildAction(BuildAction workerAction) {
        String end =" end your turn.";
        if (!actionController.readMessage(workerAction)) {
            sendBuildError();
        }
        else if(isPhaseRight(Action.SELECT_BUILD)) {
            sendModifiedTurnMessage("You may choose to build (no args) again or" + end, Action.SELECT_BUILD);
        }
        else if(isPhaseRight(Action.SELECT_REMOVE)) {
            sendModifiedTurnMessage("You may choose to remove (no args) or" + end, Action.SELECT_REMOVE);
        }
    }

    private void sendModifiedTurnMessage(String message,Action action) {
        gameHandler.singleSend(new ModifiedTurnMessage(message, action), gameHandler.getCurrentPlayerID());
    }

    private boolean isPhaseRight(Action action) {
        return actionController.getWorker().getPhase(actionController.phase) != null &&
                actionController.getWorker().getPhase(actionController.phase).getAction().equals(action);
    }

    private void setMoveUp(int i, boolean b) {
        if (!controller.getModel().getCurrentPlayer().equals(controller.getModel().getActivePlayers().get(i))) {
            controller.getModel().getActivePlayers().get(i).getWorkers().get(0).setCanMoveUp(b);
            controller.getModel().getActivePlayers().get(i).getWorkers().get(1).setCanMoveUp(b);
        }
    }

    /**
     * Method sendMoveError sends move error message
     */
    public void sendMoveError() {
        gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "Move not allowed!"),
                gameHandler.getCurrentPlayerID());
    }

    /**
     * Method sendBuildError sends build error message
     */
    public void sendBuildError() {
        gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "Build not allowed!"),
                gameHandler.getCurrentPlayerID());
    }

    public void startTurnAction(int i, int j) {
        if(actionController.phase!=0) {
            gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "You can't change your worker!"),
                    gameHandler.getCurrentPlayerID());
        }
        else if (actionController.startAction(controller.getModel().getCurrentPlayer().getWorkers().get(i))) {
            if (actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECT_BUILD)) {
                gameHandler.singleSend(new ModifiedTurnMessage("You can either type move (no args) or build (no args) based on your choice."),
                        gameHandler.getCurrentPlayerID());
            }
            else if(actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECT_FORCE_WORKER)){
                gameHandler.singleSend(new ModifiedTurnMessage("You can either type move (no args) or forceworker (no args) based on your choice.", Action.SELECT_FORCE_WORKER),
                        gameHandler.getCurrentPlayerID());
            }
        }
        else if(actionController.phase==0){
            if(controller.getModel().getCurrentPlayer().getWorkers().get(j).isBlocked()) {
                endGame();
            }
            else {
                gameHandler.singleSend(new GameError(ErrorsType.WORKERBLOCKED), gameHandler.getCurrentPlayerID());
                return;
            }
            gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT), gameHandler.getCurrentPlayerID());
        }
    }

    /**
     * Method handling the start of the turn
     *
     * @param arg StartTurnAction message
     */

    public void startTurn(StartTurnAction arg) {
        try {
            switch (arg.option) {
                case "start" -> gameHandler.singleSend(new WorkersRequestMessage(), gameHandler.getCurrentPlayerID());
                case "worker1" -> startTurnAction(0, 1);
                case "worker2" -> startTurnAction(1, 0);
                default -> {
                    gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT), gameHandler.getCurrentPlayerID());
                }
            }
        } catch (NullPointerException e) {
            gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT), gameHandler.getCurrentPlayerID());
        }

    }

    private void endGame() {
        if(controller.getModel().getActivePlayers().size()==2){
            gameHandler.singleSend(new PlayerLostMessage(controller.getModel().getCurrentPlayer().getNickname()), gameHandler.getCurrentPlayerID());
            controller.getModel().nextPlayer();
            gameHandler.singleSend(new WinMessage(), gameHandler.getCurrentPlayerID());
            gameHandler.endGame();
        }
        else{
            String loserColor;
            if(controller.getModel().getCurrentPlayer().getColor()==PlayerColors.BLUE){
                loserColor = "blue";
            }
            else if(controller.getModel().getCurrentPlayer().getColor()==PlayerColors.RED){
                loserColor = "red";
            }
            else loserColor = "green";
            gameHandler.sendAll(new PlayerLostMessage(controller.getModel().getCurrentPlayer().getNickname(), loserColor));
            int removeId = gameHandler.getCurrentPlayerID();
            gameHandler.getServer().getClientByID(removeId).getConnection().close();
            startTurn(new StartTurnAction());
        }
    }

    /**
     * Method handling the end of the turn and switching to the next player
     */
    public void endTurn() {
        if (actionController.endAction()) {
            gameHandler.singleSend(new EndTurnMessage("Turn ended :)"), gameHandler.getCurrentPlayerID());
            controller.getModel().nextPlayer();
            startTurn(new StartTurnAction());
            gameHandler.sendAllExcept(new StartTurnMessage(controller.getModel().getCurrentPlayer().getNickname()), gameHandler.getCurrentPlayerID());
        } else {
            gameHandler.singleSend(new GameError(ErrorsType.STILLYOURTURN), gameHandler.getCurrentPlayerID());
        }
    }


}