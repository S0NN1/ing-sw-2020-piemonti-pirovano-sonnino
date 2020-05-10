package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

/**
 * Answer sent by the server in order to get a worker
 */

public class WorkersRequestMessage implements Answer {
    public final String message = "Select which worker you want to move";

    @Override
    public Object getMessage() {
        return message;
    }
}
