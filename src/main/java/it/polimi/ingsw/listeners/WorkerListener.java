package it.polimi.ingsw.listeners;

import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeListener;

/**
 * @author Alice Piemonti
 */
public abstract class WorkerListener implements PropertyChangeListener {

    final VirtualClient virtualClient;

    public WorkerListener(VirtualClient client){
        virtualClient = client;
    }
}
