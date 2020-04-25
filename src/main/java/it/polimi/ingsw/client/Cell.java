package it.polimi.ingsw.client;

/**
 * @author Alice Piemonti
 * This class is a simplified representation of the gameboard's space in model
 * It allows to have the status of the model on client side
 */
public class Cell {
    private String color;
    private int level;
    private boolean dome;

    public Cell(){
        color = null;
        level = 0;
        dome = false;
    }

    public String getColor() {
        return color;
    }

    /**
     * @param color is the representation of the worker
     */
    public void setColor(String color) {
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    /**
     * @param level is the representation of the tower
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * It's a representation of build method
     */
    public void addLevel(){
        this.level++;
    }

    public boolean isDome() {
        return dome;
    }

    /**
      * @param dome is the representation of the dome
     */
    public void setDome(boolean dome) {
        this.dome = dome;
    }
}
