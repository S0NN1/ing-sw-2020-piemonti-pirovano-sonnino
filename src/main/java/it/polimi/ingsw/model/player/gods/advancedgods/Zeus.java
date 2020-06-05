package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * Class Zeus defines Zeus card.
 * @author Alice Piemonti
 * @see Worker
 */
public class Zeus extends Worker {

    /**
     * Constructor create an Zeus instance.
     * @param color of type PlayerColors - the player color,
     */
    public Zeus(PlayerColors color) {
        super(color);
    }

    /**
     * Method setPhases sets the order of action allowed for this worker.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * Method canBuildOnto returns true if the worker can build into the space received.
     *
     * @param space of type Space - the space of the GameBoard.
     * @return boolean true if successful, false otherwise.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#canBuildOnto(Space)
     */
    @Override
    public boolean canBuildOnto(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        if (space.getRow() == position.getRow() && space.getColumn() == position.getColumn()) return
                space.getTower().getHeight() < 3;
        return super.canBuildOnto(space);
    }
}
