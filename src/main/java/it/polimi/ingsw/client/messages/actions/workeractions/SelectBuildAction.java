package it.polimi.ingsw.client.messages.actions.workeractions;

/**
 * UserAction sent by the client to the server, it requests possible spaces on which the player can build.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class SelectBuildAction extends WorkerAction {
    /** @see WorkerAction#getMessage() */
    @Override
    public Object getMessage() {
        return null;
    }
}
