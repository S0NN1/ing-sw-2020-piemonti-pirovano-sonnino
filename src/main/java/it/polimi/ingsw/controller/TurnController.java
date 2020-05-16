package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.turnActions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnActions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workerActions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workerActions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.SelectMoveAction;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.answers.ErrorsType;
import it.polimi.ingsw.server.answers.GameError;
import it.polimi.ingsw.server.answers.turn.EndTurnMessage;
import it.polimi.ingsw.server.answers.turn.ModifiedTurnMessage;
import it.polimi.ingsw.server.answers.turn.WorkersRequestMessage;
import it.polimi.ingsw.server.answers.worker.PlayerLostMessage;
import it.polimi.ingsw.server.answers.worker.WinMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Turn controller handling turn's moves and routing actions to the Action Controller
 *
 * @author Sonny
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
                if (!controller.getModel().getCurrentPlayer().equals(controller.getModel().getActivePlayers().get(i))) {
                    controller.getModel().getActivePlayers().get(i).getWorkers().get(0).setCanMoveUp(false);
                    controller.getModel().getActivePlayers().get(i).getWorkers().get(1).setCanMoveUp(false);
                }
                i++;
            }
        } else if (evt.getNewValue().equals("AthenaNormalMove")) {
            while (i < controller.getModel().getActivePlayers().size()) {
                if (!controller.getModel().getCurrentPlayer().equals(controller.getModel().getActivePlayers().get(i))) {
                    controller.getModel().getActivePlayers().get(i).getWorkers().get(0).setCanMoveUp(true);
                    controller.getModel().getActivePlayers().get(i).getWorkers().get(1).setCanMoveUp(true);
                }
                i++;
            }
        } else {
            Object arg = evt.getNewValue();
            if (arg instanceof UserAction) {
                if (arg instanceof StartTurnAction) {
                    StartTurnAction start_action = (StartTurnAction) arg;
                    startTurn(start_action);
                } else if (arg instanceof BuildAction) {
                    BuildAction worker_action = (BuildAction) arg;
                    if (!actionController.readMessage(worker_action)) {
                        sendBuildError();
                    }
                    else if(actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECTBUILD)) {
                        gameHandler.singleSend(new ModifiedTurnMessage("You may choose to build (no args) again or" +
                                "end your turn.", Action.SELECTBUILD), gameHandler.getCurrentPlayerID());
                    }
                } else if (arg instanceof MoveAction) {
                    MoveAction worker_action = (MoveAction) arg;
                    if (!actionController.readMessage(worker_action)) {
                        sendMoveError();
                    }
                    else if(actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECTMOVE)) {
                        gameHandler.singleSend(new ModifiedTurnMessage("You may choose to move (no args) again or" +
                                " build (no args).", Action.SELECTMOVE), gameHandler.getCurrentPlayerID());
                    }
                } else if (arg instanceof SelectMoveAction) {
                    SelectMoveAction worker_action = (SelectMoveAction) arg;
                    if (actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECTMOVE)) {
                        if(!actionController.readMessage(worker_action) && actionController.getWorker().getPhase(actionController.getPhase()).isMust()) {
                            endGame();
                        }
                    }
                    else sendMoveError();
                } else if (arg instanceof SelectBuildAction) {
                    SelectBuildAction worker_action = (SelectBuildAction) arg;
                    if (actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECTBUILD)) {
                        if (!actionController.readMessage(worker_action) && actionController.getWorker().getPhase(actionController.getPhase()).isMust()) {
                            endGame();
                        }
                    }
                    else sendBuildError();
                } else if (arg instanceof EndTurnAction) {
                    endTurn();
                }
            }
        }
    }

    public void sendMoveError() {
        gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "You can't move right now!"),
                gameHandler.getCurrentPlayerID());
    }

    public void sendBuildError() {
        gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT, "You can't build right now!"),
                gameHandler.getCurrentPlayerID());
    }

    public void startTurnAction(int i, int j) {
        if (actionController.startAction(controller.getModel().getCurrentPlayer().getWorkers().get(i))) {
            if (actionController.getWorker().getPhase(actionController.phase).getAction().equals(Action.SELECTBUILD)) {
                gameHandler.singleSend(new ModifiedTurnMessage("You can either type move (no args) or build (no args) based on your choice."),
                        gameHandler.getCurrentPlayerID());
            }
        }
        else{
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
                case "start" -> {
                    gameHandler.singleSend(new WorkersRequestMessage(), gameHandler.getCurrentPlayerID());
                }
                case "worker1" -> {
                    startTurnAction(0, 1);
                }
                case "worker2" -> {
                    startTurnAction(1, 0);
                }
                default -> gameHandler.singleSend(new GameError(ErrorsType.INVALIDINPUT), gameHandler.getCurrentPlayerID());
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
            controller.getModel().nextPlayer();
            gameHandler.unregisterPlayer(removeId);
            gameHandler.getServer().unregisterClient(removeId);
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
        } else {
            gameHandler.singleSend(new GameError(ErrorsType.STILLYOURTURN), gameHandler.getCurrentPlayerID());
        }
    }


}