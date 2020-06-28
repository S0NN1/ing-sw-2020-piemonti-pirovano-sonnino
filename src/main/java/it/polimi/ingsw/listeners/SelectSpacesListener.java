package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.SelectSpacesMessage;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

/**
 * SelectSpacesListener class is a WorkerListener used for notifying client with selectable spaces.
 *
 * @author Alice Piemonti
 * @see WorkerListener
 */
public class SelectSpacesListener extends WorkerListener {

  /**
   * Constructor WorkerListener creates a new WorkerListener instance.
   *
   * @param client of type VirtualClient - the virtual client on Server.
   */
  public SelectSpacesListener(VirtualClient client) {
    super(client);
  }

  /**
   * Method propertyChange notifies client with a SelectSpacesMessage.
   *
   * @param evt of type PropertyChangeEvent - the event received.
   * @see WorkerListener#propertyChange(PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    SelectSpacesMessage message =
        new SelectSpacesMessage((ArrayList<Space>) evt.getNewValue(), (Action) evt.getOldValue());
    virtualClient.send(message);
  }
}
