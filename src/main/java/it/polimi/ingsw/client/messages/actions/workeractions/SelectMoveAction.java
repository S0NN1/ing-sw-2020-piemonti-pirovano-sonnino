package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.model.player.Action;

/**
 * UserAction sent by the client to the server, it requests possible spaces in which the player can move to.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class SelectMoveAction extends WorkerAction {

    private final Action action;

    public SelectMoveAction() {
        action = Action.SELECT_MOVE;
    }

    public SelectMoveAction(Action action) {
        this.action = action;
    }

    /** @see WorkerAction#getMessage() */
    @Override
    public Action getMessage() {
        return action;
    }
}
