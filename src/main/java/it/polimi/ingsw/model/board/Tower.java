package it.polimi.ingsw.model.board;

public class Tower {
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
     */
    public void addLevel() {
        try {
            if ((getHeight() >= 0 && getHeight() < 4) || !isCompleted()) {
                height++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove one block
     */
    public void removeLevel() {
        try {
            if (getHeight() > 0) {
                if (dome) {
                    dome = false;
                } else height--;
            } else if (getHeight() == 0 && dome) {
                dome = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
