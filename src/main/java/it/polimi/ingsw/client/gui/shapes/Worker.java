package it.polimi.ingsw.client.gui.shapes;

import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Constants;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;

/**
 * Worker class is the gui representation of worker in the GridPane.
 * @author Alice Piemonti
 * @see Polygon
 */
public class Worker extends Ellipse {

    int row;
    int col;
    MainGuiController controller;

    /**
     * Constructor Worker creates a new Worker instance.
     *
     * @param row of type int - the row of the cell.
     * @param col of type int - the column of the cell.
     * @param controller of type MainGuiController - the MainGuiController reference.
     */
    public Worker(int row, int col, MainGuiController controller) {
        setStyle("-fx-background-color: blue");
        setStroke(Color.BLACK);
        this.row = row;
        this.col = col;
        this.controller = controller;
        setFill(getColor());
    }

    /**
     * Method makeSelectable makes the worker selectable.
     */
    public void makeSelectable() {
        setOnMouseEntered(mouseEvent -> setCursor(Cursor.HAND));

        setOnMouseClicked(mouseEvent -> {
            controller.getGUI().getListeners().firePropertyChange("action", null, "SELECTWORKER "
                    + getWorkingNumber());
            controller.workerSelected();
        });
    }

    /**
     * Method deselect deselects worker.
     */
    public void deselect() {
        setOnMouseEntered(null);
        setOnMouseClicked(null);
    }

    /**
     * Method move handles move action.
     */
    public void move(){
        int oldRow = row;
        int oldCol = col;
        GridPane grid = controller.getGrid();
        AnchorPane tempPane = new AnchorPane();
        AnchorPane.setTopAnchor(tempPane, 66.0);
        AnchorPane.setLeftAnchor(tempPane, 69.0);
        AnchorPane mainPane = controller.getMainPane();
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
                    int newRow = (int) (Constants.GRID_MAX_SIZE - ((grid.getHeight() - this.getLayoutY())/
                            (grid.getHeight()/Constants.GRID_MAX_SIZE)));
                    int newCol = (int) (Constants.GRID_MAX_SIZE - ((grid.getWidth() - this.getLayoutX())/
                            (grid.getWidth()/Constants.GRID_MAX_SIZE)));
                        grid.add(this, oldCol, oldRow);
                        this.setPosition(oldRow, oldCol);
                        controller.getGUI().getListeners().firePropertyChange("action", null, "MOVE "
                                + newRow + " " + newCol);

                    mainPane.getChildren().remove(tempPane);
                }
        );
        this.setOnMouseDragged(mouseEvent -> {
            this.setLayoutX(mouseEvent.getSceneX() + x[0]);
            this.setLayoutY(mouseEvent.getSceneY() + y[0]);
        });
        setOnMouseEntered(mouseEvent -> setCursor(Cursor.HAND));
    }

    /**
     * Method setPosition sets worker's cell.
     *
     * @param row of type int - the row of the cell.
     * @param col of type int - the column of the cell.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Method getColor returns the color of this Worker object.
     *
     *
     *
     * @return the color (type Paint) of this Worker object.
     */
    public Paint getColor() {
        return controller.getColors().get(controller.getGUI().getModelView().getBoard().getColor(row,col));
    }

    /**
     * Method getWorkingNumber returns the workingNumber of this Worker object.
     *
     *
     *
     * @return the workingNumber (type int) of this Worker object.
     */
    public int getWorkingNumber() {
        return controller.getGUI().getModelView().getBoard().getWorkerNum(row, col);
    }
}
