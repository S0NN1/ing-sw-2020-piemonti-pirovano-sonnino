package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.player.Action;

/**
 * BuildAction class is a UserAction sent by the client to the server, it indicates a build action.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class BuildAction extends WorkerAction {

    private final Couple buildSpace;

    private final Action action;

    /**
     * Constructor BuildAction creates a new BuildAction instance.
     *
     * @param row of type int - the row.
     * @param column of type int - the column.
     */
    public BuildAction(int row, int column){
        buildSpace = new Couple(row,column);
        this.action = Action.BUILD;
    }

    /**
     * Constructor BuildAction creates a new BuildAction instance.
     *
     * @param row of type int - the cell row.
     * @param column of type int - the cell column.
     * @param action of type Action - the type of action.
     */
    public BuildAction(int row, int column, Action action) {
        buildSpace = new Couple(row, column);
        this.action = action;
    }

    /**
     * Method getAction returns the action of this BuildAction object.
     *
     *
     *
     * @return the action (type Action) of this BuildAction object.
     */
    public Action getAction() {
        return action;
    }

    /** @see WorkerAction#getMessage() */
    @Override
    public Couple getMessage() {
        return buildSpace;
    }
}
