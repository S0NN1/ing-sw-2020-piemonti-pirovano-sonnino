package it.polimi.ingsw.listeners;

import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeListener;

/**
 * WorkerListener class defines an interface used to communicate to View from Model.
 *
 * @author Alice Piemonti
 * @see PropertyChangeListener
 */
public abstract class WorkerListener implements PropertyChangeListener {

  final VirtualClient virtualClient;

  /**
   * Constructor WorkerListener creates a new WorkerListener instance.
   *
   * @param client of type VirtualClient - the virtual client on Server.
   */
  public WorkerListener(VirtualClient client) {
    virtualClient = client;
  }
}
