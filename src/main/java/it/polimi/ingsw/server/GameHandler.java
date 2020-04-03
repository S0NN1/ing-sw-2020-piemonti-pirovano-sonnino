package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.answers.Answer;

public class GameHandler {
    private Server server;
    private Controller controller;
    private Game game;
    private boolean started;


    public GameHandler(Server server) {
        this.server = server;
        started = false;
        game = new Game();
        controller = new Controller();
    }

    public void startGame() {
    }

    public void setupPlayer(String nickname) {
        game.createNewPlayer(new Player(nickname));
    }

    public void singleSend(Answer message, int ID) {
        server.getClientByID(ID).send(message);
    }

    public void sendAll(Answer message) {
        for(Player player:game.getActivePlayers()) {
            singleSend(message, server.getIDByNickname(player.getNickname()));
        }
    }

    public Controller getController() {
        return controller;
    }

}
