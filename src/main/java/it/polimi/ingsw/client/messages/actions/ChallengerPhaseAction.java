package it.polimi.ingsw.client.messages.actions;

import it.polimi.ingsw.model.Card;

/**
 * ChallengerPhaseAction class is a UserAction sent by the client to the server, handles Challenger's setup phase.
 * @author Luca Pirovano
 * @see UserAction
 */
public class ChallengerPhaseAction implements UserAction {
    public final String action;
    public final Card arg;
    public final Integer startingPlayer;

    /**
     * Constructor ChallengerPhaseAction creates a new ChallengerPhaseAction instance.
     *
     * @param action of type String - the type of the action.
     * @param arg of type Card - the god's card.
     */
    public ChallengerPhaseAction(String action, Card arg) {
        this.action = action;
        this.arg = arg;
        startingPlayer=null;
    }

    /**
     * Constructor ChallengerPhaseAction creates a new ChallengerPhaseAction instance.
     *
     * @param action of type String - the type of the action.
     */
    public ChallengerPhaseAction(String action) {
        this.action = action;
        this.arg = null;
        startingPlayer = null;
    }

    /**
     * Constructor ChallengerPhaseAction creates a new ChallengerPhaseAction instance.
     *
     * @param player of type int - the player's ID.
     */
    public ChallengerPhaseAction(int player) {
        this.action = null;
        this.arg = null;
        this.startingPlayer = player;
    }
}
