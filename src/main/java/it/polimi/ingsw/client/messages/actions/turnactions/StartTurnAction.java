package it.polimi.ingsw.client.messages.actions.turnactions;

import it.polimi.ingsw.client.messages.actions.UserAction;

/**
 * UserAction sent by the client to the server, it starts the turn or contains selected worker.
 * @author Nicolò Sonnino
 * @see UserAction
 */
public class StartTurnAction implements UserAction {
    public final String option;
    /**
     * Constructor StartTurnAction creates a new StartTurnAction instance.
     */
    public StartTurnAction(){
        option = "start";
    }

    /**
     * Constructor StartTurnAction creates a new StartTurnAction instance.
     *
     * @param option of type String - the option used to identify start turn or workers.
     */
    public StartTurnAction(String option){
        this.option=option;
    }
}