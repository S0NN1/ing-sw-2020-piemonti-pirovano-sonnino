package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author alice
 */
public class Atlas extends Worker {
    public Atlas(PlayerColors color) {
        super(color);
    }

    /**
     * build a dome at any level of the tower built on space
     * @param space space
     */
    @Override
    public void build(Space space, Boolean buildDome) throws IllegalArgumentException, OutOfBoundException {
        if(space == null) throw new IllegalArgumentException();
        if(buildDome){
            space.getTower().setDome(true);
        }
        else    super.build(space, buildDome);
    }
}
