package it.polimi.ingsw.model.player;

public class Phase {

    private Action action;
    private boolean must;

    public Phase(Action action, boolean must){
        this.action = action;
        this.must = must;
    }

    public Action getAction() {
        return action;
    }

    public boolean isMust() {
        return must;
    }

    public void changeMust(boolean newMust){
        must = newMust;
    }
}
