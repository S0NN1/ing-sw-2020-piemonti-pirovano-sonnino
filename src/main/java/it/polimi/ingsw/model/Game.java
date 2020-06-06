package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class contains the main logic of "Santorini", which is divided in several macro-areas.
 * The first area is the Player/Worker section, which contains information about the single player and his god power
 * (overriding build and construct methods).
 * The second area is the GameBoard section, which contains a grid of 25 cells (Space class). That class contains
 * information about the status of the cell, and the presence of a tower in it.
 * The last section is the "MVC Deck creation", which consists in a Model-View-Controller pattern that lets challenger
 * to choose the god powers cards.
 * @author Luca Pirovano

 */

public class Game {

    private final GameBoard gameBoard;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private final Deck deck = new Deck(this);
    private Player currentPlayer;
    private int currentPlayerN;

    /**
     * Constructor Game creates a new Game instance.
     */
    public Game() {
        gameBoard = new GameBoard();
    }


    /**
     * Method getGameBoard returns the gameBoard of this Game object.
     *
     * @return the gameBoard (type GameBoard) of this Game object.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }


    /**
     * Method createNewPlayer creates a new player in the match. The minimum length of activePlayers array is
     * 2 elements, and the maximum is 3.
     *
     * @param player of type Player - the player to be added.
     */
    public void createNewPlayer(Player player) {
        players.add(player);
        activePlayers.add(player);
    }


    /**
     * Method removePlayer removes an active player from the list (loss of workers).
     *
     * @param player of type Player - the player we want to remove from the match.
     */
    public void removePlayer(Player player) {
        if(player.getWorkers().isEmpty()) {
            activePlayers.remove(player);
            if(!activePlayers.isEmpty()) {
                if(currentPlayerN==activePlayers.size()) currentPlayerN=0;
                setCurrentPlayer(activePlayers.get(currentPlayerN));
            }
            return;
        }
        for (int i = Constants.GRID_MIN_SIZE; i <Constants.GRID_MAX_SIZE ; i++) {
            for (int j = Constants.GRID_MIN_SIZE; j <Constants.GRID_MAX_SIZE ; j++) {
                if(gameBoard.getSpace(i,j).getWorker()==player.getWorkers().get(0) ||
                        gameBoard.getSpace(i,j).getWorker()==player.getWorkers().get(1)){
                    gameBoard.getSpace(i,j).setWorker(null);
                }
            }
        }
        activePlayers.remove(player);
        if(!activePlayers.isEmpty()) {
            if(currentPlayerN==activePlayers.size()) currentPlayerN=0;
            setCurrentPlayer(activePlayers.get(currentPlayerN));
        }
    }


    /**
     * Method getPlayerByNickname searches the player identified by his nickname in the list of active player.
     *
     * @param nickname of type String - the nickname of the player.
     * @return Player - the desired player, null if there's no active player with that nickname.
     */
    public Player getPlayerByNickname(String nickname) {
        for (Player player : activePlayers) {
            if (player.getNickname().equalsIgnoreCase(nickname)) {
                return player;
            }
        }
        return null;
    }


    /**
     * Method getPlayerByID gets a player instance relying on his unique ID.
     *
     * @param id of type int - the id of the player.
     * @return Player - the player instance.
     */
    public Player getPlayerByID(int id) {
        for (Player player: activePlayers){
            if(player.getClientID() == id) {
                return player;
            }
        }
        return null;
    }

    /**
     * Method getActivePlayers returns the list of the active players in the match (not dead).
     *
     * @return the activePlayers (type List&lt;Player&gt;) of this Game object.
     */
    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Method setCurrentPlayer updates the variable "currentPlayer" with the desired one.
     *
     * @param player of type Player - the player to be set as current.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        this.currentPlayerN = activePlayers.indexOf(player);
    }

    /**
     * Method getCurrentPlayer returns the currentPlayer of this game.
     *
     * @return the currentPlayer (type Player) of this Game object.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method nextPlayer skips to the next player in "activePlayers" order.
     */
    public void nextPlayer() {
        currentPlayerN=(currentPlayerN == activePlayers.size() - 1 || currentPlayerN == activePlayers.size()) ? 0 :
                currentPlayerN+1;   //Clockwise rotation
        setCurrentPlayer(activePlayers.get(currentPlayerN));
    }

    /**
     * Method getDeck returns the deck of this Game object.
     *
     *
     *
     * @return the deck (type Deck) of this Game object.
     */
    public Deck getDeck() {
        return deck;
    }
}
