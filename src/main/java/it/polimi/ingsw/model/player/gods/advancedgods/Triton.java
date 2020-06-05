package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

/**
 * Class Triton defines Triton card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Triton extends Worker {

    private int numberOfMoves = 0;
    /**
     * Constructor create an Triton instance.
     * @param color of type PlayerColors - the player color,
     */
    public Triton(PlayerColors color) {
        super(color);
    }

    /**
     * Method setPhases sets the order of action allowed for this worker.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * Method move changes the worker's position while check winning condition.
     * If Triton moves into a perimeter space it can move another time requires this.isSelectable(space).
     *
     * @param space of type Space - the new position.
     * @return boolean false if the worker can't move into this space, true otherwise.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#move(Space)
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if (isPerimeter(space)) {
            numberOfMoves ++;
            phases.add(numberOfMoves*2, new Phase(Action.SELECT_MOVE, false));
            phases.add((numberOfMoves*2) + 1, new Phase(Action.MOVE, false));
        }
        return super.move(space);
    }

    /**
     * Method resetMoves sets numberOfMoves and phases to the initial state.
     */
    private void resetMoves() {
        numberOfMoves = 0;
        phases.clear();
        setNormalPhases();
    }

    /**
     * Method notifyWithMoves notifies the selectSpacesListener with all the moves the worker can do.
     *
     * @param gameBoard of type GameBoard - the game board.
     * @throws IllegalArgumentException when gameBoard is null.
     * @throws IllegalStateException    when the worker is blocked.
     * @see Worker#notifyWithMoves(GameBoard)
     */
    @Override
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        phases.get(2*numberOfMoves + 1).changeMust(true);
        super.notifyWithMoves(gameBoard);
    }

    /**
     * Method build checks if the space is buildable and build on the space received.
     *
     * @param space of type Space - the position.
     * @return boolean false if it's impossible to build on the space or if OutOfBoundException is thrown, true otherwise.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#build(Space)
     */
    @Override
    public boolean build(Space space) throws IllegalArgumentException {
        resetMoves();
        return super.build(space);
    }
}
