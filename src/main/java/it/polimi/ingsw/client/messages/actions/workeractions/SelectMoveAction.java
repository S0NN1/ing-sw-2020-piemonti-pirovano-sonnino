package it.polimi.ingsw.client.messages.actions.workeractions;

/**
 * UserAction sent by the client to the server, it requests possible spaces in which the player can move to.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class SelectMoveAction extends WorkerAction {

    /** @see WorkerAction#getMessage() */
    @Override
    public Object getMessage() {
        return null;
    }
}
