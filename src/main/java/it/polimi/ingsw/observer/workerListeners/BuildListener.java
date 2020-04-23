package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.BuildMessage;

import java.beans.PropertyChangeEvent;

/**
 * @author Alice Piemonti
 */
public class BuildListener extends WorkerListener{

    public BuildListener(VirtualClient client) {
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
        BuildMessage message = new BuildMessage((Space)evt.getNewValue(), (Boolean) evt.getOldValue());
        virtualClient.send(message);
    }
}
