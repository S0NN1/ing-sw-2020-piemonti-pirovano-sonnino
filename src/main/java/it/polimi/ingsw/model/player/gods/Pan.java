package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;


/**
 * @author Alice Piemonti
 */
public class Pan extends Worker {

    public Pan(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }

    @Override
    public boolean winCondition(Space space) {
        if((space.getTower().getHeight() - position.getTower().getHeight() ) > 1) return true;
        return super.winCondition(space);
    }
}
