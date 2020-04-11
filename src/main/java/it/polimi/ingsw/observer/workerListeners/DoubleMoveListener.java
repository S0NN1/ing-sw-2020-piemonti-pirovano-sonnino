package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.DoubleMoveMessage;

import java.beans.PropertyChangeEvent;

public class DoubleMoveListener extends WorkerListener {

    public DoubleMoveListener(VirtualClient client) {
        super(client);
    }

    /**
     * This method gets called when there are two moves simultaneously
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Move move1 = (Move)evt.getOldValue();
        Move move2 = (Move)evt.getNewValue();
        DoubleMoveMessage message = new DoubleMoveMessage(move1,move2);
        virtualClient.send(message);
    }
}
