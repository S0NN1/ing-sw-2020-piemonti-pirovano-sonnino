package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.server.answers.Answer;

public class PlayerLostMessage implements Answer {
    private final String loser;

    public PlayerLostMessage(String loser) {
        this.loser = loser;
    }

    public String getLoser() {
        return loser;
    }
    @Override
    public Object getMessage() {
        return null;
    }
}
