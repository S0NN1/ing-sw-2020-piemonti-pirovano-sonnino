package it.polimi.ingsw.client.gui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Dome class is the gui representation of block dome.
 * @author Alice Piemonti
 * @see Circle
 */
public class Dome extends Circle {
    /**
     * Constructor Dome creates a new Dome instance.
     */
    public Dome(){
        super(13.0, Color.BLUE);
        setStroke(Color.BLACK);
    }
}
