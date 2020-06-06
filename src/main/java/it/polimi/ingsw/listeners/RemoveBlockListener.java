package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import java.beans.PropertyChangeEvent;

/**
 * Class RemoveBlockListener is a WorkerListener used for notifying the client after a REMOVELEVEL action.
 *
 * @author Alice Piemonti
 * @see WorkerListener
 */
public class RemoveBlockListener extends WorkerListener{

    /**
     * Constructor WorkerListener creates a new WorkerListener instance.
     *
     * @param client of type VirtualClient  - the virtual client on Server.
     */
    public RemoveBlockListener(VirtualClient client) {
        super(client);
    }


    /**
     * Method propertyChange notifies the client with a BuildMessage.
     *
     * @param evt of type PropertyChangeEvent - the event received.
     * @see WorkerListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        BuildMessage message = new BuildMessage((Space) evt.getNewValue(), Action.REMOVE);
        virtualClient.sendAll(message);
    }
}
