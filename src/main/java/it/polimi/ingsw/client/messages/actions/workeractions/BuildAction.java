package it.polimi.ingsw.client.messages.actions.workeractions;

import it.polimi.ingsw.constants.Couple;

/**
 * @author Alice Piemonti
 */
public class BuildAction extends WorkerAction {

    private final Couple buildSpace;

    public BuildAction(int x, int y){
        buildSpace = new Couple(x,y);
    }

    @Override
    public Couple getMessage() {
        return buildSpace;
    }
}
