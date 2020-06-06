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
 * Class Ares defines Ares card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Ares extends Worker {

    private static final String REMOVE_BLOCK_LISTENER = "removeListener";

    /**
     * Constructor create an Ares instance.
     * @param color of type PlayerColors - the player's color,
     */
    public Ares(PlayerColors color) {
        super(color);
    }

    /**
     * Method createListener creates a RemoveBlockListener.
     * @param client virtualClient - the virtual client on the server.
     * @see Worker#createListeners(VirtualClient)
     */
    @Override
    public void createListeners(VirtualClient client) {
        super.createListeners(client);
        listeners.addPropertyChangeListener(REMOVE_BLOCK_LISTENER, new RemoveBlockListener(client));
    }

    /**
     * Method setPhases sets the order of action allowed for this worker.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        setNormalPhases();
        phases.add(new Phase(Action.SELECT_REMOVE, false));
        phases.add(new Phase(Action.REMOVE, false));
    }

    /**
     * Method removeBlock removes a level from a building.
     *
     * @param space of type Space  - the selected position.
     * @return boolean true if successful, false otherwise.
     */
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

    /**
     * Method notifyWithRemovable notifies client with possible spaces for a removal.
     *
     * @param gameBoard of type GameBoard  - GameBoard reference.
     * @param unmovedWorkerPosition of type Space - the position of the inactive worker.
     * @throws IllegalArgumentException when receiving a bad position.
     * @throws IllegalStateException when whole list is empty.
     */
    public void notifyWithRemovable(GameBoard gameBoard, Space unmovedWorkerPosition) throws IllegalArgumentException,
            IllegalStateException {
        if(gameBoard == null || !checkUnmovedWorkerPosition(unmovedWorkerPosition)) throw new
                IllegalArgumentException();
        List<Space> removable = getRemovableSpaces(gameBoard, unmovedWorkerPosition);
        if(removable.isEmpty()) {
            throw new IllegalStateException();
        }
        listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECT_REMOVE, removable);
        phases.get(5).changeMust(true);
    }

    /**
     * Method getRemovableSpaces calculates removable spaces.
     *
     * @param gameBoard of type GameBoard - GameBoard reference.
     * @param unmovedWorkerPosition of type Space - the position of the inactive worker.
     * @return List<Space> - the list of spaces.
     */
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

    /**
     * Method canRemove checks if block can be removed.
     *
     * @param space of type Space - the selected space.
     * @param unmovedWorkerPosition of type Space - the position of the inactive worker.
     * @return boolean true if possible, false otherwise.
     */
    public boolean canRemove(Space space, Space unmovedWorkerPosition) {
        return neighbour(space, unmovedWorkerPosition) && space.isEmpty() && space.getTower().getHeight() > 0 &&
                !space.getTower().isCompleted();
    }

    /**
     * Method neighbour checks if space is near.
     *
     * @param space of type Space - the selected space.
     * @param unmovedWorkerPosition of type Space - the position of the inactive worker.
     * @return boolean
     */
    private boolean neighbour(Space space, Space unmovedWorkerPosition) {
        return (space.getRow() - unmovedWorkerPosition.getRow() < 2) && (unmovedWorkerPosition.getRow() -
                space.getRow() < 2) &&
                (space.getColumn() - unmovedWorkerPosition.getColumn() < 2) && (unmovedWorkerPosition.getColumn() -
                space.getColumn() < 2) &&
                (space.getRow() != unmovedWorkerPosition.getRow() || space.getColumn() !=
                        unmovedWorkerPosition.getColumn());
    }

    /**
     * Method checkUnmovedWorkerPosition checks if inactive worker isn't the same as the active one.
     *
     * @param unmovedWorkerPosition of type Space - the position of the inactive worker.
     * @return boolean true if operation is successful, false otherwise.
     */
    public boolean checkUnmovedWorkerPosition(Space unmovedWorkerPosition) {
        return ((unmovedWorkerPosition.getWorker().getWorkerColor().equals(this.workerColor)) &&
                ((unmovedWorkerPosition.getRow() != this.position.getRow()) || (unmovedWorkerPosition.getColumn() !=
                        this.position.getColumn())));
    }
}
