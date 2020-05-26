package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;

/**
 * Class CharonForceWorkerAction contains the coordinates of the opponent worker that Charon want to force
 *
 * @author Alice Piemonti
 * Created on 25/05/2020
 */
public class CharonForceWorkerAction extends WorkerAction{

    Couple opponentPosition;
    /**
     * Constructor CharonForceWorkerAction creates a new CharonForceWorkerAction instance.
     *
     * @param row of type int
     * @param column of type int
     */
    public CharonForceWorkerAction(int row, int column) {
        opponentPosition = new Couple(row, column);
    }
    /**
     * Method getMessage returns the message of this WorkerAction object.
     *
     * @return the message (type Object) of this WorkerAction object.
     * @see WorkerAction#getMessage()
     */
    @Override
    public Couple getMessage() {
        return opponentPosition;
    }
}
