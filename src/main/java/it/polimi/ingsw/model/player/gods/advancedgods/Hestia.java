package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.List;

/**
 * Class Hestia ...
 *
 * @author Alice Piemonti
 * Created on 25/05/2020
 */
public class Hestia extends Worker {

    boolean alreadyBuilt = false;

    /**
     * Constructor
     *
     * @param color player color
     */
    public Hestia(PlayerColors color) {
        super(color);
    }

    /**
     * set the order of action allowed by this worker
     */
    @Override
    public void setPhases() {
        setTwoBuildPhases();
    }

    /**
     * Method resetAlreadyBuild sets alreadyBuilt value to false
     */
    private void resetAlreadyBuilt() { alreadyBuilt = false;}
    /**
     * notify the selectSpacesListener with all the moves the worker can do
     *
     * @param gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalStateException    if the worker is blocked
     * @see Worker#notifyWithMoves(GameBoard)
     */
    @Override
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        resetAlreadyBuilt();
        super.notifyWithMoves(gameBoard);
    }

    /**
     * return an ArrayList which contains all the buildable spaces
     * @param gameBoard gameBoard
     * @return an ArrayList of spaces
     */
    @Override
    public List<Space> getBuildableSpaces(GameBoard gameBoard) {
        List<Space> buildable = super.getBuildableSpaces(gameBoard);
        if (alreadyBuilt && !buildable.isEmpty()) phases.get(5).changeMust(true);
        return buildable;
    }

    /** @see Worker#build(Space) */
    /*
     * check if the space is buildable and build on the space received
     *
     * @param space space
     * @return false if it's impossible to build on the space or if OutOfBoundException is thrown
     * @throws IllegalArgumentException if space is null
     * @see Worker#build(Space)
     */
    @Override
    public boolean build(Space space) throws IllegalArgumentException {
        boolean value = super.build(space);
        if (value) {
            phases.get(5).changeMust(false);
            if (!alreadyBuilt) alreadyBuilt = true;
        }
        return value;
    }

    /**
     * return true if the worker can build into the space received
     *
     * @param space space of the GameBoard
     * @return boolean value
     * @throws IllegalArgumentException if space is null
     * @see Worker#canBuildOnto(Space)
     */
    @Override
    public boolean canBuildOnto(Space space) throws IllegalArgumentException {
        if (!alreadyBuilt) return super.canBuildOnto(space);
        else return !isPerimetric(space) && super.canBuildOnto(space);
    }
}
