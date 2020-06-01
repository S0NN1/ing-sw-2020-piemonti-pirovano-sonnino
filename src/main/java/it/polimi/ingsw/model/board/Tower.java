package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.OutOfBoundException;

/**
 * Class Tower describes a tower stored in a single space of the game board.
 *
 * @author Sonny
 * Created on 01/06/2020
 */
public class Tower {
    private int height;
    private boolean dome;

    /**
     * Constructor Tower creates a new Tower instance.
     */
    public Tower() {
        height = 0;
        dome = false;
    }

    /**
     * Method getHeight returns the height of this Tower object.
     *
     * @return the height (type int) of this Tower object.
     */
    public int getHeight() {
        return height;
    }


    /**
     * Method isCompleted returns if tower as reached dome level
     *
     * @return boolean true if getHeight() == 4, else boolean false
     */
    public boolean isCompleted() {
        return getHeight() == 4 || dome;
    }


    /**
     * Method addLevel adds a single block to the tower.
     * @throws OutOfBoundException when tower has reached its limit.
     */
    public void addLevel() throws OutOfBoundException {
        if ((getHeight() >= 0 && getHeight() < 4) && !isCompleted()) {
            height++;
        } else {
            throw new OutOfBoundException();
        }
    }

    /**
     * Method removeLevel removes a single block from the tower.
     * @throws OutOfBoundException when tower has reached its limit.
     */
    public void removeLevel() throws OutOfBoundException {
        if (getHeight() > 0) {
            if (dome) {
                dome = false;
            } else height--;
        } else if (getHeight() == 0 && dome) {
            dome = false;
        } else {
            throw new OutOfBoundException();
        }
    }


    /**
     * Method setDome sets the dome of this Tower object.
     *
     * @param dome the dome of this Tower object.
     *
     */
    public void setDome(boolean dome) {
        this.dome = dome;
    }
}
