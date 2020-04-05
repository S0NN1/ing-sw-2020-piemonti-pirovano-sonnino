package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

public class workersRequest implements Answer {
    public final String message = "Select which worker you want to move";

    @Override
    public Object getMessage() {
        return message;
    }
}
