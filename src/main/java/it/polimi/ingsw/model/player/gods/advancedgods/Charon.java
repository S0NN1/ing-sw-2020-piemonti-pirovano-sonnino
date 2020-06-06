package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.ArrayList;
import java.util.List;


/**
 * Class Charon defines Charon Card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Charon extends Worker {
    /**
     * Constructor
     *
     * @param color player color
     */
    public Charon(PlayerColors color) {
        super(color);
    }

    /**
     * Method setPhases sets the order of action allowed for this worker.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        phases.add(new Phase(Action.SELECT_FORCE_WORKER, false));
        phases.add(new Phase(Action.FORCE_WORKER, false));
        setNormalPhases();
    }

    /**
     * Method calculateCoordinates gets the coordinates where the opponent worker is forced to move.
     *
     * @param space of type Space - the selected space.
     * @return Couple - the coordinates.
     */
    private Couple calculateCoordinates(Space space) {
        int row;
        int column;
        //calculate row
        if (space.getRow() == position.getRow()) {
            row = position.getRow();
        } else if (space.getRow() > position.getRow()) { //move toward north
            row = position.getRow() - 1;
        } else { //move toward south
            row = position.getRow() + 1;
        }
        //calculate column
        if (space.getColumn() == position.getColumn()) {
            column = position.getColumn();
        } else if (space.getColumn() > position.getColumn()) { //move toward west
            column = position.getColumn() - 1;
        } else {  //move toward east
            column = position.getColumn() + 1;
        }

        return new Couple(row, column);
    }

    /**
     * Method forceWorker indicates whether an opponent worker is forced to the space directly on the other side of
     * Charon.
     *
     * @param space     of type Space - the opponent worker's position.
     * @param gameBoard of type GameBoard - GameBoard reference.
     * @return boolean true if operation is permitted, false otherwise.
     */
    public boolean forceWorker(Space space, GameBoard gameBoard) {
        if(canForceFrom(space, gameBoard)){
            Space newOpponentPosition = otherSideSpace(space, gameBoard);
            if( newOpponentPosition != null && canForceOn(newOpponentPosition)) {
                space.getWorker().setPosition(newOpponentPosition);
                space.setWorker(null);
                listeners.firePropertyChange(MOVE_LISTENER, space, newOpponentPosition);
                phases.get(1).changeMust(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Method notifyWithForceWorkerSpaces notifies SELECT_SPACES_LISTENER with all the spaces where Charon can apply
     * his power.
     *
     * @param gameBoard of type GameBoard - GameBoard reference.
     * @throws IllegalArgumentException when gameBoard is null.
     * @throws IllegalStateException when there are no spaces on which Charon can apply his power.
     */
    public void notifyWithForceWorkerSpaces(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException
    {
        if(gameBoard == null) throw new IllegalArgumentException();
        List<Space> forceWorkerSpaces = selectForceWorkerSpaces(gameBoard);
        if(forceWorkerSpaces.isEmpty()) {
            throw new IllegalStateException();
        }
        listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECT_FORCE_WORKER, forceWorkerSpaces);
        phases.get(1).changeMust(true);
    }

    /**
     * Method selectForceWorkerSpaces gets an ArrayList with all the spaces where Charon can use his power.
     *
     * @param gameBoard of type GameBoard - gameBoard.
     * @return List&lt;Space&gt; - the list of  spaces where Charon can use his power, null if there are not space selectable.
     */
    private List<Space> selectForceWorkerSpaces(GameBoard gameBoard) {
        ArrayList<Space> spaces = new ArrayList<>();
        for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
            for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
                Space space = gameBoard.getSpace(i, j);
                if (canForceFrom(space, gameBoard)) {
                    spaces.add(space);
                }
            }
        }
        return spaces;
    }

    /**
     * Method canForceFrom indicates whether Charon can apply his power on the space received.
     *
     * @param space of type Space - the position where force worker would be applied.
     * @param gameBoard of type GameBoard - gameBoard.
     * @return boolean true if Charon can apply his power on the space received, false otherwise.
     */
    private boolean canForceFrom(Space space, GameBoard gameBoard) {
        if (space == null || !isReachable(space) || space.isEmpty() ||
                space.getWorker().getWorkerColor().equals(workerColor)) return false;
        Space newOpponentPosition = otherSideSpace(space, gameBoard);
        return newOpponentPosition != null && canForceOn(newOpponentPosition);
    }

    /**
     * Method otherSideSpace gets the space directly on the other side of an opponent worker near this worker.
     *
     * @param space of type Space - where opponent worker is positioned
     * @param gameBoard of type GameBoard - gameBoard.
     * @return Space - the new position of opponent worker.
     */
    public Space otherSideSpace(Space space, GameBoard gameBoard) {
        Couple newCoordinates = calculateCoordinates(space);
        if (exists(newCoordinates)) {
            return gameBoard.getSpace(newCoordinates.getRow(), newCoordinates.getColumn());
        }
        return null;
    }
}