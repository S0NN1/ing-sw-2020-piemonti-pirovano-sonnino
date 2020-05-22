package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;
/**
 * UserAction sent by the client to the server, it indicates a build move action.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class MoveAction extends WorkerAction {

    private final Couple newPosition;

    /**
     * Constructor MoveAction creates a new MoveAction instance.
     *
     * @param x of type int the row.
     * @param y of type int the column.
     */
    public MoveAction(int x, int y){
        newPosition = new Couple(x,y);
    }

    /** @see WorkerAction#getMessage() */
    @Override
    public Couple getMessage() {
        return newPosition;
    }

}
