package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.model.player.Action;

/**
 * SelectBuildAction class is a UserAction sent by the client to the server, it requests possible spaces on which the
 * player can build.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class SelectBuildAction extends WorkerAction {

    private final Action action;

    /**
     * Constructor SelectBuildAction creates a new SelectBuildAction instance.
     */
    public SelectBuildAction() {
        action = Action.SELECT_BUILD;
    }

    /**
     * Constructor SelectBuildAction creates a new SelectBuildAction instance.
     *
     * @param action of type Action - custom action.
     */
    public SelectBuildAction(Action action) {
        this.action = action;
    }

    /** @see WorkerAction#getMessage() */
    @Override
    public Action getMessage() {
        return action;
    }
}
