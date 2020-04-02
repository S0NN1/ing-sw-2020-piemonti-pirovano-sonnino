package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.MoveMessage;

import java.beans.PropertyChangeEvent;

public class MoveListener extends WorkerListener{

    public MoveListener(VirtualClient virtualClient) {
        super(virtualClient);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MoveMessage message = new MoveMessage((Space)evt.getOldValue(),(Space)evt.getNewValue());
        virtualClient.send(message);
    }
}
