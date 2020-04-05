package it.polimi.ingsw.client.messages.actions;

import it.polimi.ingsw.model.Card;

public class GodSelectionAction implements UserAction {
    public final String action;
    public final Card arg;

    public GodSelectionAction(String action, Card arg) {
        this.action = action;
        this.arg = arg;
    }

    public GodSelectionAction(String action) {
        this.action = action;
        this.arg = null;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
