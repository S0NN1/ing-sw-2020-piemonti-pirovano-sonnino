package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.Answer;

/**
 * BuildMessage class is an Answer used for sending infos about a build action to the client.
 *
 * @author Alice Piemonti
 * @see Answer
 */
public class BuildMessage implements Answer {

  private final Action action;
  private final Couple message;
  private final boolean dome;

  /**
   * Constructor BuildMessage creates a new BuildMessage instance.
   *
   * @param space of type Space - the space involved with the build action.
   * @param dome of type boolean - the boolean indicating if a dome was built.
   */
  public BuildMessage(Space space, boolean dome) {
    message = new Couple(space.getRow(), space.getColumn());
    this.dome = dome;
    action = Action.BUILD;
  }

  /**
   * Constructor BuildMessage creates a new BuildMessage instance.
   *
   * @param space of type Space - the space involved with the build action.
   * @param action of type Action - the type of Action distinguishing a normal BuildAction from
   *     CustomAction.
   */
  public BuildMessage(Space space, Action action) {
    message = new Couple(space.getRow(), space.getColumn());
    this.action = action;
    this.dome = false;
  }

  /**
   * Method getMessage returns the message of this WorkerPlacement object.
   *
   * @return the message (type Object) of this WorkerPlacement object.
   * @see Answer#getMessage()
   */
  @Override
  public Couple getMessage() {
    return message;
  }

  /**
   * Method getDome returns the dome of this BuildMessage object.
   *
   * @return the dome (type boolean) of this BuildMessage object.
   */
  public boolean getDome() {
    return dome;
  }

  /**
   * Method getAction returns the action of this BuildMessage object.
   *
   * @return the action (type Action) of this BuildMessage object.
   */
  public Action getAction() {
    return action;
  }
}
