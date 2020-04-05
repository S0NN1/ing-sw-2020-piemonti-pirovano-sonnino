package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.workerActions.StartTurnAction;
import it.polimi.ingsw.server.GameHandler;

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
                StartTurnAction action = (StartTurnAction) arg;
                startTurn(action);
            }
        }

    }

    public void startTurn(StartTurnAction arg) {
        if (arg.option.equals("start")) {
            if (gameHandler.getCurrentPlayerID() == controller.getModel().getCurrentPlayer().getClientID()) {
                gameHandler.singleSend(); //TODO Answer which worker
            }
        }
        if (arg.option.equals("worker1")) {
            actionController.startAction(controller.getModel().getCurrentPlayer().getWorker1());   //TODO need getWorker method
        }
        if (arg.option.equals("worker2")) {
            actionController.startAction(controller.getModel().getCurrentPlayer().getWorker1());  //TODO need getWorker method
        }

    }
}
