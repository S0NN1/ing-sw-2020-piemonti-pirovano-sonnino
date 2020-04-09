package it.polimi.ingsw.client.messages.actions.workerActions;

import it.polimi.ingsw.constants.Couple;

public class BuildAction extends WorkerAction {

    private Couple buildSpace;
    private boolean dome;

    public BuildAction(int x, int y){
        buildSpace = new Couple(x,y);
    }

    @Override
    public Couple getMessage() {
        return buildSpace;
    }
}
