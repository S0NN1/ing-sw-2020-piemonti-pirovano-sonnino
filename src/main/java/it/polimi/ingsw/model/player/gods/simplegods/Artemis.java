package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import java.util.List;

/**
 * @author Alice Piemonti
 */
public class Artemis extends Worker {

    private Space oldPosition;

    public Artemis(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setTwoMovePhases();
    }

    /**
     * return an ArrayList that contains the spaces which the worker can move to
     *
     * @param gameBoard GameBoard of the game
     * @return ArrayList of Spaces
     * @throws IllegalArgumentException    if gameBoard is null
     * @throws IllegalThreadStateException if the worker is blocked, so it cannot move
     */
    @Override
    public List<Space> selectMoves(GameBoard gameBoard) {
        phases.get(3).changeMust(true);
        return super.selectMoves(gameBoard);
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
        if(oldPosition == null) {   //first move
            oldPosition = position; //save the actual position: Artemis can't move here again in this turn
            if(super.move(space)){  //try to move
                phases.get(3).changeMust(false);
                return true;
            }
            oldPosition = null; //if super.move return false
            return false;
        }
        else if(oldPosition == space) return false; //it's the second move and Artemis try to move to the previous position
        else{   //second move
            if(super.move(space)){
                phases.get(3).changeMust(false);
                oldPosition = null;
                return true;
            }
        }
        return false;
    }

    /**
     * return true if the worker can move to the space received
     * can't select oldPosition
     * @param space a space of the GameBoard
     * @return boolean value
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == oldPosition) return false;
        return super.isSelectable(space);
    }

    /**
     * notify the selectSpaceListener with all the spaces on which the worker can build
     *
     * @param gameBoard gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     */
    @Override
    public void notifyWithBuildable(GameBoard gameBoard) {
        oldPosition = null;
        super.notifyWithBuildable(gameBoard);
    }
}


