package it.polimi.ingsw.client.messages.actions.workerActions;

import it.polimi.ingsw.constants.Couple;

public class MoveAction extends WorkerAction {

    private final Couple newPosition;

    public MoveAction(int x, int y){
        newPosition = new Couple(x,y);
    }

    @Override
    public Couple getMessage() {
        return newPosition;
    }

}
