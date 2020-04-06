package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.turnActions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnActions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workerActions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.MoveAction;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.answers.invalidInputRequest;
import it.polimi.ingsw.server.answers.turn.workersRequest;

import java.util.Observable;
import java.util.Observer;

/**
 * Controller
 *
 * @author Sonny
 */

public class TurnController implements Observer {
    Controller controller;
    ActionController actionController;
    GameHandler gameHandler;

    public TurnController(Controller controller, ActionController actionController, GameHandler gameHandler) {
        this.actionController = actionController;
        this.controller = controller;
        this.gameHandler = gameHandler;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserAction) {
            if (arg instanceof StartTurnAction) {
                StartTurnAction start_action = (StartTurnAction) arg;
                startTurn(start_action);
            }
            if (arg instanceof BuildAction) {
                BuildAction worker_action = (BuildAction) arg;
                if (actionController.readMessage(worker_action)) {
                    if (!actionController.nextPhase()) {
                        gameHandler.singleSend(invalidInputRequest::new, gameHandler.getCurrentPlayerID());
                    }
                } else {
                    gameHandler.singleSend(invalidInputRequest::new, gameHandler.getCurrentPlayerID());
                }
            }
            if (arg instanceof MoveAction) {
                MoveAction worker_action = (MoveAction) arg;
                if (actionController.readMessage(worker_action)) {
                    if (!actionController.nextPhase()) {
                        gameHandler.singleSend(invalidInputRequest::new, gameHandler.getCurrentPlayerID());
                    }
                } else {
                    gameHandler.singleSend(invalidInputRequest::new, gameHandler.getCurrentPlayerID());
                }
            }
            if (arg instanceof EndTurnAction) {
                endTurn();
                //TODO need to set ID into game handler
            }
        }

    }

    public void startTurn(StartTurnAction arg) {
        if (arg.option.equals("start")) {
            if (gameHandler.getCurrentPlayerID() == controller.getModel().getCurrentPlayer().getClientID()) {
                gameHandler.singleSend(workersRequest::new, gameHandler.getCurrentPlayerID());
            }
        }
        if (arg.option.equals("worker1")) {
            actionController.startAction(controller.getModel().getCurrentPlayer().getWorkers().get(0));
        }
        if (arg.option.equals("worker2")) {
            actionController.startAction(controller.getModel().getCurrentPlayer().getWorkers().get(1));
        }

    }

    public void endTurn() {
        controller.getModel().nextPlayer();
    }
}
