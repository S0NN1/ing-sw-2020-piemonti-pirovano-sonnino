package it.polimi.ingsw.listeners;

import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.WinMessage;

import java.beans.PropertyChangeEvent;

/**
 * WinListener is a WorkerListener used for notifying the client with a WinMessage.
 *
 * @author Alice Piemonti
 * @see WorkerListener
 */
public class WinListener extends WorkerListener {

  /**
   * Constructor WorkerListener creates a new WorkerListener instance.
   *
   * @param client of type VirtualClient - the virtual client on Server.
   */
  public WinListener(VirtualClient client) {
    super(client);
  }

  /**
   * Method propertyChange notifies client with WinMessage.
   *
   * @param evt of type PropertyChangeEvent - the event received.
   * @see WorkerListener#propertyChange(PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    WinMessage message = new WinMessage();
    virtualClient.win(message);
  }
}
