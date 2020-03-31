package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author Sonny
 * @version 1.0
 * <h1>Class describing spaces</h1>
 */
public class Space {
    private int x, y;
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
    public int getX() {
        return x;
    }

    /**
     * Set x value
     *
     * @param x position
     */
    public void setX(int x) throws InvalidInputException {
        if (x >= 0 && x < 25) {
            this.x = x;
        } else throw new InvalidInputException();
    }

    /**
     * Get y value
     *
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * Set y value
     *
     * @param y position
     */
    public void setY(int y) throws InvalidInputException {
        if (x >= 0 && x < 25) {
            this.y = y;
        } else throw new InvalidInputException();
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
}
