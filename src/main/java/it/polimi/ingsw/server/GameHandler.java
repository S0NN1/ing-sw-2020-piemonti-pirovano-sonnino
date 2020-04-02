package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

public class GameHandler {
    private Server server;
    private Controller controller;
    private Game game;
    private boolean started;

    public GameHandler(Server server) {
        this.server = server;
        started = false;
    }

    public void startGame() {

    }

    public void setMVC() {
        Controller controller = new Controller();
    }
}
