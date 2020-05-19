package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.OutOfBoundException;

public class    Tower {
    private int height;
    private boolean dome;

    /**
     * Constructor
     */
    public Tower() {
        height = 0;
        dome = false;
    }

    /**
     * Get height value
     *
     * @return height value
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return if tower as reached dome level
     *
     * @return true if getHeight() == 4, else false
     */
    public boolean isCompleted() {
        return getHeight() == 4 || dome;
    }


    /**
     * Add one block
     * @throws OutOfBoundException exceeded range
     */
    public void addLevel() throws OutOfBoundException {
        if ((getHeight() >= 0 && getHeight() < 4) && !isCompleted()) {
            height++;
        } else {
            throw new OutOfBoundException();
        }
    }

    /**
     * Remove one block
     * @throws OutOfBoundException exceeded range
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
     * Let Atlas set dome ==true
     * @param dome used to identify dome block on an unfinished tower
     */
    public void setDome(boolean dome) {
        this.dome = dome;
    }
}
