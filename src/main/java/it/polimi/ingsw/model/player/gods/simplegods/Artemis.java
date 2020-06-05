package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import java.util.List;

/**
 * Class Artemis defines Artemis card.
 * @author Alice Piemonti
 * @see Worker
 */
public class Artemis extends Worker {

    private Space oldPosition;

    /**
     * Constructor Artemis creates a new Artemis instance.
     *
     * @param color of type PlayerColor - the player color.
     */
    public Artemis(PlayerColors color) {
        super(color);
    }

    /**
     * Method setPhases set the order of the actions allowed by this worker, which can move twice.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        setTwoMovePhases();
    }

    /**
     * Method selectMoves returns an ArrayList that contains the spaces which the worker can move to.
     *
     * @param gameBoard of GameBoard - the game board of the game.
     * @return List<Space></Space> - the list of spaces.
     * @throws IllegalArgumentException    when gameBoard is null.
     * @throws IllegalThreadStateException when the worker is blocked, so it cannot move.
     * @see Worker#selectMoves(GameBoard)
     */
    @Override
    public List<Space> selectMoves(GameBoard gameBoard) {
        phases.get(3).changeMust(true);
        return super.selectMoves(gameBoard);
    }

    /**
     * Method move changes the worker's position while check winning condition.
     *
     * @param space of type Space - the new position.
     * @return boolean true if the worker moved correctly to space, false if the worker did not move to space.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#move(Space)
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
        else if(oldPosition == space) return false; //it's the second move and Artemis try to move to the previous
            // position
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
     * Method isSelectable returns if the worker can move to the space received. This worker can move twice but can not
     * select oldPosition (the space where he was previously) as second move.
     * @param space of type Space - a space of the GameBoard.
     * @return boolean true if the worker can move to space.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#isSelectable(Space)
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == oldPosition) return false;
        return super.isSelectable(space);
    }

    /**
     * Method notifyWithBuildable notifies the selectSpaceListener with all the spaces on which the worker can build.
     *
     * @param gameBoard of type GameBoard - the game board.
     * @throws IllegalArgumentException when gameBoard is null.
     * @see Worker#notifyWithBuildable(GameBoard)
     */
    @Override
    public void notifyWithBuildable(GameBoard gameBoard) {
        oldPosition = null;
        super.notifyWithBuildable(gameBoard);
    }
}


