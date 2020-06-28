package it.polimi.ingsw.client.messages.actions.workeractions;

/**
 * AtlasBuildAction is a UserAction sent by the client to the server, it indicates a build sent by a
 * player with Atlas card.
 *
 * @author Alice Piemonti
 * @see BuildAction
 */
public class AtlasBuildAction extends BuildAction {

  private final boolean dome;

  /**
   * Constructor AtlasBuildAction creates a new AtlasBuildAction instance.
   *
   * @param x of type int - the row.
   * @param y of type int - the column.
   * @param dome of type boolean true if player fired a PLACEDOME, false otherwise.
   */
  public AtlasBuildAction(int x, int y, boolean dome) {
    super(x, y);
    this.dome = dome;
  }

  /**
   * Method isDome return if action is type of PLACEDOME.
   *
   * @return boolean true if the action is PLCADOME, false otherwise.
   */
  public boolean isDome() {
    return dome;
  }
}
