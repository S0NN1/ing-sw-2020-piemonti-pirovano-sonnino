package it.polimi.ingsw.client.gui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Block class is the GUI representation of the block in the GridPane.
 * @author Alice Piemonti
 * @see Rectangle
 */
public class Block extends Rectangle {
private final int level;

    /**
     * Constructor Block creates a new Block instance.
     *
     * @param width of type double - the grid's width.
     * @param height of type double - the grid's height.
     * @param level of type int - the level identifying the right block.
     */
    public Block(int level, double width, double height) {
        super();
        switch (level) {
            case 1 -> {
                setWidth(width*0.87);
                setHeight(height*0.87);
            }
            case 2 -> {
                setWidth(width*0.627);
                setHeight(height*0.627);
            }
            case 3 -> {
                setWidth(width*0.4);
                setHeight(height*0.4);
            }
            default -> throw new IllegalArgumentException();
        }
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        this.level=level;
    }

    /**
     * Method getLevel returns the level of this Block object.
     *
     *
     *
     * @return the level (type int) of this Block object.
     */
    public int getLevel() {
        return level;
    }
}
