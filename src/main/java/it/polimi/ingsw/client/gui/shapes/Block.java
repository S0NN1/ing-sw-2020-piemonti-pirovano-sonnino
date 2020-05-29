package it.polimi.ingsw.client.gui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Block class is the GUI representation of the block in the GridPane.
 * @author Alice Piemonti
 * @see Rectangle
 */
public class Block extends Rectangle {

    /**
     * Constructor Block creates a new Block instance.
     *
     * @param level of type int - the level identifying the right block.
     */
    public Block(int level) {
        super();
        switch (level) {
            case 0 -> {

            }
            case 1 -> {
                setWidth(65);
                setHeight(65);
            }
            case 2 -> {
                setWidth(47);
                setHeight(47);
            }
            case 3 -> {
                setWidth(30);
                setHeight(30);
            }
            default -> throw new IllegalArgumentException();
        }
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
