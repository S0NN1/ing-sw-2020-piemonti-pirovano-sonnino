package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.BuildMessage;

import java.beans.PropertyChangeEvent;

/**
 * BuildListener class is a WorkerListener used for notifying the client after a build action.
 *
 * @author Alice Piemonti
 * @see WorkerListener
 */
public class BuildListener extends WorkerListener{

    /**
     * Constructor WorkerListener creates a new WorkerListener instance.
     *
     * @param client of type VirtualClient  - the virtual client on Server.
     */
    public BuildListener(VirtualClient client) {
        super(client);
    }


    /**
     * Method propertyChange notifies the client with a BuildMessage.
     *
     * @param evt of type PropertyChangeEvent  - the event received.
     * @see WorkerListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        BuildMessage message = new BuildMessage((Space)evt.getNewValue(), (Boolean) evt.getOldValue());
        virtualClient.sendAll(message);
    }
}
