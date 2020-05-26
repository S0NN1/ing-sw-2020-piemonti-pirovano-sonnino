package it.polimi.ingsw.model.player.gods.advancedgods;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;


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
     * set the order of action allowed by this worker
     */
    @Override
    public void setPhases() {
        phases.add(new Phase(Action.FORCEWORKER, false));
        setNormalPhases();
    }

    /**
     * Method calculateCoordinates gets the coordinates where the opponent worker is forced to move
     *
     * @param space of type Space
     * @return Couple of coordinates
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
     * Method forceWorker indicates whether an opponent worker is forced to the space directly on the other side of Charon
     *
     * @param space     of type Space opponent worker's position
     * @param gameBoard of type GameBoard
     * @return boolean
     */
    public boolean forceWorker(Space space, GameBoard gameBoard) {
        if (space == null || space.isEmpty() || space.getWorker().getWorkerColor().equals(workerColor)|| !isReachable(space)) return false;
        Space newOpponentPosition = otherSideSpace(space, gameBoard);
        if (newOpponentPosition != null && canForceOn(newOpponentPosition)) {
            space.getWorker().setPosition(newOpponentPosition);
            space.setWorker(null);
            return true;
        }
        return false;
    }

    /**
     * Method otherSideSpace gets the space directly on the other side of an opponent worker neighbouring this worker
     *
     * @param space of type Space where opponent worker is positioned
     * @param gameBoard of type GameBoard
     * @return Space the new position of opponent worker
     */
    public Space otherSideSpace(Space space, GameBoard gameBoard) {
        Couple newCoordinates = calculateCoordinates(space);
        if (exists(newCoordinates)) {
            return gameBoard.getSpace(newCoordinates.getRow(), newCoordinates.getColumn());
        }
        return null;
    }
}