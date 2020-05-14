package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.server.answers.Answer;

public class LoseMessage implements Answer {
    private final String winner;

    public LoseMessage(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
