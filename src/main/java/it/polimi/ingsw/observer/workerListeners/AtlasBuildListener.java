package it.polimi.ingsw.observer.workerListeners;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.worker.AtlasBuildMessage;

import java.beans.PropertyChangeEvent;

public class AtlasBuildListener extends WorkerListener{

    public AtlasBuildListener(VirtualClient client) {
        super(client);
    }

    /**
     * This method gets called when Atlas wants to build.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AtlasBuildMessage message = new AtlasBuildMessage((Space)evt.getNewValue());
        virtualClient.send(message);
    }
}
