package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.server.answers.Answer;

public class CharonForceWorkerMessage implements Answer {
    private final String message;
    public CharonForceWorkerMessage(String message) {
        this.message = message;
    }

    @Override
    public Object getMessage() {
        return message;
    }
}
