package it.polimi.ingsw.client.gui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Worker extends Polygon {

    public Worker(Color playerColor) {
        super( 0.0, 10.0,
                20.0, 10.0,
                10.0, -10.0);
        setStroke(Color.BLACK);
        setFill(playerColor);
    }
}
