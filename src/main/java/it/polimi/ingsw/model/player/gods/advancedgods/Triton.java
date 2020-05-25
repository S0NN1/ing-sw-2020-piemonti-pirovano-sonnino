package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * Class Triton ...
 *
 * @author Alice Piemonti
 * Created on 25/05/2020
 */
public class Triton extends Worker {

    private int numberOfMoves = 0;
    /**
     * Constructor
     *
     * @param color player color
     */
    public Triton(PlayerColors color) {
        super(color);
    }

    /**
     * set the order of action allowed by this worker
     */
    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * change the worker's position while check winning condition. If Triton moves into a perimeter space it can move another time.
     * requires this.isSelectable(space)
     *
     * @param space the new position
     * @return false if the worker can't move into this space
     * @throws IllegalArgumentException if space is null
     * @see Worker#move(Space)
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if (isPerimetric(space)) {
            numberOfMoves ++;
            phases.add(numberOfMoves*2, new Phase(Action.SELECTMOVE, false));
            phases.add((numberOfMoves*2) + 1, new Phase(Action.MOVE, false));
        }
        return super.move(space);
    }

    /**
     * Method resetMoves sets numberOfMoves and phases to the initial state
     */
    private void resetMoves() {
        numberOfMoves = 0;
        phases.clear();
        setNormalPhases();
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
        phases.get(2*numberOfMoves + 1).changeMust(true);
        super.notifyWithMoves(gameBoard);
    }

    /**
     * check if the space is buildable and build on the space received
     *
     * @param space space
     * @return false if it's impossible to build on the space or if OutOfBoundException is thrown
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean build(Space space) throws IllegalArgumentException {
        resetMoves();
        return super.build(space);
    }
}
