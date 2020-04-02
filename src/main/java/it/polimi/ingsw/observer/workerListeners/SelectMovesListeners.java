package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.SelectMovesMessage;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectMovesListeners extends WorkerListener {

    public SelectMovesListeners(VirtualClient client) {
        super(client);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SelectMovesMessage message = new SelectMovesMessage(evt.getNewValue());
        virtualClient.send(message);
    }
}
