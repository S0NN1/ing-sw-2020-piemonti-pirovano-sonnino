package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.*;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class handles a single match, instantiating a game mode (Game class) and a main controller (Controller class).
 * It also manages the startup phase, like the marker's color selection.
 * @author Luca Pirovano
 */
public class GameHandler {
    private Server server;
    private Controller controller;
    private Game game;
    private int started;
    private int playersNumber;
    private PropertyChangeSupport controllerListener = new PropertyChangeSupport(this);


    public GameHandler(Server server) {
        this.server = server;
        started = 0;
        game = new Game();
        controller = new Controller(game, this);
        controllerListener.addPropertyChangeListener(controller);
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
        RequestColor req = new RequestColor("Please choose your workers' color.");
        req.addRemaining(PlayerColors.notChosen());
        if(playersNumber==2 && PlayerColors.notChosen().size()>1) {
            String nickname = game.getActivePlayers().get(playersNumber - PlayerColors.notChosen().size() + 1).getNickname();
            singleSend(req, server.getIDByNickname(nickname));
            sendAllExcept(new CustomMessage(Constants.ANSI_RED + "Please wait, user " + nickname +
                    " is choosing his color!" + Constants.ANSI_RESET, false), server.getIDByNickname(nickname));
            return;
        }
        else if(playersNumber==3 && PlayerColors.notChosen().size()>0) {
            String nickname = game.getActivePlayers().get(playersNumber - PlayerColors.notChosen().size()).getNickname();
            if(PlayerColors.notChosen().size()==1) {
                game.getPlayerByNickname(nickname).setColor(PlayerColors.notChosen().get(0));
                singleSend(new CustomMessage(Constants.ANSI_RED + "\nThe society decides for you! You have the " +
                        PlayerColors.notChosen().get(0) + " color!\n" + Constants.ANSI_RESET, false), server.getIDByNickname(nickname));
                PlayerColors.choose(PlayerColors.notChosen().get(0));
            }
            else {
                server.getClientByID(server.getIDByNickname(nickname)).send(req);
                sendAllExcept(new CustomMessage("Please wait, user " + nickname + " is choosing his color!", false), server.getIDByNickname(nickname));
                return;
            }
        }

        //Challenger section
        Random rnd = new Random();
        game.setCurrentPlayer(game.getActivePlayers().get(rnd.nextInt(playersNumber)));
        singleSend(new CustomMessage(Constants.ANSI_GREEN + game.getCurrentPlayer().getNickname() +
                        ", you are the challenger!" + Constants.ANSI_RESET, false), game.getCurrentPlayer().getClientID());
        singleSend(new ChallengerMessages("You have to choose gods power. Type GODLIST to get a list of available gods, GODDESC " +
                "<god name> to get a god's description and ADDGOD <god name> to add a God power to deck.\n" + Constants.ANSI_YELLOW +
                (playersNumber - game.getDeck().getCards().size()) + " gods left." + Constants.ANSI_RESET),
                game.getCurrentPlayer().getClientID());
        sendAllExcept(new CustomMessage(Constants.ANSI_RED + game.getCurrentPlayer().getNickname() + " is the challenger! Please wait while " +
                "he chooses the god powers." + Constants.ANSI_RESET, false), game.getCurrentPlayer().getClientID());
        controller.setSelectionController(game.getCurrentPlayer().getClientID());
    }

    /**
     * @return the main game manager controller.
     * @see it.polimi.ingsw.controller.Controller for more information.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @return the server class.
     */
    public Server getServer() {
        return server;
    }

    /**
     * Handles an action received from a single client. It makes several instance checks. It's based on the value of
     * "started", which represents the current game phase, in this order:
     *  - 0: challenger phase;
     *  - 1: players select their god powers;
     *  - 2: the game has started.
     * @param action the action sent by the client.
     */
    public void makeAction(UserAction action) {
        if((action instanceof ChallengerPhaseAction)) {
            ChallengerPhaseAction userAction = (ChallengerPhaseAction)action;
            if (started == 0) {
                if((userAction.action.equals("CHOOSE"))) {
                    singleSend(new ChallengerMessages(Constants.ANSI_RED + "Error: not in correct game phase for " +
                            "this command!" + Constants.ANSI_RESET), getCurrentPlayerID());
                    return;
                }
                controllerListener.firePropertyChange(null, null, action);
                if (game.getDeck().getCards().size() == playersNumber) {
                    started = 1;
                    game.nextPlayer();
                    singleSend(new ChallengerMessages(Constants.ANSI_GREEN +server.getNicknameByID(getCurrentPlayerID()) +
                            ", please choose your god power from one of the list below.\n\n" + Constants.ANSI_RESET +
                            game.getDeck().getCards().stream().map(e -> e.toString() + "\n" + e.godsDescription() + "\n").
                                    collect(Collectors.joining("\n")) + "Select your god by typing choose <god-name>:"),getCurrentPlayerID());
                    sendAllExcept(new CustomMessage(Constants.ANSI_RED + "Player " + game.getCurrentPlayer().getNickname() +
                            " is" + " choosing his god power..." + Constants.ANSI_RESET, false), getCurrentPlayerID());
                }
            }
            else if (started == 1) {
                if(userAction.action.equals("CHOOSE")) {
                    controllerListener.firePropertyChange(null, null, userAction);
                    if (game.getDeck().getCards().size() > 1) {
                        game.nextPlayer();
                        singleSend(new ChallengerMessages(Constants.ANSI_GREEN + server.getNicknameByID(getCurrentPlayerID()) +
                                ", please choose your god power from one of the list below.\n\n" + Constants.ANSI_RESET + game.getDeck().
                                getCards().stream().map(e -> e.toString() + "\n" + e.godsDescription() + "\n").collect(Collectors.joining("\n ")) +
                                "Select your god by typing CHOOSE " + "<god-name>:"), getCurrentPlayerID());
                        sendAllExcept(new CustomMessage(Constants.ANSI_RED + "Player " + game.getCurrentPlayer().getNickname() +
                                " is choosing his god power..." + Constants.ANSI_RESET, false), getCurrentPlayerID());
                    } else if (game.getDeck().getCards().size() == 1) {
                        game.nextPlayer();
                        controllerListener.firePropertyChange(null, null, new ChallengerPhaseAction("LASTSELECTION"));
                        game.nextPlayer();
                        ArrayList<String> players = new ArrayList<>();
                        game.getActivePlayers().forEach(n -> players.add(n.getNickname()));
                        game.nextPlayer();
                        singleSend(new ChallengerMessages(Constants.ANSI_GREEN + game.getCurrentPlayer().getNickname() +
                                ", choose the starting player!" + Constants.ANSI_RESET, true, players), game.getCurrentPlayer().getClientID());
                        sendAllExcept(new CustomMessage(Constants.ANSI_RED + "Player " + game.getCurrentPlayer().getNickname() + " is " +
                                " choosing the starting player, please wait!" + Constants.ANSI_RESET, false), game.getCurrentPlayer().getClientID());
                        started = 2;
                    }
                }
                else {
                    singleSend(new ChallengerMessages(Constants.ANSI_RED + "Error: not in correct game phase for this command!"
                            + Constants.ANSI_RESET), getCurrentPlayerID());
                }
            }
            else if(userAction.startingPlayer!=null) {
                game.setCurrentPlayer(game.getActivePlayers().get(userAction.startingPlayer));
                singleSend(new CustomMessage(Constants.ANSI_GREEN + game.getCurrentPlayer().getNickname() +
                        ", you are the first player; let's go!" + Constants.ANSI_RESET, false), getCurrentPlayerID());
                sendAllExcept(new CustomMessage(Constants.ANSI_YELLOW + "Well done! " + game.getCurrentPlayer().getNickname()
                        + " is the first player!" + Constants.ANSI_RESET, false), getCurrentPlayerID());
            }
        }
        else {
            controllerListener.firePropertyChange(null, null, action);
            }
        }

    /**
     * Unregister a player identified by his unique ID, after a disconnection event or message.
     * @param ID the unique id of the client to be unregistered.
     */
    public void unregisterPlayer(int ID) {
        game.removePlayer(game.getPlayerByID(ID));
    }

    /**
     * Terminates the game, disconnecting all the players. This method can be invoked after a disconnection of a player
     * or after a win condition. It also unregisters each client connected to the server, freeing a new lobby.
     */
    public void endGame() {
        sendAll(new ConnectionMessage("Match ended.\nThanks for playing!", 1));
        for (Player player:game.getActivePlayers()) {
            server.getClientByID(player.getClientID()).getConnection().close();
        }
    }
}