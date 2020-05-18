package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.Answer;

public class ModifiedTurnMessage implements Answer {
    private final String message;
    private final Action action;

    public ModifiedTurnMessage(String message) {
        this.message = message;
        this.action = null;
    }

    public ModifiedTurnMessage(String message, Action action) {
        this.message = message;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
