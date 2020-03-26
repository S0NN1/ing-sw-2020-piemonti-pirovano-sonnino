package it.polimi.ingsw;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.PlayersNumber;
import it.polimi.ingsw.view.CardSelection;


public class App 
{
    public static void main( String[] args ) {
        Game game = new Game();
        game.createNewPlayer(new Player("piro", PlayerColors.BLUE));
        game.createNewPlayer(new Player("piro", PlayerColors.WHITE));
        PlayersNumber.setPlayer(2);
        game.createDeck();
    }
}
