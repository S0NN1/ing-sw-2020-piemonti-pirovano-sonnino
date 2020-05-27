package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.RemoveMessage;

import java.beans.PropertyChangeEvent;

/**
 * Class RemoveBlockListener ...
 *
 * @author Alice Piemonti
 * Created on 26/05/2020
 */
public class RemoveBlockListener extends WorkerListener{

    public RemoveBlockListener(VirtualClient client) {
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
        RemoveMessage message = new RemoveMessage((Space) evt.getNewValue());
        virtualClient.sendAll(message);
    }
}