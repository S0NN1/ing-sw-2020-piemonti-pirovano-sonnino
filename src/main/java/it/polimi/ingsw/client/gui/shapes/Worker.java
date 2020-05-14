package it.polimi.ingsw.client.gui.shapes;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Worker extends Polygon {

    private  Double x;
    private  Double y;

    public Worker(Color playerColor, GridPane gridpane, AnchorPane anchorPane) {
        super(0.0, 10.0,
                        20.0, 10.0,
                        10.0, -10.0);
        setStroke(Color.BLACK);
        setFill(playerColor);

        setOnMouseEntered( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCursor(Cursor.HAND);
            }
        });
    }
}
