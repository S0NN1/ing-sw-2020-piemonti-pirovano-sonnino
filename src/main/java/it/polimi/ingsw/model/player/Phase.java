package it.polimi.ingsw.model.player;

/**
 * Phase class represents a singular sub-phase of the turn, it is used to identify different gods.
 *
 * @author Alice Piemonti
 */
public class Phase {

  private final Action action;
  private boolean must;

  /**
   * Constructor Phase creates a new Phase instance.
   *
   * @param action of type Action - the action provided.
   * @param must of type boolean - checker defining if action on phase is required for the turn.
   */
  public Phase(Action action, boolean must) {
    this.action = action;
    this.must = must;
  }

  /**
   * Method getAction returns the action of this Phase object.
   *
   * @return the action (type Action) of this Phase object.
   */
  public Action getAction() {
    return action;
  }

  /**
   * Method isMust returns the must of this Phase object.
   *
   * @return the must (type boolean) of this Phase object.
   */
  public boolean isMust() {
    return must;
  }

  /**
   * Method changeMust changes the priority of an Action.
   *
   * @param newMust of type boolean - the checker defining the priority of the Action.
   */
  public void changeMust(boolean newMust) {
    must = newMust;
  }
}
