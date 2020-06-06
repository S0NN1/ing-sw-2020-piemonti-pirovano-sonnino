package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;


/**
 * Pan class defines Pan card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Pan extends Worker {

    /**
     * Constructor Worker creates a new Worker instance.
     *
     * @param color of type PlayerColors - the player's color.
     */
    public Pan(PlayerColors color) {
        super(color);
    }

    /**
     * Method setPhases sets phases.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * Method winCondition checks if win condition is satisfied.
     *
     * @param space of type Space - the space provided.
     * @return boolean true if worker has won, false otherwise.
     * @see Worker#winCondition(Space)
     */
    @Override
    public boolean winCondition(Space space) {
        if((space.getTower().getHeight() - position.getTower().getHeight() ) > 1) return true;
        return super.winCondition(space);
    }
}
