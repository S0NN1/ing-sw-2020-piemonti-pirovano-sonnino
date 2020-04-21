package it.polimi.ingsw.client;

/**
 * @author Alice Piemonti
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

    public void setColor(String color) {
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void addLevel(){
        this.level++;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }
}
