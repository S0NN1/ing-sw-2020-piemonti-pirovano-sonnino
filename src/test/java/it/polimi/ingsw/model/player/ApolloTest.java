package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    @Test
    void moveTest() {
        Worker apollo = new Apollo(PlayerColors.RED);
        Worker worker = new Worker((PlayerColors.BLUE));

        Space spaceInit = new Space();
        Space spaceFinal = new Space();
        spaceInit.setWorker(apollo);
        spaceFinal.setWorker(worker);
        apollo.setPosition(spaceInit);
        worker.setPosition(spaceFinal);

        assertFalse(spaceFinal.isEmpty(), "1");
        apollo.move(spaceFinal);

        assertEquals(spaceFinal, apollo.getPosition(),"2");
        assertEquals(spaceInit, worker.getPosition(),"3");
        assertEquals(apollo, spaceFinal.getWorker(),"4");
        assertEquals(worker, spaceInit.getWorker(),"5");
    }

    @Test
    void getMovesTest() {
        Worker worker1 = new Worker(PlayerColors.BLUE);
        Worker worker2 = new Worker(PlayerColors.BLUE);
        Worker worker3 = new Worker(PlayerColors.RED);
        Worker apollo = new Apollo(PlayerColors.RED);

        GameBoard gameBoard = new GameBoard();
        worker1.setPosition(gameBoard.getSpace(0,1));
        worker2.setPosition(gameBoard.getSpace(0,0));
        worker3.setPosition(gameBoard.getSpace(2,1));
        apollo.setPosition(gameBoard.getSpace(1,1));

        int expectedMoves = 8;
        assertEquals(expectedMoves, apollo.getMoves(gameBoard).size());

    }
}