package it.polimi.ingsw.client.messages.actions;

import java.util.ArrayList;

/**
 * WorkerSetupAction class is a UserAction sent by the client to the server, used to place workers
 * on the board.
 *
 * @author Luca Pirovano
 * @see UserAction
 */
public class WorkerSetupAction implements UserAction {

  private final ArrayList<Integer> xPositions = new ArrayList<>();
  private final ArrayList<Integer> yPositions = new ArrayList<>();

  /**
   * Constructor WorkerSetupMessage creates a new WorkerSetupMessage instance.
   *
   * @param in of type String[] - the position's array.
   */
  public WorkerSetupAction(String[] in) {
    xPositions.add(Integer.parseInt(in[1]));
    yPositions.add(Integer.parseInt(in[2]));
    xPositions.add(Integer.parseInt(in[3]));
    yPositions.add(Integer.parseInt(in[4]));
  }

  /**
   * Method getXPosition gets workers' rows.
   *
   * @param index of type int - the number defining which worker is requested (1/2).
   * @return int - the row requested.
   */
  public int getXPosition(int index) {
    return xPositions.get(index);
  }

  /**
   * Method getXPosition gets workers' columns.
   *
   * @param index of type int - the number defining which worker is requested (1/2).
   * @return int - the column requested.
   */
  public int getYPosition(int index) {
    return yPositions.get(index);
  }
}
