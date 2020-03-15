package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.view.CardSelection;

import java.util.ArrayList;

public class Board {
    private Space[][] grid;
    private ArrayList<Player> players;
    private Player currentPlayer;

    public Board() {
        grid = new Space[5][5];
        players = new ArrayList<Player>();
    }

    public void createNewPlayer(String nickname, PlayerColors color) {
        players.add(new Player(nickname, color));
    }

    public void cardChoice() {
        CardSelectionBoard model = new CardSelectionBoard();
        CardSelection view = new CardSelection();
        GodSelectionController controller = new GodSelectionController(model, view);

        model.addObserver(view);
        view.addObserver(controller);

        view.run();
    }

    public void setup() {
        cardChoice();
    }
}
