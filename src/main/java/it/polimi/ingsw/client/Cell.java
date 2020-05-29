package it.polimi.ingsw.client;

/**
 * Cell class is a simplified representation of the gameBoard's space in model.
 * @author Alice Piemonti
 */
public class Cell {
    private String color;
    private int level;
    private boolean dome;
    private int workerNum;

    /**
     * Constructor Cell creates a new Cell instance.
     */
    public Cell(){
        workerNum = 0;
        color = null;
        level = 0;
        dome = false;
    }

    /**
     * Method getWorkerNum returns the workerNum of this Cell object.
     *
     *
     *
     * @return the workerNum (type int) of this Cell object.
     */
    public int getWorkerNum() {
        return workerNum;
    }

    /**
     * Method setWorkerNum sets the workerNum of this Cell object.
     *
     * @param workerNum of type int - the workerNum of this Cell object.
     * @throws IllegalArgumentException when if workerNum <0 || workerNum>2.
     */
    public void setWorkerNum(int workerNum) throws IllegalArgumentException {   //0 -> no worker; 1 -> worker n1; 2 -> worker n2
        if(workerNum < 0 || workerNum > 2) throw new IllegalArgumentException();
        this.workerNum = workerNum;
    }

    /**
     * Method getColor returns the color of this Cell object.
     *
     * @return the color (type String) of this Cell object.
     */
    public String getColor() {
        return color;
    }

    /**Method setColor sets the color of this Cell object.
     *
     * @param color of type String - the representation of the worker
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Method getLevel returns the level of this Cell object.
     *
     * @return the level (type int) of this Cell object.
     */
    public int getLevel() {
        return level;
    }


    /**
     * Method setLevel sets the level of this Cell object.
     *
     * @param level the level of this Cell object.
     */
    public void setLevel(int level) {
        this.level = level;
    }


    /**
     * Method addLevel adds a level.
     */
    public void addLevel(){
        this.level++;
    }

    /**
     * Method isDome returns the dome of this Cell object.
     *
     * @return the dome (type boolean) of this Cell object.
     */
    public boolean isDome() {
        return dome;
    }


    /**
     * Method setDome sets the dome of this Cell object.
     *
     * @param dome of type boolean - the dome of this Cell object.
     */
    public void setDome(boolean dome) {
        this.dome = dome;
    }

    /**
     * Method removeLevel removes one block from the selected cell.
     */
    public void removeLevel() { this.level--; }
}
