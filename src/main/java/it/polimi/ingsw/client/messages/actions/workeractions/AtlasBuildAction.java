package it.polimi.ingsw.client.messages.actions.workeractions;

/**
 * UserAction sent by the client to the server, it indicates a build sent by a player with Atlas as god.
 * @author Alice Piemonti
 * @see BuildAction
 */
public class AtlasBuildAction extends BuildAction {

    private boolean dome = false;

    /**
     * Constructor AtlasBuildAction creates a new AtlasBuildAction instance.
     *
     * @param x of type int the row.
     * @param y of type int  the column.
     * @param dome of type boolean dome being set to true if player fired a PLACEDOME.
     */
    public AtlasBuildAction(int x, int y, boolean dome) {
        super(x, y);
        this.dome = dome;
    }

    /**
     * Method isDome return if action is type of PLACEDOME.
     * @return boolean dome.
     */
    public boolean isDome() {
        return dome;
    }
}
