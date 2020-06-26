package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.MoveMessage;

import java.beans.PropertyChangeEvent;

/**
 * MoveListener class is a WorkerListener used for notifying the client after a move action.
 *
 * @author Alice Piemonti
 * @see WorkerListener
 */
public class MoveListener extends WorkerListener {

  /**
   * Constructor MoveListener creates a new MoveListener instance.
   *
   * @param client of type VirtualClient - the virtual client on Server.
   */
  public MoveListener(VirtualClient client) {
    super(client);
  }

  /**
   * Method propertyChange notifies the client with a MoveMessage.
   *
   * @param evt of type PropertyChangeEvent - the event received.
   * @see WorkerListener#propertyChange(PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    MoveMessage message = new MoveMessage((Space) evt.getOldValue(), (Space) evt.getNewValue());
    virtualClient.sendAll(message);
  }
}
