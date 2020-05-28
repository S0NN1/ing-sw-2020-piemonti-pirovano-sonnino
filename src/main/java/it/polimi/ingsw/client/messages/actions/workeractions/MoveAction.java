package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.player.Action;

/**
 * UserAction sent by the client to the server, it indicates a build move action.
 * @author Alice Piemonti
 * @see WorkerAction
 */
public class MoveAction extends WorkerAction {

    private final Couple newPosition;
    private final Action action;

    /**
     * Constructor MoveAction creates a new MoveAction instance.
     *
     * @param x of type int - the row of the cell.
     * @param y of type int - the column of the cell.
     */
    public MoveAction(int x, int y){
        newPosition = new Couple(x,y);
        action = Action.MOVE;
    }

    /**
     * Constructor MoveAction creates a new MoveAction instance.
     * @param x of type int - the row of the cell.
     * @param y of type int - the column of the cell.
     * @param action of type Action - custom action.
     */
    public MoveAction(int x, int y, Action action) {
        newPosition = new Couple(x, y);
        this.action = action;
    }

    /**
     * Method getAction returns the action of this MoveAction object.
     *
     *
     *
     * @return the action (type Action) of this MoveAction object.
     */
    public Action getAction() {
        return action;
    }

    /** @see WorkerAction#getMessage() */
    @Override
    public Couple getMessage() {
        return newPosition;
    }

}
