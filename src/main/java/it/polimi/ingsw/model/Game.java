package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.CardSelection;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {
    private GameBoard gameBoard;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private Deck deck = new Deck();
    private int challengerNumber;
    private Player currentPlayer;
    private int currentPlayerN;


    /*
        Player control section
     */
    public void createNewPlayer(Player player) {
        players.add(player);
        activePlayers.add(player);
    }
    public void removePlayer(Player player) {
        activePlayers.remove(player);
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    public void currentPlayerName() {
        setChanged();
        notifyObservers("PLAYERNAME:" + currentPlayer.getNickname());
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextPlayer() {
        currentPlayerN=(currentPlayerN == PlayersNumber.playerNumber - 1) ? 0 : currentPlayerN+1;   //Clockwise rotation
        currentPlayer = activePlayers.get(currentPlayerN);
    }

    public void getPlayerList() {
        for(int i=0; i<activePlayers.size(); i++) {
            setChanged();
            notifyObservers("LIST:" + i + ":" + activePlayers.get(i).getNickname());
        }
    }

    /*
        Challenger section
     */

    public void setChallenger(int challengerNumber) {
        this.challengerNumber = challengerNumber;
        this.currentPlayerN = challengerNumber;
    }

    public int getChallenger() {
        return challengerNumber;
    }

    public void createDeck() {
        CardSelectionBoard model = new CardSelectionBoard(deck);
        CardSelection view = new CardSelection();
        GodSelectionController controller = new GodSelectionController(model, view);

        model.addObservers(view);
        view.addObservers(controller);

        view.run();
    }

}
