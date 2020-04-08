package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.ArrayList;

/**
 * @author alice
 */
public class Demeter extends Worker {

    private Space oldPosition;

    public Demeter(PlayerColors color) {
        super(color);
    }

    /**
     * set the order of actions allowed by this worker
     */
    @Override
    protected void setPhases() {
        phases.add(new Phase(Action.SELECTMOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECTBUILD,true));
        phases.add(new Phase(Action.BUILD,true));
        phases.add(new Phase(Action.SELECTBUILD,false));
        phases.add(new Phase(Action.BUILD,false));
    }

    /**
     * return an ArrayList which contains all the buildable spaces
     *
     * @param gameBoard gameBoard
     * @return an ArrayList of spaces
     */
    @Override
    public ArrayList<Space> getBuildableSpaces(GameBoard gameBoard) {
        phases.get(5).changeMust(true);
        return super.getBuildableSpaces(gameBoard);
    }

    /**
     * build on the space received
     *
     * @param space space
     * @return false if it's impossible to build on the space or if OutOfBoundException is thrown
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean build(Space space) throws IllegalArgumentException {
        if(oldPosition == null){ //first build
            if(super.build(space)){
                oldPosition = space;
                phases.get(5).changeMust(false);
                return true;
            }
            return false;
        }   //second build
        else if(oldPosition == space) return false; //if try to build to the same space
        else{
            if (super.build(space)) {
                phases.get(5).changeMust(false);
                oldPosition = null;
                return true;
            }
        }
        return false;
    }

    /**
     * return true if the worker can build into the space received
     * Demeter can't build on the same space twice in a turn
     * @param space space of the GameBoard
     * @return boolean value
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean isBuildable(Space space) throws IllegalArgumentException {
        if(oldPosition == space) return false;
        return super.isBuildable(space);
    }

    /**
     * notify the selectSpacesListener with all the moves the worker can do
     *
     * @param gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalStateException    if the worker is blocked
     */
    @Override
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        oldPosition = null;
        super.notifyWithMoves(gameBoard);
    }
}
