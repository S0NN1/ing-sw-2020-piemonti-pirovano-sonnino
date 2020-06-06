package it.polimi.ingsw.listeners;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.DoubleMoveMessage;

import java.beans.PropertyChangeEvent;

/**
 * Class DoubleMoveListener is a WorkerListener used for notifying the client after a double move action.
 *
 * @author Alice Piemonti
 * @see WorkerListener
 */
public class DoubleMoveListener extends WorkerListener {

    /**
     * Constructor WorkerListener creates a new WorkerListener instance.
     *
     * @param client of type VirtualClient  - the virtual client on Server.
     */
    public DoubleMoveListener(VirtualClient client) {
        super(client);
    }

  /**
   * Method propertyChange notifies the client with a DoubleMoveMessage.
   *
   * @param evt of type PropertyChangeEvent - the event provided.
   * @see WorkerListener#propertyChange(PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
        Move move1 = (Move)evt.getOldValue();
        Move move2 = (Move)evt.getNewValue();
        String god = evt.getPropertyName();

        DoubleMoveMessage message = new DoubleMoveMessage(move1, move2, god);
        virtualClient.sendAll(message);
    }
}
