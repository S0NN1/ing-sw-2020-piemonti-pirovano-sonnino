package it.polimi.ingsw.model.board;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.player.Worker;

/**
 * Space class describes a single cell of the GameBoard.
 * @author Nicolò Sonnino
 */
public class Space {
    private int row;
    private int column;
    private Worker workerHere;
    private Tower builtTower;

    /**
     * Constructor Space creates a new Space instance.
     */
    public Space() {
        builtTower = new Tower();
        workerHere = null;
    }

    /**
     * Method getRow returns the row of this Space object.
     *
     * @return the row (type int) of this Space object.
     */
    public int getRow() {
        return row;
    }

    /**
     * Method setRow sets the row of this Space object.
     *
     * @param row the row of this Space object.
     *
     * @throws InvalidInputException when the user input is not in the correct range.
     */
    public void setRow(int row) throws InvalidInputException {
        if (row >= Constants.GRID_MIN_SIZE && row < Constants.GRID_MAX_SIZE) {
            this.row = row;
        } else throw new InvalidInputException();
    }


    /**
     * Method getColumn returns the column of this Space object.
     *
     * @return the column (type int) of this Space object.
     */
    public int getColumn() {
        return column;
    }


    /**
     * Method setY sets the y of this Space object.
     *
     * @param column the y of this Space object.
     *
     * @throws InvalidInputException when
     */
    public void setColumn(int column) throws InvalidInputException {
        if (column >= Constants.GRID_MIN_SIZE && column < Constants.GRID_MAX_SIZE) {
            this.column = column;
        } else {
            throw new InvalidInputException();
        }
    }


    /**
     * Method setTower assigns builtTower to existing Tower.
     *
     * @param tower of type Tower - the tower parsed.
     */
    public void setTower(Tower tower) {
        builtTower = tower;
    }


    /**
     * Method getWorker returns the Worker stored in workerHere.
     *
     * @return the worker (type Worker) reference.
     */
    public Worker getWorker() {
        return workerHere;
    }

    /**
     * Method setWorker sets worker into workerHere variable.
     *
     * @param worker the worker parameter.
     *
     */
    public void setWorker(Worker worker) {
        workerHere = worker;
    }


    /**
     * Method isEmpty returns if Space has a Worker.
     *
     * @return boolean true if workerHere==null, boolean false else.
     */
    public boolean isEmpty() {
        return workerHere == null;
    }


    /**
     * Method getTower returns the Tower stored in builtTower.
     *
     * @return the tower (type Tower) of this Space object.
     */
    public Tower getTower() {
        return builtTower;
    }
}
