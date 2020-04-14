package it.polimi.ingsw.client.messages.actions.workerActions;

import it.polimi.ingsw.client.messages.actions.workerActions.WorkerAction;

/**
 * @author Alice Piemonti
 */
public class AtlasBuildAction extends BuildAction {

    private boolean dome = false;

    public AtlasBuildAction(int x, int y) {
        super(x, y);
    }

    /**
     * change dome to true if Atlas worker wants to build a dome instead of a block
     * @param dome
     */
    public void setDome(boolean dome){
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
