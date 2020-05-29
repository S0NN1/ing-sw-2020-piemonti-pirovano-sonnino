package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.shapes.Block;
import it.polimi.ingsw.client.gui.shapes.Dome;
import it.polimi.ingsw.client.gui.shapes.Worker;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.Action;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * MainGuiController class handles mainScene.fxml by executing commands from the GUI.
 *
 * @author Alice Piemonti
 * @see GUIController
 */
public class MainGuiController implements GUIController {

  // TODO LAUNCH EACH METHOD AFTER THE SAME METHOD IN CLIENT BOARD

  private final HashMap<String, Color> colors;
  private final HashMap<Integer, Label> playerMapLabel = new HashMap<>();
  private final HashMap<Integer, Rectangle> playerMapRect = new HashMap<>();
  private GUI gui;
  private ClientBoard board;
  private boolean godPowerActive;
  @FXML private GridPane grid;
  @FXML private Label actionsLabel;
  @FXML private Button buttonMove;
  @FXML private Button buttonBuild;
  @FXML private Button buttonEnd;
  @FXML private Button buttonCustom;
  @FXML private AnchorPane mainAnchor;
  @FXML private AnchorPane centerAnchor;
  @FXML private Label player1;
  @FXML private Label player2;
  @FXML private Label player3;
  @FXML private Rectangle rect1;
  @FXML private Rectangle rect2;
  @FXML private Rectangle rect3;

  /** Constructor MainGuiController creates a new MainGuiController instance. */
  public MainGuiController() {
    super();
    colors = new HashMap<>();
    colors.put("RED", Color.RED);
    colors.put(Constants.ANSI_YELLOW, Color.YELLOW);
    colors.put("BLUE", Color.DARKBLUE);
    colors.put("GREEN", Color.GREEN);
    colors.put("CYAN", Color.CYAN);
  }

  /** Method init sets players' nicknames, colors and customAction visibility. */
  public void init() {
    playerHashmapFill();
    Collection players = gui.getModelView().getPlayerMapColor().keySet();
    Iterator iterator = players.iterator();
    for (int i = 0; i < gui.getModelView().getPlayerMapColor().size(); i++) {
      String nickname = iterator.next().toString();
      playerMapLabel.get(i).setText(nickname);
      playerMapRect
          .get(i)
          .setFill(colors.get(gui.getModelView().getPlayerMapColor().get(nickname).toUpperCase()));
      playerMapLabel.get(i).setVisible(true);
      setMousePlayerAction(i);
    }
    setVisibleCustomAction();
  }

  /** Method setVisibleCustomAction sets customAction visibility based on god power. */
  private void setVisibleCustomAction() {
    if (!Constants.getGodMapCustomAction().containsKey(gui.getModelView().getGod().toUpperCase())) {
      buttonCustom.setVisible(false);
    } else {
      buttonCustom.setText(
          Constants.getGodMapCustomAction().get(gui.getModelView().getGod().toUpperCase()));
      buttonCustom.setOnAction(
          event -> {
            gui.getListeners()
                .firePropertyChange(
                    "action",
                    null,
                    Constants.getGodMapCustomAction()
                        .get(gui.getModelView().getGod().toUpperCase()));
          });
    }
  }

  /**
   * Method setMousePlayerAction sets the mousePlayerAction of this MainGuiController object.
   *
   * @param i the mousePlayerAction of this MainGuiController object.
   */
  private void setMousePlayerAction(int i) {
    playerMapLabel
        .get(i)
        .setOnMouseEntered(mouseEvent -> playerMapLabel.get(i).setCursor(Cursor.HAND));
    playerMapLabel
        .get(i)
        .setOnMouseClicked(
            mouseEvent -> {
              Alert description = new Alert(Alert.AlertType.INFORMATION);
              description.setTitle(
                  gui.getModelView().getPlayerMapGod().get(playerMapLabel.get(i).getText()));
              description.setHeaderText("Description");
              description.setContentText(
                  Card.parseInput(
                          gui.getModelView().getPlayerMapGod().get(playerMapLabel.get(i).getText()))
                      .godsDescription());
              description.show();
            });
  }

  /** Method playerHashmapFill builds Map containing player's nicknames and colors. */
  private void playerHashmapFill() {
    playerMapLabel.put(0, player1);
    playerMapLabel.put(1, player2);
    playerMapLabel.put(2, player3);
    playerMapRect.put(0, rect1);
    playerMapRect.put(1, rect2);
    playerMapRect.put(2, rect3);
  }

  /**
   * Method showActions sets buttons styles based on the turnPhase.
   *
   * @param checkers of type boolean[] - the checkers identify respectively.
   */
  public void showActions(boolean[] checkers) {
    buttonMove.getStyleClass().clear();
    buttonBuild.getStyleClass().clear();
    buttonEnd.getStyleClass().clear();
    buttonCustom.getStyleClass().clear();
    buttonMove.getStyleClass().add(checkers[0] ? "rightBoard" : "grayedOut");
    buttonBuild.getStyleClass().add(checkers[1] ? "rightBoard" : "grayedOut");
    buttonEnd.getStyleClass().add(checkers[2] ? "rightBoard" : "grayedOut");
    if (checkers.length == 4 && checkers[3]) {
      buttonCustom.getStyleClass().add("rightBoard");
    } else {
      buttonCustom.getStyleClass().add("grayedOut");
    }
    getActionsLabel().setText("Select Action:");
    buttonMove.setOnAction(event -> gui.getListeners().firePropertyChange("action", null, "MOVE"));
    buttonBuild.setOnAction(
        event -> gui.getListeners().firePropertyChange("action", null, "BUILD"));
    buttonEnd.setOnAction(event -> gui.getListeners().firePropertyChange("action", null, "END"));
  }

  /**
   * Method setWorker adds a triangle (worker) into gridPane at row/col.
   *
   * @param row of type int - the row of the cell.
   * @param col of type int - the column of the cell.
   */
  public void setWorker(int row, int col) {
    grid.add(new Worker(row, col, this), col, row);
  }

  /** Method selectWorker makes the two workers selectable. */
  public void selectWorker() {
    actionsLabel.setText("Select your worker.");
    actionsLabel.setVisible(true);
    String playerColor = getGUI().getModelView().getColor();
    Couple worker1 = board.getWorkerPosition(playerColor, 1);
    Couple worker2 = board.getWorkerPosition(playerColor, 2);
    getWorkerFromGrid(worker1.getRow(), worker1.getColumn()).makeSelectable();
    getWorkerFromGrid(worker2.getRow(), worker2.getColumn()).makeSelectable();
  }

  /**
   * Method build chooses if to build a block or a dome.
   *
   * @param row of type int - the row of the cell.
   * @param col of type int - the column of the cell.
   * @param dome of type boolean - true if user wants to build a dome, false otherwise.
   */
  public void build(int row, int col, boolean dome, Action action) {
    if(!action.equals(Action.BUILD)){
      int height = board.getHeight(row, col);
      removeBlock(row, col, height);
    }
    else if (!dome) {
      int height = board.getHeight(row, col);
      addBlock(row, col, height);
    } else {
      addDome(row, col);
    }
  }

  /**
   * Method addBlock adds an inner rectangle into the others one, if worker is on the selected cell
   * is brought to the front.
   *
   * @param row of type int - the row.
   * @param col of type int - the column.
   * @param level of type int - the level of the block
   */
  public void addBlock(int row, int col, int level) {
    Worker worker = null;
    for (Node node : grid.getChildren()) {
      if (GridPane.getRowIndex(node) == row
          && GridPane.getColumnIndex(node) == col
          && node instanceof Worker) {
        worker = (Worker) node;
        grid.getChildren().remove(node);
        break;
      }
    }
    grid.add(new Block(level), col, row);
    if (worker != null) {
      grid.add(worker, col, row);
    }
  }

  public void removeBlock(int row, int col, int level) {
    for (Node node : grid.getChildren()) {
      if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
        node.getParent().getParent().getParent().getParent().getParent()
        return;
      }
    }
}
  /**
   * Method getWorkerFromGrid returns the node of the grid which represents a worker at a specific
   * row/col.
   *
   * @param row of type int - the row of the cell.
   * @param col of type int - the column of the cell.
   * @return node of type Worker - the worker's node.
   */
  public Worker getWorkerFromGrid(int row, int col) {
    Worker worker = null;
    for (Node node : grid.getChildren()) {
      if (GridPane.getRowIndex(node) == row
          && GridPane.getColumnIndex(node) == col
          && node instanceof Worker) {
        worker = (Worker) node;
        break;
      }
    }
    return worker;
  }

  /**
   * Method addDome adds a dome into grid at row/col position.
   *
   * @param row of type int - the row of the cell.
   * @param col of type int - the column of the cell.
   */
  public void addDome(int row, int col) {
    grid.add(new Dome(), col, row);
  }

  /**
   * Method move moves the triangle (worker) into another cell of the gridPane and deletes the
   * precedent one.
   *
   * @param oldRow of type int - the worker's old row.
   * @param oldCol of type int - the worker's old column.
   * @param newRow of type int - the worker's new row.
   * @param newCol of type int - the worker's new column.
   */
  public void move(int oldRow, int oldCol, int newRow, int newCol) {
    grid.getChildren().remove(getWorkerFromGrid(oldRow, oldCol));
    grid.add(new Worker(newRow, newCol, this), newCol, newRow);
  }

  /**
   * Method apolloDoubleMove moves the two workers into gridPane's cell (switching their fill).
   *
   * @param oldRow1 of type int - Apollo's old row (other worker's new row).
   * @param oldCol1 of type int - Apollo's old column (other worker's new column).
   * @param oldRow2 of type int - the other worker's old row (Apollo's new row).
   * @param oldCol2 of type int - the other worker's old column (Apollo's new column).
   */
  public void apolloDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2) {
    Worker worker1 = getWorkerFromGrid(oldRow1, oldCol1);
    worker1.deselect();
    worker1.setFill(colors.get(board.getColor(oldRow1, oldCol1)));
    Worker worker2 = getWorkerFromGrid(oldRow2, oldCol2);
    worker2.setFill(colors.get(board.getColor(oldRow2, oldCol2)));
  }

  /**
   * Method minotaurDoubleMove moves the two workers into gridPane's cells.
   *
   * @param oldRow1 of type int - Minotaur's old row (other worker's new row).
   * @param oldCol1 of type int - Minotaur's old column (other worker's new column).
   * @param oldRow2 of type int - the other worker's old row (Minotaur's new row).
   * @param oldCol2 of type int - the other worker's old column (Minotaur's new column).
   * @param newRow2 of type int - the other worker's new row.
   * @param newCol2 of type int - the other worker's new column.
   */
  public void minotaurDoubleMove(
      int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2) {
    Worker worker1 = getWorkerFromGrid(oldRow2, oldCol2);
    worker1.setFill(colors.get(board.getColor(oldRow2, oldCol2)));
    grid.getChildren().remove(getWorkerFromGrid(oldRow1, oldCol1));
    Worker worker2 = new Worker(newRow2, newCol2, this);
    worker2.setFill(colors.get(board.getColor(newRow2, newCol2)));
    grid.add(worker2, newCol2, newRow2);
  }

  /**
   * Method highlightCell highlights some cell of the grid in order to show to the user in which
   * spaces he can move or build onto.
   *
   * @param build of type boolean true if highlighting a selectBuild, false if highlighting a
   *     selectMove.
   */
  public void highlightCell(boolean build) {
    getActionsLabel().setText("Move your worker!");
    getActionsLabel().setVisible(true);
    List<Couple> spaces = getGUI().getModelView().getSelectSpaces();
    for (Couple element : spaces) {
      AnchorPane node = new AnchorPane();
      grid.add(node, element.getColumn(), element.getRow());
      node.setStyle("-fx-background-color: yellow");
      node.setOpacity(0.4);
     int row = GridPane.getRowIndex(node);
     int col = GridPane.getColumnIndex(node);
      if (gui.getModelView().isGodPowerActive()) {
        node.setOnMouseClicked(
            mouseEvent -> {
              gui.getModelView().setGodPowerActive(false);
              getGUI()
                  .getListeners()
                  .firePropertyChange(
                      "action",
                      null,
                      Constants.getGodMapCustomAction()
                              .get(gui.getModelView().getGod().toUpperCase())
                          + " "
                          + row
                          + " "
                          + col);
            });
      } else if (build) {
        node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));
        node.setOnMousePressed(mouseEvent -> node.setCursor(Cursor.CROSSHAIR));
       // int row = GridPane.getRowIndex(node);
        //int col = GridPane.getColumnIndex(node);
        node.setOnMouseClicked(
            mouseEvent ->
                getGUI()
                    .getListeners()
                    .firePropertyChange("action", null, "BUILD " + row + " " + col));
      } else {
        node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));
        node.setOnMousePressed(mouseEvent -> node.setCursor(Cursor.CROSSHAIR));
       // int row = GridPane.getRowIndex(node);
        //int col = GridPane.getColumnIndex(node);
        node.setOnMouseClicked(
            mouseEvent ->
                getGUI()
                    .getListeners()
                    .firePropertyChange("action", null, "MOVE " + row + " " + col));
      }
    }
    // Couple position = getGUI().getModelView().getActiveWorkerPosition();
    // getWorkerFromGrid(position.getRow(), position.getColumn()).move();
  }

  /** Method normalCell rollbacks to normal cells from highlighted ones. */
  public void normalCell() {
    for (int i = 0; i < grid.getChildren().size(); i++) {
      Node node = grid.getChildren().get(i);
      if (node instanceof AnchorPane) {
        grid.getChildren().remove(node);
        i--;
      }
    }
  }

  /** Method endTurn removes button and print turn ended. */
  public void endTurn() {
    showActions(new boolean[] {false, false, false});
    actionsLabel.setText("Turn ended! :)");
  }

  /** Method workerSelected sets other workers as not selectable. */
  public void workerSelected() {
    String playerColor = getGUI().getModelView().getColor();
    Couple worker1 = board.getWorkerPosition(playerColor, 1);
    Couple worker2 = board.getWorkerPosition(playerColor, 2);
    getWorkerFromGrid(worker1.getRow(), worker1.getColumn()).setOnMouseEntered(null);
    getWorkerFromGrid(worker1.getRow(), worker1.getColumn()).setOnMouseClicked(null);
    getWorkerFromGrid(worker2.getRow(), worker2.getColumn()).setOnMouseEntered(null);
    getWorkerFromGrid(worker2.getRow(), worker2.getColumn()).setOnMouseClicked(null);
    // if not Prometheus
    getActionsLabel().setText("Move your worker!");
    // if Prometheus
    // showActions()
    getActionsLabel().setVisible(true);
  }
  /**
   * Method getActionsLabel returns the actionsLabel of this MainGuiController object.
   *
   * @return the actionsLabel (type Label) of this MainGuiController object.
   */
  public Label getActionsLabel() {
    return actionsLabel;
  }

  /**
   * Method getGrid returns the grid of this MainGuiController object.
   *
   * @return the grid (type GridPane) of this MainGuiController object.
   */
  public GridPane getGrid() {
    return grid;
  }

  /**
   * Method getCenterAnchor returns the centerAnchor of this MainGuiController object.
   *
   * @return the centerAnchor (type AnchorPane) of this MainGuiController object.
   */
  public AnchorPane getCenterAnchor() {
    return centerAnchor;
  }

  /**
   * Method getGUI returns the GUI of this MainGuiController object.
   *
   * @return the GUI (type GUI) of this MainGuiController object.
   */
  public GUI getGUI() {
    return gui;
  }

  /**
   * Method getMainAnchor returns the mainAnchor of this MainGuiController object.
   *
   * @return the mainAnchor (type AnchorPane) of this MainGuiController object.
   */
  public AnchorPane getMainAnchor() {
    return mainAnchor;
  }
  /** @see GUIController#setGui(GUI) */
  @Override
  public void setGui(GUI gui) {
    this.gui = gui;
    board = gui.getModelView().getBoard();
  }

  /**
   * Method getColors returns the colors of this MainGuiController object.
   *
   * @return the colors (type HashMap<String, Color>) of this MainGuiController object.
   */
  public HashMap<String, Color> getColors() {
    return colors;
  }



  /**
   * Method setGodPowerActive sets the godPowerActive of this MainGuiController object.
   *
   * @param godPowerActive the godPowerActive of this MainGuiController object.
   */
  private  void setGodPowerActive(boolean godPowerActive) {
    this.godPowerActive = godPowerActive;
  }
}
