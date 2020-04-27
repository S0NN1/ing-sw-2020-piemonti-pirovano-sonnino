package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author Alice Piemonti
 */
public class Athena extends Worker  {

    public Athena(PlayerColors color, TurnController controller) {
        super(color);
        listeners.addPropertyChangeListener("moveUpListener",controller);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * change the worker's position while check winning condition
     * requires this.isSelectable(space)
     *
     * @param space the new position
     * @return false if the worker can't move into this space
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        Space oldPosition = position;
        if(super.move(space)){
            if(position.getTower().getHeight() - oldPosition.getTower().getHeight() == 1){
                listeners.firePropertyChange("moveUpListener", null, "AthenaMovedUp");
            }
            else listeners.firePropertyChange("moveUpListener", null, "AthenaNormalMove");
            return true;
        }
        return false;
    }


}