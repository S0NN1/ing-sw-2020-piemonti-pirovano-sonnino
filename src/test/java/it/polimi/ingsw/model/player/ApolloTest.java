package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Space;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    @Test
    void move() {
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
}