package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.List;

/**
 * Class Hestia defines Hestia card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Hestia extends Worker {

    boolean alreadyBuilt = false;

    /**
     * Constructor create an Hestia instance.
     * @param color of type PlayerColors - the player color,
     */
    public Hestia(PlayerColors color) {
        super(color);
    }

    /**
     * Method setPhases sets the order of action allowed for this worker.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        setTwoBuildPhases();
    }

    /**
     * Method resetAlreadyBuild sets alreadyBuilt value to false.
     */
    private void resetAlreadyBuilt() { alreadyBuilt = false;}
    /**
     * Method notifyWithMoves notifies the selectSpacesListener with all the moves the worker can do.
     *
     * @param gameBoard of type GameBoard- the game board .
     * @throws IllegalArgumentException when gameBoard is null.
     * @throws IllegalStateException    when the worker is blocked.
     * @see Worker#notifyWithMoves(GameBoard)
     */
    @Override
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        resetAlreadyBuilt();
        super.notifyWithMoves(gameBoard);
    }

    /**
     * Method getBuildableSpaces returns an ArrayList containing all the buildable spaces.
     * @param gameBoard of type GameBoard- the game board .
     * @return ArrayList - the list of spaces.
     * @see Worker#getBuildableSpaces(GameBoard)
     */
    @Override
    public List<Space> getBuildableSpaces(GameBoard gameBoard) {
        List<Space> buildable = super.getBuildableSpaces(gameBoard);
        if (alreadyBuilt && !buildable.isEmpty()) phases.get(5).changeMust(true);
        return buildable;
    }

    /**
     * Method build checks if the space is buildable and build on the space received.
     *
     * @param space of type Space - the position.
     * @return boolean false if it's impossible to build on the space or if OutOfBoundException is thrown.
     * @throws IllegalArgumentException when space is null.
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
     * Method canBuildOnto returns true if the worker can build into the space received.
     *
     * @param space of type Space - the position.
     * @return boolean true if successful, false otherwise.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#canBuildOnto(Space)
     */
    @Override
    public boolean canBuildOnto(Space space) throws IllegalArgumentException {
        if (!alreadyBuilt) return super.canBuildOnto(space);
        else return !isPerimeter(space) && super.canBuildOnto(space);
    }
}
