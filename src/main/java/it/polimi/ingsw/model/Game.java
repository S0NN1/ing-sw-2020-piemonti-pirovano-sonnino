package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This class contains the main logic of "it.polimi.ingsw.Santorini", which is divided in several macro-areas.
 * The first area is the Player/Worker section, which contains information about the single player and his god power
 * (overriding build and construct methods).
 * The second area is the GameBoard section, which contains a grid of 25 cells (Space class). That class contains
 * information about the status of the cell, and the presence of a tower in it.
 * The last section is the "MVC Deck creation", which consists in a Model-View-Controller pattern that lets challenger
 * to choose the god powers cards.
 * @author Luca Pirovano
 * @version 1.0.1
 */

public class Game extends Observable {
    private GameBoard gameBoard;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private Deck deck = new Deck(this);
    private int challengerNumber;
    private Player currentPlayer;
    private int currentPlayerN;


    /**
     * Create a new player in the match. The minimum length of activePlayers array is 2 elements, and the maximum is 3.
     * @param player the player to be added.
     */
    public void createNewPlayer(Player player) {
        players.add(player);
        activePlayers.add(player);
    }

    /**
     * Remove an active player from the list (loss of workers).
     * @param player we want to remove from the match.
     */
    public void removePlayer(Player player) {
        activePlayers.remove(player);
        if(!activePlayers.isEmpty()) {
            if(currentPlayerN==activePlayers.size()) currentPlayerN=0;
            setCurrentPlayer(activePlayers.get(currentPlayerN));
        }
    }

    /**
     * Search the player identified by his nickname in the list of active player.
     * @param nickname of the player.
     * @return the desired player, null if there's no active player with that nickname.
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
     * Get a player instance relying on his unique ID.
     * @param ID the id of the player.
     * @return the player instance.
     */
    public Player getPlayerByID(int ID) {
        for (Player player: activePlayers){
            if(player.getClientID() == ID) {
                return player;
            }
        }
        return null;
    }

    /**
     * @return the list of the active players in the match (not dead).
     */
    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Update the variable "currentPlayer" with the desired one.
     * @param player the player we want to set as current.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        this.currentPlayerN = activePlayers.indexOf(player);
    }

    /**
     * @return the current player (value of the variable currentPlayer).
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Skip to the next player in "activePlayers" order.
     */
    public void nextPlayer() {
        currentPlayerN=(currentPlayerN == activePlayers.size() - 1 || currentPlayerN == activePlayers.size()) ? 0 : currentPlayerN+1;   //Clockwise rotation
        setCurrentPlayer(activePlayers.get(currentPlayerN));
    }

    /**
     * @return the challenger ID: his position number in "activePlayers" array.
     */
    public int getChallenger() {
        return challengerNumber;
    }

    /**
     * @return the cards deck.
     */
    public Deck getDeck() {
        return deck;
    }
}
