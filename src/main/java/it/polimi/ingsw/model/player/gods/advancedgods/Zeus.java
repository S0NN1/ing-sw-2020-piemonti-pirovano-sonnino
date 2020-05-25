package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author Alice Piemonti
 */
public class Zeus extends Worker {

    /**
     * Constructor
     *
     * @param color player color
     */
    public Zeus(PlayerColors color) {
        super(color);
    }

    /**
     * set the order of action allowed by this worker
     */
    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * return true if the worker can build into the space received
     *
     * @param space space of the GameBoard
     * @return boolean value
     * @throws IllegalArgumentException if space is null
     * @see Worker#canBuildOnto(Space)
     */
    @Override
    public boolean canBuildOnto(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        if (space.getRow() == position.getRow() && space.getColumn() == position.getColumn()) return space.getTower().getHeight() < 3;
        return super.canBuildOnto(space);
    }
}
