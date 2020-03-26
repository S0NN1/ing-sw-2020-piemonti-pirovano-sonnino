package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.CardSelection;

import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Luca Pirovano
 */

public class Game extends Observable {
    private GameBoard gameBoard;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private Deck deck = new Deck();
    private int challengerNumber;
    private Player currentPlayer;
    private int currentPlayerN;



    public void createNewPlayer(Player player) {
        players.add(player);
        activePlayers.add(player);
    }

    /**
     * Remove an active player from the list (loss of workers).
     * @param player the player we want to remove from the match.
     */
    public void removePlayer(Player player) {
        activePlayers.remove(player);
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
        currentPlayer = activePlayers.get(currentPlayerN);
    }

    /*
        Challenger section
     */

    /**
     * @return the challenger ID: his position number in "activePlayers" array.
     */
    public int getChallenger() {
        return challengerNumber;
    }

    /**
     * Create a new deck with God Powers. The challenger decides the cards he wants to put inside. MVC Local Pattern.
     */
    public void createDeck() {
        CardSelectionBoard model = new CardSelectionBoard(deck);
        CardSelection RemoteView = new CardSelection();
        GodSelectionController controller = new GodSelectionController(model, RemoteView);

        model.addObservers(RemoteView);
        RemoteView.addObservers(controller);

        RemoteView.run();
        System.out.println("LMAO");
    }

}
