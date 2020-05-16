package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

public class EndTurnMessage implements Answer {

    private final String message;

    public EndTurnMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
