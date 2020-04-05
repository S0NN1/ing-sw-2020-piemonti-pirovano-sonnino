package it.polimi.ingsw.controller;

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
        if (arg) {

        }

    }
}
