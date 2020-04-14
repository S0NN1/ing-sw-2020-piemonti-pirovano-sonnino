package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.server.answers.worker.AthenaMoveUpMessage;
import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeEvent;

/**
 * @author Alice Piemonti
 */
public class AthenaMoveUpListener extends WorkerListener {

    public AthenaMoveUpListener(VirtualClient client) {
        super(client);
    }

    /**
     * This method gets called when Athena moves up
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AthenaMoveUpMessage message = new AthenaMoveUpMessage();
        virtualClient.send(message);
    }
}
