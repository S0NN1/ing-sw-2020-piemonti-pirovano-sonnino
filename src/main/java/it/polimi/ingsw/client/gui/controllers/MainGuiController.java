package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.shapes.Block;
import it.polimi.ingsw.client.gui.shapes.Dome;
import it.polimi.ingsw.client.gui.shapes.Worker;
import it.polimi.ingsw.constants.Constants;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * @author Alice Piemonti
 */
public class MainGuiController implements GUIController{

//TODO LAUNCH EACH METHOD AFTER THE SAME METHOD IN CLIENT BOARD

    private final HashMap<String, Color> colors;
    private GUI gui;
    private ClientBoard board;
    private ActionParser actionParser;
    @FXML
    GridPane grid;
    @FXML
    Label test;
    @FXML
    Button buttonMove, buttonBuild;
    @FXML
    AnchorPane mainAnchor, rightAnchor, centerAnchor;

    /**
     * constructor
     */
    public MainGuiController(){
        super();
        colors = new HashMap<>();
        colors.put(Constants.ANSI_RED, Color.RED);
        colors.put(Constants.ANSI_YELLOW, Color.YELLOW);
        colors.put(Constants.ANSI_BLUE, Color.DARKBLUE);
        colors.put(Constants.ANSI_GREEN, Color.GREEN);
        colors.put(Constants.ANSI_CYAN, Color.CYAN);
    }

    public void setActionParser(ActionParser actionParser) {
        this.actionParser = actionParser;
    }

    /*public void testDragAndDrop(){
        Button button = new Button();
        centerAnchor.getChildren().add(button);
        final double[] x = new double[1];
        final double[] y = new double[1];
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                 x[0] = button.getLayoutX() - mouseEvent.getSceneX();
                 y[0] = button.getLayoutY() - mouseEvent.getSceneY();
                button.setCursor(Cursor.MOVE);
            }
        });
        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setCursor(Cursor.HAND);
            }
        });
        button.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setLayoutX(mouseEvent.getSceneX() + x[0]);
                button.setLayoutY(mouseEvent.getSceneY() + y[0]);
            }
        });
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setCursor(Cursor.HAND);
            }
        });
    }
*/
    /**
     * add a triangle (worker) into gridPane at row/col
     * @param row of the grid
     * @param col of the grid
     * @param color of the worker
     */
    public void setWorker(int row, int col, String color) {
        grid.add(new Worker(colors.get(color), grid, rightAnchor),col,row);
    }

    /**
     * add another rectangle (block) into the others. All the controls on level must be done before.
     * if there is a triangle (worker), his layer bring in front.
     * @param row of the cell
     * @param col of the cell
     * @param level of the block
     */
    public void addBlock(int row, int col, int level){
        Worker worker = null;
        for(Node node: grid.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Worker) {
                worker = (Worker) node;
                grid.getChildren().remove(node);
                break;
            }
        }
        grid.add(new Block(level), col, row);
        if(worker != null) {
            grid.add(worker, col, row);
        }
    }

    /**
     * add a dome into gird at row/col position
     * @param row of the grid
     * @param col of the grid
     */
    public void addDome(int row, int col){
        grid.add(new Dome(), col, row);
    }

    /**
     * move the triangle (worker) into another cell of the gridPane and delete the precedent one
     * @param oldRow of worker's old position
     * @param oldCol of worker's old position
     * @param newRow of worker's actual position
     * @param newCol of worker's actual position
     */
    public void move(int oldRow, int oldCol, int newRow, int newCol) {
        String color = gui.getModelView().getBoard().getColor(newRow, newCol);
        for(Node node: grid.getChildren()) {
            if(GridPane.getRowIndex(node) == oldRow && GridPane.getColumnIndex(node) == oldCol && node instanceof Worker) {
                grid.getChildren().remove(node);
                break;
            }

        }
        grid.add(new Worker(colors.get(color), grid, rightAnchor), newCol, newRow);
    }

    /**
     * move the two workers into gridPane's cell (switching their fill)
     * @param oldRow1 of Apollo's old position (other worker's new position)
     * @param oldCol1 of Apollo's old position (other worker's new position)
     * @param oldRow2 of the other worker's old position (Apollo's new position)
     * @param oldCol2 of the other worker's old position (Apollo's new position)
     */
    public void apolloDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2) {
        for(Node node: grid.getChildren()) {
            if (GridPane.getRowIndex(node) == oldRow1 && GridPane.getColumnIndex(node) == oldCol1 && node instanceof Worker) {
                Worker worker1 = (Worker) node;
                worker1.setFill(colors.get(board.getGrid()[oldRow1][oldCol1].getColor()));
            } else if (GridPane.getRowIndex(node) == oldRow2 && GridPane.getColumnIndex(node) == oldCol2 && node instanceof Worker) {
                Worker worker2 = (Worker) node;
                worker2.setFill(colors.get(board.getGrid()[oldRow2][oldCol2].getColor()));
            }
        }
    }

    /**
     * move the two workers into gridPane's cells
     * @param oldRow1 of Minotaur's old position
     * @param oldCol1 of Minotaur's old position
     * @param oldRow2 of the other worker's old position (Minotaur's new position)
     * @param oldCol2 of the other worker's old position (Minotaur's new position)
     * @param newRow2 of the other worker's new position
     * @param newCol2 of the other worker's new position
     */
    public void minotaurDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2) {
        Node remove = null;
        for(Node node: grid.getChildren()) {
            if (GridPane.getRowIndex(node) == oldRow2 && GridPane.getColumnIndex(node) == oldCol2 && node instanceof Worker) {
                Worker worker1 = (Worker) node;
                worker1.setFill(colors.get(board.getGrid()[oldRow2][oldCol2].getColor()));
            } else if (GridPane.getRowIndex(node) == oldRow1 && GridPane.getColumnIndex(node) == oldCol1 && node instanceof Worker) {
                remove = node;
            }
        }
        if(remove != null) grid.getChildren().remove(remove);
        grid.add(new Worker(colors.get(board.getColor(newRow2, newCol2)), grid, rightAnchor), newCol2, newRow2);
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
        board = gui.getModelView().getBoard();
    }
}
