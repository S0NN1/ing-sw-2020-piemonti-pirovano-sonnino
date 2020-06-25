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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MainGuiController class handles mainScene.fxml by executing commands from the GUI.
 *
 * @author Alice Piemonti
 * @see GUIController
 */
public class MainGuiController implements GUIController {

  public static final String RIGHT_BOARD = "rightBoard";
  public static final String ACTION = "action";
  public static final String GRAYED_OUT = "grayedOut";
  private final HashMap<String, Color> colors;
  private final HashMap<Integer, Label> playerMapLabel = new HashMap<>();
  private final HashMap<Integer, ImageView> playerMapRect = new HashMap<>();
  private final HashMap<String, ImageView> playerMapStar = new HashMap<>();
  private final HashMap<Integer, ImageView> indexMapStar = new HashMap<>();
  private final HashMap<Color, Image> colorMapImage = new HashMap<>();
  private GUI gui;
  private ClientBoard board;
  @FXML private GridPane grid;
  @FXML private Label actionsLabel;
  @FXML private Button buttonMove;
  @FXML private Button buttonBuild;
  @FXML private Button buttonEnd;
  @FXML private Button buttonCustom;
  @FXML private AnchorPane mainPane;
  @FXML private AnchorPane centerAnchor;
  @FXML private Label player1;
  @FXML private Label player2;
  @FXML private Label player3;
  @FXML private ImageView rect1;
  @FXML private ImageView rect2;
  @FXML private ImageView rect3;
  @FXML private ImageView star1;
  @FXML private ImageView star2;
  @FXML private ImageView star3;

  /** Constructor MainGuiController creates a new MainGuiController instance. */
  public MainGuiController() {
    super();
    colors = new HashMap<>();
    colors.put("RED", Color.RED);
    colors.put(Constants.ANSI_YELLOW, Color.YELLOW);
    colors.put("BLUE", Color.DARKBLUE);
    colors.put("GREEN", Color.GREEN);
    colors.put("CYAN", Color.CYAN);
    colorMapImage.put(Color.RED, new Image(getClass().getResourceAsStream("/graphics/red.png")));
    colorMapImage.put( Color.GREEN, new Image(getClass().getResourceAsStream("/graphics/green.png")));
    colorMapImage.put(Color.DARKBLUE, new Image(getClass().getResourceAsStream("/graphics/blue.png"))); }

  /** Method init sets players' nicknames, colors and customAction visibility. */
  public void init() {
    playerHashmapFill();
    Collection<String> players = gui.getModelView().getPlayerMapColor().keySet();
    Iterator<String> iterator = players.iterator();
    for (int i = 0; i < gui.getModelView().getPlayerMapColor().size(); i++) {
      String nickname = iterator.next();
      playerMapLabel.get(i).setText(nickname);
      playerMapStar.put(playerMapLabel.get(i).getText(), indexMapStar.get(i));
      playerMapRect
          .get(i)
          .setImage(colorMapImage.get(colors.get(gui.getModelView().getPlayerMapColor().get(nickname).toUpperCase())));
      playerMapRect.get(i).setVisible(true);
      playerMapLabel.get(i).setVisible(true);
      playerMapStar.get(playerMapLabel.get(i).getText()).setVisible(true);
      setMousePlayerAction(i);
    }
    setVisibleCustomAction();
  }

  /** Method setVisibleCustomAction sets customAction visibility based on god power. */
  private void setVisibleCustomAction() {
    if (!Constants.getGodMapCustomAction().containsKey(gui.getModelView().getGod().toUpperCase())) {
      buttonCustom.setVisible(false);
    } else{
      new ResizeHandler((Pane) buttonCustom.getScene().lookup("#mainPane"));
      buttonCustom.setText(
          Constants.getGodMapCustomAction().get(gui.getModelView().getGod().toUpperCase()));
      buttonCustom.setOnAction(
          event ->
              gui.getListeners()
                  .firePropertyChange(
                      ACTION,
                      null,
                      Constants.getGodMapCustomAction()
                          .get(gui.getModelView().getGod().toUpperCase())));
    }
  }

  /**
   * Method updateTurnStatus checks the current player from the ModelView and set the correct star ImageView.
   */
  public void updateTurnStatus() {
    for(int i=0; i<gui.getModelView().getPlayerMapColor().size(); i++) {
        playerMapStar.get(playerMapLabel.get(i).getText()).setImage(new Image(getClass().
                getResourceAsStream("/graphics/icons/clp_star_empty.png")));
    }
      String currentPlayer = gui.getModelView().getCurrentPlayer();
      playerMapStar.get(currentPlayer).setImage(new Image(getClass().getResourceAsStream(
              "/graphics/icons/clp_star_full.png")));
  }

  /**
   * Method setMousePlayerAction sets the mousePlayerAction of this MainGuiController object.
   *
   * @param i of type int - the mousePlayerAction of this MainGuiController object.
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
              description.setTitle("Description");
              description.setHeaderText(
                  gui.getModelView().getPlayerMapGod().get(playerMapLabel.get(i).getText()));
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
    indexMapStar.put(0, star1);
    indexMapStar.put(1, star2);
    indexMapStar.put(2, star3);
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
    buttonMove.getStyleClass().add(checkers[0] ? RIGHT_BOARD : GRAYED_OUT);
    buttonBuild.getStyleClass().add(checkers[1] ? RIGHT_BOARD : GRAYED_OUT);
    buttonEnd.getStyleClass().add(checkers[2] ? RIGHT_BOARD : GRAYED_OUT);
    if (checkers.length == 4 && checkers[3]) {
      buttonCustom.getStyleClass().add(RIGHT_BOARD);
    } else {
      buttonCustom.getStyleClass().add(GRAYED_OUT);
    }
    getActionsLabel().setText("Select Action:");
    buttonMove.setOnAction(event -> gui.getListeners().firePropertyChange(ACTION, null, "MOVE"));
    buttonBuild.setOnAction(event -> gui.getListeners().firePropertyChange(ACTION, null, "BUILD"));
    buttonEnd.setOnAction(event -> gui.getListeners().firePropertyChange(ACTION, null, "END"));
  }

  /**
   * Method setWorker adds a triangle (worker) into gridPane at row/col.
   *
   * @param row of type int - the row of the cell.
   * @param col of type int - the column of the cell.
   */
  public void setWorker(int row, int col) {
    Worker worker = new Worker(row, col, this);
    grid.add(worker, col, row);
    worker.radiusXProperty().bind(grid.widthProperty().divide(30));
    worker.radiusYProperty().bind(grid.heightProperty().divide(30));  }

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
   * @param action of type Action - the type of action.
   */
  public void build(int row, int col, boolean dome, Action action) {
    if (!action.equals(Action.BUILD)) {
      int height = board.getHeight(row, col);
      removeBlock(row, col, height);
    } else if (!dome) {
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
    Block block = new Block(level, grid.getWidth()/5, grid.getWidth()/5);
    grid.add(block, col, row);
    double sqrt = Math.sqrt(Math.pow(level, 2) * 1.2) + 5;
    block.widthProperty().bind(grid.widthProperty().divide(sqrt));
    block.heightProperty().bind(grid.heightProperty().divide(sqrt));
   // block.setScaleX(grid.getScene().lookup("#gridPane").getScaleX());
    //block.setScaleY(grid.getScene().lookup("#gridPane").getScaleY());
    if (worker != null) {
      grid.add(worker, col, row);
      worker.radiusXProperty().bind(grid.widthProperty().divide(30));
      worker.radiusYProperty().bind(grid.heightProperty().divide(30));
    }
  }

  /**
   * Method removeBlock removes a block.
   *
   * @param row of type int - the row.
   * @param col of type int - the column.
   * @param level of type int - the level of the block
   */
  public void removeBlock(int row, int col, int level) {
    for (Node node : grid.getChildren()) {
      if (GridPane.getRowIndex(node) == row
          && GridPane.getColumnIndex(node) == col
          && node instanceof Block
          && level + 1 == ((Block) node).getLevel()) {
        grid.getChildren().remove(node);
        return;
      }
    }
  }
  /**
   * Method getWorkerFromGrid returns the node of the grid which represents a worker at a specific row/col.
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
    Dome dome = new Dome(grid.getWidth()/5, grid.getHeight());
    grid.add(dome, col, row);
    dome.radiusXProperty().bind(grid.widthProperty().divide(20));
    dome.radiusYProperty().bind(grid.heightProperty().divide(20));
  }

  /**
   * Method move moves the triangle (worker) into another cell of the gridPane and deletes the precedent one.
   *
   * @param oldRow of type int - the worker's old row.
   * @param oldCol of type int - the worker's old column.
   * @param newRow of type int - the worker's new row.
   * @param newCol of type int - the worker's new column.
   */
  public void move(int oldRow, int oldCol, int newRow, int newCol) {
    grid.getChildren().remove(getWorkerFromGrid(oldRow, oldCol));
    Worker worker =new Worker(newRow, newCol, this);
    grid.add(worker, newCol, newRow);
    worker.radiusXProperty().bind(grid.widthProperty().divide(30));
    worker.radiusYProperty().bind(grid.heightProperty().divide(30));
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
    worker1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/graphics/icons/hammer_"+
            board.getColor(oldRow1, oldCol1).toLowerCase() + ".png"))));
    Worker worker2 = getWorkerFromGrid(oldRow2, oldCol2);
    worker2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/graphics/icons/hammer_"+
            board.getColor(oldRow2, oldCol2).toLowerCase() + ".png"))));
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
    worker1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/graphics/icons/hammer_"+
            board.getColor(oldRow2, oldCol2).toLowerCase() + ".png"))));
    grid.getChildren().remove(getWorkerFromGrid(oldRow1, oldCol1));
    Worker worker2 = new Worker(newRow2, newCol2, this);
    worker2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/graphics/icons/hammer_"+
            board.getColor(newRow2, newCol2).toLowerCase() + ".png"))));
    grid.add(worker2, newCol2, newRow2);
    worker2.radiusXProperty().bind(grid.widthProperty().divide(30));
    worker2.radiusYProperty().bind(grid.heightProperty().divide(30));
  }

  /**
   * Method highlightCell highlights some cell of the grid in order to show to the user in which spaces he can move or
   * build onto.
   *
   * @param build of type boolean true if highlighting a selectBuild, false if highlighting a selectMove.
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
                      ACTION,
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
        node.setOnMouseClicked(
            mouseEvent ->
                getGUI()
                    .getListeners()
                    .firePropertyChange(ACTION, null, "BUILD " + row + " " + col));
      } else {
        node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));
        node.setOnMousePressed(mouseEvent -> node.setCursor(Cursor.CROSSHAIR));
        node.setOnMouseClicked(
            mouseEvent ->
                getGUI()
                    .getListeners()
                    .firePropertyChange(ACTION, null, "MOVE " + row + " " + col));
      }
    }
  }

  /** Method normalCell rollbacks to normal cells from highlighted ones. */
  public void normalCells() {
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
   * Method getMainPane returns the mainPane of this MainGuiController object.
   *
   * @return the mainPane (type AnchorPane) of this MainGuiController object.
   */
  public AnchorPane getMainPane() {
    return mainPane;
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
   * @return the colors (type HashMap&lt;String, Color&gt;) of this MainGuiController object.
   */
  public Map<String, Color> getColors() {
    return colors;
  }

  /**
   * Method workerPlacement displays worker placement.
   *
   * @param coords of type List&lt;int[]&gt; - the coords received.
   */
  public void workerPlacement(List<int[]> coords) {
    gui.getModelView().activateInput();
    String[] set =new String[2];
    AtomicInteger i = new AtomicInteger(0);
      getActionsLabel().setText("Place your worker!");
      getActionsLabel().setVisible(true);
      List<Couple> spaces = new ArrayList<>();
      for (int[] coord : coords) {
        spaces.add(new Couple(coord[0], coord[1]));
      }
      for (Couple element : spaces) {
        AnchorPane node = new AnchorPane();
        grid.add(node, element.getColumn(), element.getRow());
        node.setStyle("-fx-background-color: #ffff00");
        node.setOpacity(0.4);
        int row = GridPane.getRowIndex(node);
        int col = GridPane.getColumnIndex(node);
          node.setOnMouseEntered(mouseEvent -> node.setCursor(Cursor.HAND));
          node.setOnMousePressed(mouseEvent -> node.setCursor(Cursor.CROSSHAIR));
          node.setOnMouseClicked(mouseEvent -> {
            set[i.get()]=" " + row + " " + col;
            i.getAndIncrement();
            grid.getChildren().remove(node);
            if(set[0]!=null && set[1]!=null){
              normalCells();
              getActionsLabel().setVisible(false);
              gui.getListeners().firePropertyChange(ACTION, null, "SET" + set[0] + set[1]);
            }
          });
        }
    }
} 