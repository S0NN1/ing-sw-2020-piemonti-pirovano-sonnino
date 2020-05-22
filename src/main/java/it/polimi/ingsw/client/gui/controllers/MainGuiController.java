package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.shapes.Block;
import it.polimi.ingsw.client.gui.shapes.Dome;
import it.polimi.ingsw.client.gui.shapes.Worker;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alice Piemonti
 */
public class MainGuiController implements GUIController{

//TODO LAUNCH EACH METHOD AFTER THE SAME METHOD IN CLIENT BOARD

    private final HashMap<String, Color> colors;
    private GUI gui;
    private ClientBoard board;
    private Worker selectedWorker;

    @FXML
    GridPane grid;
    @FXML
    Label actionsLabel;
    @FXML
    Button buttonMove, buttonBuild;
    @FXML
    AnchorPane mainAnchor, rightAnchor, centerAnchor;

    /**
     * constructor
     */
    public MainGuiController() {
        super();
        colors = new HashMap<>();
        colors.put(Constants.ANSI_RED, Color.RED);
        colors.put(Constants.ANSI_YELLOW, Color.YELLOW);
        colors.put(Constants.ANSI_BLUE, Color.DARKBLUE);
        colors.put(Constants.ANSI_GREEN, Color.GREEN);
        colors.put(Constants.ANSI_CYAN, Color.CYAN);
    }

    public void showActions() {
        buttonMove.setVisible(true);
        buttonBuild.setVisible(true);
        getActionsLabel().setText("Select Action:");
        EventHandler<MouseEvent> buttonClicked = mouseEvent -> gui.getObservers().firePropertyChange("action", null, mouseEvent.getButton().name());
    }

    /*public void testDragAndDrop(){
        Button button = new Button("dragMe");
        grid.add(button, 4,0);
        final double[] x = new double[1];
        final double[] y = new double[1];
        button.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            AnchorPane tempPane = new AnchorPane(button);
            centerAnchor.getChildren().add(tempPane);
            AnchorPane.setTopAnchor(tempPane, 66.0);
            AnchorPane.setLeftAnchor(tempPane, 69.0);
            tempPane.setMaxSize(330.0,330.0);
            grid.getChildren().remove(button);
             x[0] = button.getLayoutX() - mouseEvent.getSceneX();
             y[0] = button.getLayoutY() - mouseEvent.getSceneY();
            button.setCursor(Cursor.MOVE);
        });
        button.setOnMouseReleased(mouseEvent -> {
            button.setCursor(Cursor.DEFAULT);
            int row = (int) (Constants.GRID_MAX_SIZE - ((grid.getHeight() - button.getLayoutY())/(grid.getHeight()/Constants.GRID_MAX_SIZE)));
            int col = (int) (Constants.GRID_MAX_SIZE - ((grid.getWidth() - button.getLayoutX())/(grid.getWidth()/Constants.GRID_MAX_SIZE)));
            grid.add(button, col, row);
                }
        );
        button.setOnMouseDragged(mouseEvent -> {
            button.setLayoutX(mouseEvent.getSceneX() + x[0]);
            button.setLayoutY(mouseEvent.getSceneY() + y[0]);
        });
        button.setOnMouseEntered(mouseEvent -> button.setCursor(Cursor.HAND));
        button.setOnMouseExited(mouseEvent -> button.setCursor(Cursor.DISAPPEAR));
    }*/

    /**
     * add a triangle (worker) into gridPane at row/col
     * @param row of the grid
     * @param col of the grid
     */
    public void setWorker(int row, int col) {
        grid.add(new Worker(row, col, this), col, row);
    }

    /**
     * make the two workers selectable
     */
    public void selectWorker() {
        String playerColor = getGUI().getModelView().getColor();
        Couple worker1 = board.getWorkerPosition(playerColor, 1);
        Couple worker2 = board.getWorkerPosition(playerColor, 2);
        getWorkerFromGrid(worker1.getRow(), worker1.getColumn()).makeSelectable();
        getWorkerFromGrid(worker2.getRow(), worker2.getColumn()).makeSelectable();
    }

    /**
     * choose if to build a block or a dome
     * @param row where to build
     * @param col where to build
     * @param dome if user want to build a dome
     */
    public void build(int row, int col, boolean dome) {
        if (!dome) {
            int height = board.getHeight(row, col);
            addBlock(row, col, height);
        }
        else {
            addDome(row, col);
        }
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
     * return the node of the grid which represents a worker at a specific row/col
     * @param row int
     * @param col int
     * @return node
     */
    public Worker getWorkerFromGrid(int row, int col) {
        Worker worker = null;
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Worker) {
                worker = (Worker) node;
                break;
            }
        }
        return worker;
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
        grid.getChildren().remove(getWorkerFromGrid(oldRow, oldCol));
        grid.add(new Worker(newRow, newCol, this), newCol, newRow);
    }

    /**
     * move the two workers into gridPane's cell (switching their fill)
     * @param oldRow1 of Apollo's old position (other worker's new position)
     * @param oldCol1 of Apollo's old position (other worker's new position)
     * @param oldRow2 of the other worker's old position (Apollo's new position)
     * @param oldCol2 of the other worker's old position (Apollo's new position)
     */
    public void apolloDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2) {
        Worker worker1 = (Worker) getWorkerFromGrid(oldRow1, oldCol1);
        worker1.setFill(colors.get(board.getColor(oldRow1,oldCol1)));
        Worker worker2 = (Worker) getWorkerFromGrid(oldRow2, oldCol2);
        worker2.setFill(colors.get(board.getColor(oldRow2, oldCol2)));
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
        Worker worker1 = (Worker) getWorkerFromGrid(oldRow2, oldCol2);
        worker1.setFill(colors.get(board.getColor(oldRow2,oldCol2)));
        grid.getChildren().remove(getWorkerFromGrid(oldRow1, oldCol1));
        Worker worker2 = new Worker(newRow2, newCol2, this);
        worker2.setFill(colors.get(board.getColor(newRow2, newCol2)));
        grid.add(worker2, newCol2, newRow2);
    }

    /**
     * highlight some cell of the grid in order to show to the user in which spaces he can move or build into
     */
    public void highlightCell(boolean build) {
        getActionsLabel().setText("Move your worker!");
        getActionsLabel().setVisible(true);
        List<Couple> spaces = getGUI().getModelView().getSelectSpaces();
        for (Couple element: spaces) {
              AnchorPane node = new AnchorPane();
              grid.add(node, element.getColumn(), element.getRow());
              node.setStyle("-fx-background-color: yellow");
              node.setOpacity(0.4);
              if (build) {
                  node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));
                  node.setOnMousePressed(mouseEvent -> node.setCursor(Cursor.CROSSHAIR));
                  int row = GridPane.getRowIndex(node);
                  int col = GridPane.getColumnIndex(node);
                  node.setOnMouseClicked(mouseEvent -> getGUI().getObservers().firePropertyChange("action", null, "BUILD "+ row + col));
              }
          }
        Couple position = getGUI().getModelView().getActiveWorkerPosition();
        getWorkerFromGrid(position.getRow(), position.getColumn()).move();
    }

    /**
     * stop highlighting cells
     */
    public void normalCell(){
        for (int i = 0; i < grid.getChildren().size(); i++) {
            Node node = grid.getChildren().get(i);
            if (node instanceof AnchorPane) {
                grid.getChildren().remove(node);
                i--;
            }
        }
    }

    public void workerSelected() {
        String playerColor = getGUI().getModelView().getColor();
        Couple worker1 = board.getWorkerPosition(playerColor, 1);
        Couple worker2 = board.getWorkerPosition(playerColor, 2);
        getWorkerFromGrid(worker1.getRow(), worker1.getColumn()).setOnMouseEntered(null);
        getWorkerFromGrid(worker2.getRow(), worker2.getColumn()).setOnMouseEntered(null);
        //if not Prometheus
        getActionsLabel().setText("Move your worker!");
        //if Prometheus
        //showActions()
        getActionsLabel().setVisible(true);
    }

    public Button getButtonMove() {
        return buttonMove;
    }

    public Button getButtonBuild() {
        return buttonBuild;
    }

    public Label getActionsLabel() {
        return actionsLabel;
    }

    public GridPane getGrid() {
        return grid;
    }

    public AnchorPane getCenterAnchor() {
        return centerAnchor;
    }

    public GUI getGUI() {
        return gui;
    }

    public AnchorPane getMainAnchor() {
        return mainAnchor;
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
        board = gui.getModelView().getBoard();
    }

    public HashMap<String, Color> getColors() {
        return colors;
    }
}
