package it.polimi.ingsw.client.gui.shapes;

import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Constants;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;


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
    }

    public void makeSelectable() {
        setOnMouseEntered(mouseEvent -> setCursor(Cursor.HAND));

        setOnMouseClicked(mouseEvent -> {
            controller.getGUI().getObservers().firePropertyChange("action", null, "SELECTWORKER "+ getWorkingNumber());
            controller.workerSelected();
        });
    }

    public void deselect() {
        setOnMouseEntered(null);
        setOnMouseClicked(null);
    }

    public void move(){
        int oldRow = row;
        int oldCol = col;
        GridPane grid = controller.getGrid();
        AnchorPane tempPane = new AnchorPane();
        AnchorPane.setTopAnchor(tempPane, 66.0);
        AnchorPane.setLeftAnchor(tempPane, 69.0);
        AnchorPane mainAnchor = controller.getMainAnchor();
        final double[] x = new double[1];
        final double[] y = new double[1];
        this.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            tempPane.getChildren().add(this);
            controller.getCenterAnchor().getChildren().add(tempPane);
            grid.getChildren().remove(this);
            x[0] = this.getLayoutX() - mouseEvent.getSceneX();
            y[0] = this.getLayoutY() - mouseEvent.getSceneY();
            this.setCursor(Cursor.MOVE);
        });
        this.setOnMouseReleased(mouseEvent -> {
                    this.setCursor(Cursor.DEFAULT);
                    int newRow = (int) (Constants.GRID_MAX_SIZE - ((grid.getHeight() - this.getLayoutY())/(grid.getHeight()/Constants.GRID_MAX_SIZE)));
                    int newCol = (int) (Constants.GRID_MAX_SIZE - ((grid.getWidth() - this.getLayoutX())/(grid.getWidth()/Constants.GRID_MAX_SIZE)));
                        grid.add(this, oldCol, oldRow);
                        this.setPosition(oldRow, oldCol);
                        controller.getGUI().getObservers().firePropertyChange("action", null, "MOVE "+ newRow + " " + newCol);

                    mainAnchor.getChildren().remove(tempPane);
                }
        );
        this.setOnMouseDragged(mouseEvent -> {
            this.setLayoutX(mouseEvent.getSceneX() + x[0]);
            this.setLayoutY(mouseEvent.getSceneY() + y[0]);
        });
        this.setOnMouseEntered(mouseEvent -> this.setCursor(Cursor.HAND));
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
