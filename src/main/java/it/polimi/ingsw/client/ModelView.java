package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.server.answers.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ModelView class contains a small representation of the game model, and contains linking to the
 * main client actions, which is invoked after an instance control.
 *
 * @author Alice Piemonti, Nicolò Sonnino
 */
public class ModelView {

  private Answer serverAnswer;
  private final CLI cli;
  private boolean canInput;
  private int gamePhase;
  private final ClientBoard clientBoard;
  private final GUI gui;
  private volatile int started;
  private int activeWorker;
  private String playerName;
  private String god;
  private Map<String, String> playerMapColor;
  private Map<String, String> playerMapGod;
  private boolean godPowerActive;
  private String currentPlayer;

  /**
   * Method getGodDesc returns the godDesc of this ModelView object.
   *
   * @return the godDesc (type String) of this ModelView object.
   */
  public String getGodDesc() {
    return godDesc;
  }

  /**
   * Method setGodDesc sets the godDesc of this ModelView object.
   *
   * @param godDesc the godDesc (type String) of this ModelView object .
   */
  public void setGodDesc(String godDesc) {
    if (godDesc.length() > 110) {
      String temp1 = godDesc.substring(0, 110);
      String temp2 = godDesc.substring(110);
      if (Character.toString(temp2.charAt(0)).equals(" ")) {
        godDesc = temp1 + "\n" + temp2.substring(1);
      } else {
        godDesc = temp1 + "-" + "\n" + temp2;
      }
    }
    this.godDesc = godDesc;
  }

  private String godDesc;
  private int turnPhase;
  private boolean turnActive;
  private List<Couple> selectSpaces = new ArrayList<>();

  /**
   * Method setPlayerMapColor sets the playerMapColor of this ModelView object.
   *
   * @param playerMapColor the playerMapColor (type Maps&lt;String, String&gt;) of this ModelView
   *     object.
   */
  public void setPlayerMapColor(Map<String, String> playerMapColor) {
    this.playerMapColor = playerMapColor;
  }

  /**
   * Method setPlayerMapGod sets the playerMapGod of this ModelView object.
   *
   * @param playerMapGod the playerMapColor (type Maps&lt;String, String&gt;) of this ModelView
   *     object.
   */
  public void setPlayerMapGod(Map<String, String> playerMapGod) {
    this.playerMapGod = playerMapGod;
  }

  /**
   * Method getPlayerMapColor returns the playerMapColor of this ModelView object.
   *
   * @return the playerMapColor (type Maps&lt;String, String&gt;) of this ModelView object.
   */
  public Map<String, String> getPlayerMapColor() {
    return playerMapColor;
  }

  /**
   * Method getPlayerMapGod returns the playerMapGod of this ModelView object.
   *
   * @return the playerMapGod (type Maps&lt;String, String&gt;) of this ModelView object.
   */
  public Map<String, String> getPlayerMapGod() {
    return playerMapGod;
  }

  /**
   * Method getSelectSpaces returns the selectSpaces of this ModelView object.
   *
   * @return the selectSpaces (type List&lt;Couple&gt;) of this ModelView object.
   */
  public List<Couple> getSelectSpaces() {
    return selectSpaces;
  }

  /**
   * Method setSelectSpaces sets the selectSpaces of this ModelView object.
   *
   * @param selectSpaces the selectSpaces(type List&lt;Couple&gt;) of this ModelView object.
   */
  public void setSelectSpaces(List<Couple> selectSpaces) {
    this.selectSpaces = selectSpaces;
  }

  /**
   * Method getPlayerName returns the playerName of this ModelView object.
   *
   * @return the playerName (type String) of this ModelView object.
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Method setPlayerName sets the playerName of this ModelView object.
   *
   * @param playerName the playerName (type String) of this ModelView object.
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Method getTurnPhase returns the turnPhase of this ModelView object.
   *
   * @return the turnPhase (type int) of this ModelView object.
   */
  public int getTurnPhase() {
    return turnPhase;
  }

  /**
   * Method setTurnPhase sets the turnPhase of this ModelView object.
   *
   * @param turnPhase the turnPhase (type int) of this ModelView object.
   */
  public void setTurnPhase(int turnPhase) {
    this.turnPhase = turnPhase;
  }

  /**
   * Method getColor returns the color of this ModelView object.
   *
   * @return the color (type String) of this ModelView object.
   */
  public String getColor() {
    return color;
  }

  /**
   * Method setColor sets the color of this ModelView object.
   *
   * @param color the color (type color) of this ModelView object.
   */
  public void setColor(String color) {
    this.color = color;
  }

  private String color;

  /**
   * Method getGod returns the god of this ModelView object.
   *
   * @return the god (type String) of this ModelView object.
   */
  public String getGod() {
    return god;
  }

  /**
   * Method setGod sets the god of this ModelView object.
   *
   * @param god the god (type String) of this ModelView object.
   */
  public void setGod(String god) {
    this.god = god;
  }

  /**
   * Method isTurnActive returns the turnActive of this ModelView object.
   *
   * @return the turnActive (type boolean) of this ModelView object.
   */
  public boolean isTurnActive() {
    return turnActive;
  }

  /**
   * Method setTurnActive sets the turnActive of this ModelView object.
   *
   * @param turnActive the turnActive (type boolean) of this ModelView object.
   */
  public void setTurnActive(boolean turnActive) {
    this.turnActive = turnActive;
  }

  /**
   * Method getActiveWorker returns the activeWorker of this ModelView object.
   *
   * @return the activeWorker (type int) of this ModelView object.
   */
  public int getActiveWorker() {
    return activeWorker;
  }

  /**
   * Method setActiveWorker sets the activeWorker of this ModelView object.
   *
   * @param activeWorker the activeWorker (type int) of this ModelView object.
   */
  public void setActiveWorker(int activeWorker) {
    this.activeWorker = activeWorker;
  }

  /**
   * Constructor ModelView creates a new ModelView instance.
   *
   * @param cli of type CLI - CLI reference.
   */
  public ModelView(CLI cli) {
    this.cli = cli;
    gamePhase = 0;
    clientBoard = new ClientBoard();
    gui = null;
    activeWorker = 0;
  }

  /**
   * Constructor ModelView creates a new ModelView instance.
   *
   * @param gui of type GUI - GUI reference.
   */
  public ModelView(GUI gui) {
    this.gui = gui;
    this.clientBoard = new ClientBoard();
    this.cli = null;
    gamePhase = 0;
    activeWorker = 0;
  }

  /**
   * Method getBoard returns the board of this ModelView object.
   *
   * @return the board (type ClientBoard) of this ModelView object.
   */
  public synchronized ClientBoard getBoard() {
    return clientBoard;
  }

  /**
   * Method getCli returns the cli of this ModelView object.
   *
   * @return the cli (type CLI) of this ModelView object.
   */
  public CLI getCli() {
    return cli;
  }

  /**
   * Method getGui returns the gui of this ModelView object.
   *
   * @return the gui (type GUI) of this ModelView object.
   */
  public GUI getGui() {
    return gui;
  }

  /**
   * Method setStarted sets the started of this ModelView object.
   *
   * @param val the started (type int) of this ModelView object.
   */
  public synchronized void setStarted(int val) {
    started = val;
  }

  /**
   * Method getStarted returns the started of this ModelView object.
   *
   * @return the started (type int) of this ModelView object.
   */
  public synchronized int getStarted() {
    return started;
  }
  /**
   * Method setGamePhase sets the gamePhase of this ModelView object.
   *
   * @param phase the gamePhase (of type int) of this ModelView object.
   */
  public void setGamePhase(int phase) {
    gamePhase = phase;
  }

  /**
   * Method getGamePhase returns the gamePhase of this ModelView object.
   *
   * @return the gamePhase (type int) of this ModelView object.
   */
  public int getGamePhase() {
    return gamePhase;
  }

  /** Method activateInput activates the input of the main user class. */
  public synchronized void activateInput() {
    canInput = true;
  }

  /** Method activateInput deactivates the input of the main user class. */
  public synchronized void deactivateInput() {
    canInput = false;
  }

  /**
   * Method getCanInput returns the canInput of this ModelView object.
   *
   * @return the canInput (type boolean) of this ModelView object.
   */
  public synchronized boolean getCanInput() {
    return canInput;
  }

  /**
   * Method setCanInput sets the canInput of this ModelView object.
   *
   * @param value the canInput (type boolean) of this ModelView object.
   */
  public synchronized void setCanInput(boolean value) {
    canInput = value;
  }

  /**
   * Method setServerAnswer sets the serverAnswer of this ModelView object.
   *
   * @param answer the serverAnswer (type Answer) of this ModelView object.
   */
  public void setServerAnswer(Answer answer) {
    this.serverAnswer = answer;
  }

  /**
   * Method getServerAnswer returns the serverAnswer of this ModelView object.
   *
   * @return the serverAnswer (type Answer) of this ModelView object.
   */
  public Answer getServerAnswer() {
    return serverAnswer;
  }

  /**
   * Method unregisterPlayer removes loser's workers from clientBoard.
   *
   * @param loserColor of type String - the loser's color.
   */
  public void unregisterPlayer(String loserColor) {
    for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
      for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
        if (clientBoard.getGrid()[i][j].getColor() != null
            && clientBoard.getGrid()[i][j].getColor().equalsIgnoreCase(loserColor)) {
          clientBoard.getGrid()[i][j].setColor(null);
          clientBoard.getGrid()[i][j].setWorkerNum(0);
        }
      }
    }
  }

  /**
   * Method getWorkersPositionByColor returns the position of the workers identified by the selected
   * color.
   *
   * @param color of type String the color of the player.
   * @return List&lt;Couple&gt; the position of his workers in the grid.
   */
  public List<Couple> getWorkersPositionByColor(String color) {
    List<Couple> couples = new ArrayList<>();
    for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
      for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
        if (clientBoard.getGrid()[i][j].getColor() != null
            && clientBoard.getGrid()[i][j].getColor().equalsIgnoreCase(color)) {
          Couple couple = new Couple(i, j);
          couples.add(couple);
        }
      }
    }
    return couples;
  }

  /**
   * Method isGodPowerActive returns the godPowerActive of this ModelView object.
   *
   * @return the godPowerActive (type boolean) of this ModelView object.
   */
  public synchronized boolean isGodPowerActive() {
    return godPowerActive;
  }

  /**
   * Method setGodPowerActive sets the godPowerActive of this ModelView object.
   *
   * @param godPowerActive the godPowerActive of this ModelView object.
   */
  public synchronized void setGodPowerActive(boolean godPowerActive) {
    this.godPowerActive = godPowerActive;
  }

  /**
   * Method getCurrentPlayer returns the currentPlayer of this ModelView object.
   *
   * @return the currentPlayer (type String) of this ModelView object.
   */
  public String getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Method setCurrentPlayer sets the currentPlayer of this ModelView object.
   *
   * @param currentPlayer the currentPlayer of this ModelView object.
   */
  public void setCurrentPlayer(String currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
}
