package it.polimi.ingsw.client.gui.shapes;

import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javax.xml.stream.EventFilter;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class Worker extends Polygon {

    int workerNumber;

    public Worker(Color playerColor, int workerNumber, MainGuiController controller) {
        super(0.0, 10.0,
                        20.0, 10.0,
                        10.0, -10.0);
        setStroke(Color.BLACK);
        this.workerNumber = workerNumber;
        setFill(playerColor);

        setOnMouseEntered( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCursor(Cursor.HAND);
            }
        });
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.getGUI().getObservers().firePropertyChange("action", null, "SELECT WORKER ");
                controller.getButtonMove().setVisible(true);
                controller.getButtonBuild().setVisible(true);
                controller.getActionsLabel().setText("Select Action:");
                controller.getActionsLabel().setVisible(true);
            }
        });
    }

    public void setWorkerNumber(int workerNumber) {
        this.workerNumber = workerNumber;
    }
}
