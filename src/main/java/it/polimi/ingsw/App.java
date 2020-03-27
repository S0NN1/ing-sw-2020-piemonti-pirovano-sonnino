package it.polimi.ingsw;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.PlayersNumber;
import it.polimi.ingsw.view.CardSelection;


public class App 
{
    public static void main( String[] args ) throws DuplicateColorException, DuplicateNicknameException {
        Game game = new Game();
        game.createNewPlayer(new Player("piro", PlayerColors.BLUE));
        game.createNewPlayer(new Player("PIRO", PlayerColors.GREEN));
        game.createNewPlayer(new Player("Sonny", PlayerColors.RED));
        PlayersNumber.setPlayer(3);
        game.createDeck();
    }
}
