package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;

/**
 * UserAction sent by the client to the server, it indicates a build action.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class BuildAction extends WorkerAction {

    private final Couple buildSpace;

    /**
     * Constructor BuildAction creates a new BuildAction instance.
     *
     * @param x of type int the row.
     * @param y of type int the column.
     */
    public BuildAction(int x, int y){
        buildSpace = new Couple(x,y);
    }

    /** @see WorkerAction#getMessage() */
    @Override
    public Couple getMessage() {
        return buildSpace;
    }
}
