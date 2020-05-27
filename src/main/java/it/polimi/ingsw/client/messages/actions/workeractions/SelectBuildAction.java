package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.model.player.Action;

/**
 * UserAction sent by the client to the server, it requests possible spaces on which the player can build.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class SelectBuildAction extends WorkerAction {

    private final Action action;

    public SelectBuildAction() {
        action = Action.SELECT_BUILD;
    }

    public SelectBuildAction(Action action) {
        this.action = action;
    }
    /** @see WorkerAction#getMessage() */
    @Override
    public Action getMessage() {
        return action;
    }
}
