package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.*;

import java.util.Observable;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class handles a single match, instantiating a game mode (Game class) and a main controller (Controller class).
 * It also manages the startup phase, like the marker's color selection.
 * @author Luca Pirovano
 */
public class GameHandler extends Observable {
    private Server server;
    private Controller controller;
    private Game game;
    private int started;
    private int playersNumber;


    public GameHandler(Server server) {
        this.server = server;
        started = 0;
        game = new Game();
        controller = new Controller(game, this);
        this.addObserver(controller);
    }

    /**
     * @return if the game has started (the started attribute becomes true after the challenger selection phase).
     */
    public int isStarted() {
        return started;
    }

    /**
     * Set the active players number of the match, decided by the first connected player.
     * @param playersNumber the number of the playing clients.
     */
    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    /**
     * This method receives a nickname from the server application and creates a new player in the game class.
     * @param nickname the chosen nickname, after a duplicates check.
     */
    public void setupPlayer(String nickname, int clientID) {
        game.createNewPlayer(new Player(nickname, clientID));
    }

    public int getCurrentPlayerID() {
        return game.getCurrentPlayer().getClientID();
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
                singleSend(message, player.getClientID());
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
        else if(playersNumber==3 && PlayerColors.notChosen().size()>0) {
            String nickname = game.getActivePlayers().get(playersNumber - PlayerColors.notChosen().size()).getNickname();
            if(PlayerColors.notChosen().size()==1) {
                game.getPlayerByNickname(nickname).setColor(PlayerColors.notChosen().get(0));
                singleSend(new CustomMessage(Constants.ANSI_RED + "\nThe society decides for you! You have the " +
                        PlayerColors.notChosen().get(0) + " color!\n" + Constants.ANSI_RESET), server.getIDByNickname(nickname));
                PlayerColors.choose(PlayerColors.notChosen().get(0));
            }
            else {
                server.getClientByID(server.getIDByNickname(nickname)).send(req);
                sendAllExcept(new CustomMessage("Please wait, user " + nickname + " is choosing his color!"), server.getIDByNickname(nickname));
                return;
            }
        }

        //Challenger section
        Random rnd = new Random();
        game.setCurrentPlayer(game.getActivePlayers().get(rnd.nextInt(playersNumber)));
        singleSend(new CustomMessage(game.getCurrentPlayer().getNickname() + ", you are the challenger!"),
                game.getCurrentPlayer().getClientID());
        singleSend(new GodRequest("You have to choose gods power. Type GODLIST to get a list of available gods, GODDESC " +
                "<god name> to get a god's description and ADDGOD <god name> to add a God power to deck."),
                game.getCurrentPlayer().getClientID());
        sendAllExcept(new CustomMessage(game.getCurrentPlayer().getNickname() + " is the challenger!\nPlease wait while " +
                "he chooses the god powers."), game.getCurrentPlayer().getClientID());
        controller.setSelectionController(game.getCurrentPlayer().getClientID());
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
        if((action instanceof GodSelectionAction)) {
            if (started == 0) {
                setChanged();
                notifyObservers((GodSelectionAction) action);
                if (game.getDeck().getCards().size() == playersNumber) {
                    started = 1;
                    game.nextPlayer();
                    singleSend(new GodRequest(server.getNicknameByID(getCurrentPlayerID()) + ", please choose your" +
                                    "god power from one of the list below by typing CHOOSE <god-name>.\n" +
                                    game.getDeck().getCards().stream().map(e -> e.toString()).collect(Collectors.joining(", "))),
                            getCurrentPlayerID());
                }
            } else if (started == 1) {
                setChanged();
                notifyObservers((GodSelectionAction) action);
                if (game.getDeck().getCards().size() > 1) {
                    game.nextPlayer();
                    singleSend(new GodRequest(server.getNicknameByID(getCurrentPlayerID()) + ", please choose your" +
                                    "god power from one of the list below by typing CHOOSE <god-name>.\n" + game.getDeck().
                                    getCards().stream().map(e -> e.toString()).collect(Collectors.joining(", "))),
                            getCurrentPlayerID());
                }
                else if(game.getDeck().getCards().size()==1) {
                    game.nextPlayer();
                    setChanged();
                    notifyObservers(new GodSelectionAction("LASTSELECTION"));
                }
            }
        }
        else {
            setChanged();
            notifyObservers(action);
            }
        }

    public void unregisterPlayer(int ID) {
        game.removePlayer(game.getPlayerByID(ID));
    }

    public void endGame() {
        sendAll(new ConnectionClosed("Match ended.\nThanks for playing!"));
        for (Player player:game.getActivePlayers()) {
            server.getClientByID(player.getClientID()).getConnection().close();
        }
    }
}