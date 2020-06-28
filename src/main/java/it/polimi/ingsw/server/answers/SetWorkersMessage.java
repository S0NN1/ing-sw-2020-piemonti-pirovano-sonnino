package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.constants.Couple;

/**
 * Class SetWorkersMessage is a Answer used for selected spaces on set worker phase.
 *
 * @author Luca Pirovano
 * @see Answer
 */
public class SetWorkersMessage implements Answer {

  private final Couple worker1;
  private final Couple worker2;
  private final String color;

  /**
   * Constructor SetWorkersMessage creates a new SetWorkersMessage instance.
   *
   * @param playerColor of type String - the color of the player.
   * @param workerRow1 of type int - the row of the first worker.
   * @param workerCol1 of type int - the column of the first worker.
   * @param workerRow2 of type int - the row of the second worker.
   * @param workerCol2 of type int - the column of the second worker.
   */
  public SetWorkersMessage(
      String playerColor, int workerRow1, int workerCol1, int workerRow2, int workerCol2) {
    worker1 = new Couple(workerRow1, workerCol1);
    worker2 = new Couple(workerRow2, workerCol2);
    color = playerColor;
  }

  /**
   * Method getMessage returns the message of this SetWorkersMessage object.
   *
   * @return the message (type String) of this SetWorkersMessage object.
   * @see Answer#getMessage()
   */
  @Override
  public String getMessage() {
    return color;
  }

  /**
   * Method getWorker1 returns the worker1 of this SetWorkersMessage object.
   *
   * @return the worker1 (type Couple) of this SetWorkersMessage object.
   */
  public Couple getWorker1() {
    return worker1;
  }
  /**
   * Method getWorker2 returns the worker2 of this SetWorkersMessage object.
   *
   * @return the worker2 (type Couple) of this SetWorkersMessage object.
   */
  public Couple getWorker2() {
    return worker2;
  }
}
