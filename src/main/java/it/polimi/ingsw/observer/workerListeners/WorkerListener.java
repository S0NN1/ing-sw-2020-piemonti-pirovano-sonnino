package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeListener;

public abstract class WorkerListener implements PropertyChangeListener {

    VirtualClient virtualClient;

    public WorkerListener(VirtualClient client){
        virtualClient = client;
    }
}
