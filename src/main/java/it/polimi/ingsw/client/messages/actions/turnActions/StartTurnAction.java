package it.polimi.ingsw.client.messages.actions.turnActions;

import it.polimi.ingsw.client.messages.actions.UserAction;

public class StartTurnAction implements UserAction {
    public final String option;
    public StartTurnAction(){
        option="start";
    }
    public StartTurnAction(String option){
        this.option=option;
    }
}