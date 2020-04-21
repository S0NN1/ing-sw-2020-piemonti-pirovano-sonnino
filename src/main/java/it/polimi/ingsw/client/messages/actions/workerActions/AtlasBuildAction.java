package it.polimi.ingsw.client.messages.actions.workerActions;

import it.polimi.ingsw.client.messages.actions.workerActions.WorkerAction;

/**
 * @author Alice Piemonti
 */
public class AtlasBuildAction extends BuildAction {

    private boolean dome = false;

    public AtlasBuildAction(int x, int y, boolean dome) {
        super(x, y);
        this.dome = dome;
    }

    /**
     * get dome value
     * @return dome
     */
    public boolean isDome() {
        return dome;
    }
}
