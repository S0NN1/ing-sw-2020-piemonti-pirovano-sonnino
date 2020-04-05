package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.GodRequest;
import it.polimi.ingsw.server.answers.RequestColor;

import java.util.Observable;
import java.util.Random;

/**
 * This class handles a single match, instantiating a game mode (Game class) and a main controller (Controller class).
 * It also manages the startup phase, like the marker's color selection.
 * @author Luca Pirovano
 */
public class GameHandler extends Observable {
    private Server server;
    private Controller controller;
    private Game game;
    private boolean started;
    private int playersNumber;


    public GameHandler(Server server) {
        this.server = server;
        started = false;
        game = new Game();
        controller = new Controller(game, this);
        this.addObserver(controller);
    }

    /**
     * Set the active players number of the match, decided by the first connected player.
     * @param playersNumber the number of the playing clients.
     */
    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    public int getCurrentPlayerID() {
        return server.getIDByNickname(game.getCurrentPlayer().getNickname());
    }

    /**
     * This method receives a nickname from the server application and creates a new player in the game class.
     * @param nickname the chosen nickname, after a duplicates check.
     */
    public void setupPlayer(String nickname) {
        game.createNewPlayer(new Player(nickname));
    }

    /**
     * Send to a client, identified by his ID number, a determined message through the server socket.
     * @param message the message to be sent to the client.
     * @param ID the unique identification number of the client to be contacted.
     */
    public void singleSend(Answer message, int ID) {
        server.getClientByID(ID).send(message);
    }

    /**
     * Same as the previous method, but it iterates on all the clients present in the game. It's a full effects broadcast.
     * @param message the message to broadcast (at single match participants' level).
     */
    public void sendAll(Answer message) {
        for(Player player:game.getActivePlayers()) {
            singleSend(message, server.getIDByNickname(player.getNickname()));
        }
    }

    /**
     * Same as the previous method, but it iterates on all the clients present in the game, except the declared one.
     * @param message the message to be transmitted.
     * @param excludedID the client which will not receive the communication.
     */
    public void sendAllExcept(Answer message, int excludedID) {
        for(Player player:game.getActivePlayers()) {
            if(server.getIDByNickname(player.getNickname())!=excludedID) {
                singleSend(message, server.getIDByNickname(player.getNickname()));
            }
        }
    }

    /**
     * Preliminary player setup phase; in this phase the color of workers' markers will be asked the player, with a
     * double check (on both client and server sides) of the validity of them (also in case of duplicate colors).
     */
    public void setup() {
        RequestColor req = new RequestColor("Please insert a color choosing between BLUE, WHITE or GREY");
        req.addRemaining(PlayerColors.notChosen());
        if(playersNumber==2 && PlayerColors.notChosen().size()>1) {
            String nickname = game.getActivePlayers().get(playersNumber - PlayerColors.notChosen().size() + 1).getNickname();
            singleSend(req, server.getIDByNickname(nickname));
            //server.getClientByID(server.getIDByNickname(game.getActivePlayers().get(playersNumber - PlayerColors.notChosen().size() + 1).getNickname())).send(req);
            sendAllExcept(new CustomMessage("Please wait, user " + nickname + " is choosing his color!"), server.getIDByNickname(nickname));
            return;
        }
        else if(playersNumber==3) {
            String nickname = game.getActivePlayers().get(playersNumber - PlayerColors.notChosen().size()).getNickname();
            server.getClientByID(server.getIDByNickname(nickname)).send(req);
            sendAllExcept(new CustomMessage("Please wait, user " + nickname + " is choosing his color!"), server.getIDByNickname(nickname));
            return;
        }

        //Challenger section
        Random rnd = new Random();
        game.setCurrentPlayer(game.getActivePlayers().get(rnd.nextInt(playersNumber)));
        singleSend(new CustomMessage(game.getCurrentPlayer().getNickname() + ", you are the challenger!"),
                server.getIDByNickname(game.getCurrentPlayer().getNickname()));
        singleSend(new GodRequest("You have to choose gods power. Type GODLIST to get a list of available gods, DESC " +
                "<god name> to get a god's description and ADDGOD <god name> to add a God power to deck."),
                server.getIDByNickname(game.getCurrentPlayer().getNickname()));
        sendAllExcept(new CustomMessage(game.getCurrentPlayer().getNickname() + " is the challenger!\nPlease wait while " +
                "he chooses the god powers."), server.getIDByNickname(game.getCurrentPlayer().getNickname()));
        controller.setSelectionController(server.getIDByNickname(game.getCurrentPlayer().getNickname()));
    }

    /**
     * @return the main game manager controller.
     * @see it.polimi.ingsw.controller.Controller for more information.
     */
    public Controller getController() {
        return controller;
    }

    public Server getServer() {
        return server;
    }

    public void makeAction(UserAction action) {
        if(action instanceof GodSelectionAction) {
            setChanged();
            notifyObservers((GodSelectionAction)action);
        }
    }

}
