package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeListener;

/**
 * @author Alice Piemonti
 */
public abstract class WorkerListener implements PropertyChangeListener {

    VirtualClient virtualClient;

    public WorkerListener(VirtualClient client){
        virtualClient = client;
    }
}
