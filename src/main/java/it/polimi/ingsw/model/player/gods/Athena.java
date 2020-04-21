package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.observer.workerListeners.AthenaMoveUpListener;
import it.polimi.ingsw.server.VirtualClient;

/**
 * @author Alice Piemonti
 */
public class Athena extends Worker  {
    public Athena(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * create the Map of listeners
     *
     * @param client virtualClient
     */
    @Override
    public void createListeners(VirtualClient client) {
        super.createListeners(client);
        listeners.addPropertyChangeListener("moveUpListener",new AthenaMoveUpListener(client));
        //TODO  aggiungere listener per modificare CanMoveUp dei players
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
                listeners.firePropertyChange("moveUpListener", null, null);
           //TODO  aggiungere listener per modificare CanMoveUp dei players
            }
            return true;
        }
        return false;
    }


}