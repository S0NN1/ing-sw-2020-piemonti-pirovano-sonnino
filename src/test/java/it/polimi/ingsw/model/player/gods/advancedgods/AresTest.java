package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import org.junit.jupiter.api.BeforeEach;

/**
 * Class AresTest tests Ares class
 *
 * @author Alice Piemonti
 * Created on 26/05/2020
 */
public class AresTest {

    Worker ares1;
    Worker ares2;
    GameBoard gameBoard;

    @BeforeEach
    void init() {
        ares1 = new Ares(PlayerColors.BLUE);
        ares2 = new Ares(PlayerColors.BLUE);
        gameBoard = new GameBoard();
        ares1.setPosition(gameBoard.getSpace(3,4));
    }
}
