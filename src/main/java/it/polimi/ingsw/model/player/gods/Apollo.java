package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.listeners.DoubleMoveListener;
import it.polimi.ingsw.server.VirtualClient;

/**
 * @author Alice Piemonti
 */
public class Apollo extends Worker {

    public Apollo(PlayerColors color) {
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
        listeners.addPropertyChangeListener("ApolloDoubleMove", new DoubleMoveListener(client));
    }

    /**
     * return true if the worker can move to the space received
     *
     * @param space a space of the GameBoard
     * @return boolean value
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        boolean result =  canMoveTo(space) && (space.getTower().getHeight() - position.getTower().getHeight() < 2);
        if(space.getWorker() == null)   return result;
        return !space.getWorker().getWorkerColor().equals(this.getWorkerColor()) && result;
    }

    /**
     * change the worker's position while check winning condition. This worker can also change his position with a neighboring worker
     * @param space the new position
     * @throws IllegalArgumentException if space is null
     * @return super
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        else if(!space.isEmpty()) {
            Space oldPosition = position;
            space.getWorker().setPosition(oldPosition);
            this.setPosition(space);
            Move myMove = new Move(oldPosition.getRow(),oldPosition.getColumn(),position.getRow(),position.getColumn());
            Move otherMove = new Move(position.getRow(),position.getColumn(),oldPosition.getRow(),oldPosition.getColumn());
            listeners.firePropertyChange("ApolloDoubleMove", myMove,otherMove);
            if(winCondition(oldPosition)){
                listeners.firePropertyChange("winListener", null, null);
            }
            return true;
        }
        else return super.move(space);
    }
}
