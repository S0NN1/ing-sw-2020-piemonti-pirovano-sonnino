package it.polimi.ingsw.model.board;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author Nicolò Sonnino
 * @version 1.0
 * <h1>Class describing spaces</h1>
 */
public class Space {
    private int row;
    private int column;
    private Worker workerHere;
    private Tower builtTower;

    /**
     * Constructor
     */
    public Space() {
        builtTower = new Tower();
        workerHere = null;
    }

    /**
     * Get x value
     *
     * @return x position
     */
    public int getRow() {
        return row;
    }

    /**
     * Set x value
     *
     * @param row position
     * @exception InvalidInputException input isn't between 0 and 4
     */
    public void setRow(int row) throws InvalidInputException {
        if (row >= Constants.GRID_MIN_SIZE && row < Constants.GRID_MAX_SIZE) {
            this.row = row;
        } else throw new InvalidInputException();
    }

    /**
     * Get y value
     *
     * @return y position
     */
    public int getColumn() {
        return column;
    }


    /**
     * Method setY sets the y of this Space object.
     *
     *
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
     * Assign builtTower to existing Tower
     *
     * @param tower tower input
     */
    public void setTower(Tower tower) {
        builtTower = tower;
    }

    /**
     * Get Worker stored in workerHere
     *
     * @return worker worker reference
     */
    public Worker getWorker() {
        return workerHere;
    }

    /**
     * Set worker into workerHere variable
     *
     * @param worker parameter
     */
    public void setWorker(Worker worker) {
        workerHere = worker;
    }

    /**
     * Return if Space has a Worker
     *
     * @return true if workerHere==null, false else
     */
    public boolean isEmpty() {
        return workerHere == null;
    }

    /**
     * get Tower stored in builtTower
     * @return tower
     */
    public Tower getTower() {
        return builtTower;
    }
}
