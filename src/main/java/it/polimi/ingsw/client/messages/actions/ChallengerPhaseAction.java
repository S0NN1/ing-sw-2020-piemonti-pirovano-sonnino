package it.polimi.ingsw.client.messages.actions;

import it.polimi.ingsw.model.Card;

public class ChallengerPhaseAction implements UserAction {
    public final String action;
    public final Card arg;
    public final Integer startingPlayer;

    public ChallengerPhaseAction(String action, Card arg) {
        this.action = action;
        this.arg = arg;
        startingPlayer=null;
    }

    public ChallengerPhaseAction(String action) {
        this.action = action;
        this.arg = null;
        startingPlayer = null;
    }

    public ChallengerPhaseAction(int player) {
        this.action = null;
        this.arg = null;
        this.startingPlayer = player;
    }
}
