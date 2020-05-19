package it.polimi.ingsw.client.gui.shapes;

import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

import javax.xml.stream.EventFilter;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class Worker extends Polygon {

    int row;
    int col;
    MainGuiController controller;

    public Worker(int row, int col, MainGuiController controller) {
        super(0.0, 10.0,
                        20.0, 10.0,
                        10.0, -10.0);
        setStroke(Color.BLACK);

        this.row = row;
        this.col = col;
        this.controller = controller;
        setFill(this.getColor());

        setOnMouseEntered( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCursor(Cursor.HAND);
            }
        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.getGUI().getObservers().firePropertyChange("action", null, "SELECT WORKER "+ getWorkingNumber());
                controller.workerSelected();
            }
        });
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Paint getColor() {
        return this.controller.getColors().get(controller.getGUI().getModelView().getBoard().getColor(row,col));
    }

    public int getWorkingNumber() {
        return this.controller.getGUI().getModelView().getBoard().getWorkerNum(row, col);
    }
}
