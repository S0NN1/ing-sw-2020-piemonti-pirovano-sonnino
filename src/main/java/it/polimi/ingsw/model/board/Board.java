package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.view.CardSelection;

import java.util.ArrayList;
import java.util.Observable;

public class Board extends Observable implements Cloneable {
    private Space[][] grid = new Space[5][5];
    private ArrayList<Player> players;
    private Player currentPlayer;

    public Board() {
        players = new ArrayList<Player>();
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                grid[i][j] = new Space();
                this.grid[i][j].setX(i);
                this.grid[i][j].setY(j);
            }
        }
    }

    public void createNewPlayer(String nickname, PlayerColors color) {
        players.add(new Player(nickname, color));
    }

    public Space getSpace(int x, int y) throws ArrayIndexOutOfBoundsException{
        return grid[x][y];
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
