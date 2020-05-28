package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.listeners.RemoveBlockListener;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Ares ...
 *
 * @author Alice Piemonti
 * Created on 26/05/2020
 */
public class Ares extends Worker {

    private final String REMOVE_BLOCK_LISTENER = "removeListener";

    /**
     * Constructor
     *
     * @param color player color
     */
    public Ares(PlayerColors color) {
        super(color);
    }

    /**
     * create the Map of listeners
     *
     * @param client virtualClient
     */
    @Override
    public void createListeners(VirtualClient client) {
        super.createListeners(client);
        listeners.addPropertyChangeListener(REMOVE_BLOCK_LISTENER, new RemoveBlockListener(client));
    }

    /**
     * set the order of action allowed by this worker
     */
    @Override
    public void setPhases() {
        setNormalPhases();
        phases.add(new Phase(Action.SELECT_REMOVE, false));
        phases.add(new Phase(Action.REMOVE, false));
    }

    public boolean removeBlock(Space space) {
        try {
            space.getTower().removeLevel();
        } catch (OutOfBoundException e) {
            return false;
        }
        phases.get(5).changeMust(false);
        listeners.firePropertyChange(REMOVE_BLOCK_LISTENER, null, space);
        return true;
    }

    public void notifyWithRemovable(GameBoard gameBoard, Space unmovedWorkerPosition) throws IllegalArgumentException, IllegalStateException {
        if(gameBoard == null || checkUnmovedWorkerPosition(unmovedWorkerPosition)) throw new IllegalArgumentException();
        List<Space> removable = getRemovableSpaces(gameBoard, unmovedWorkerPosition);
        if(removable.isEmpty()) {
            throw new IllegalStateException();
        }
        listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECT_REMOVE, removable);
        phases.get(5).changeMust(true);
    }

    private List<Space> getRemovableSpaces(GameBoard gameBoard, Space unmovedWorkerPosition) {
            ArrayList<Space> removable = new ArrayList<>();
            for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++){
                for(int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++){
                    Space space = gameBoard.getSpace(i,j);
                    if(canRemove(space, unmovedWorkerPosition)){ removable.add(space);}
                }
            }
            return removable;
    }

    public boolean canRemove(Space space, Space unmovedWorkerPosition) {
        return neighbouring(space, unmovedWorkerPosition) && space.isEmpty() && space.getTower().getHeight() > 0 && !space.getTower().isCompleted();
    }

    private boolean neighbouring(Space space, Space unmovedWorkerPosition) {
        return (space.getRow() - unmovedWorkerPosition.getRow() < 2) && (unmovedWorkerPosition.getRow() - space.getRow() < 2) &&
                (space.getColumn() - unmovedWorkerPosition.getColumn() < 2) && (unmovedWorkerPosition.getColumn() - space.getColumn() < 2) &&
                (space.getRow() != unmovedWorkerPosition.getRow() || space.getColumn() != unmovedWorkerPosition.getColumn());
    }

    public boolean checkUnmovedWorkerPosition(Space unmovedWorkerPosition) {
        return unmovedWorkerPosition.getWorker().getWorkerColor().equals(this.workerColor) &&
                (unmovedWorkerPosition.getRow() != this.position.getRow() || unmovedWorkerPosition.getColumn() != this.position.getColumn());
    }
}
