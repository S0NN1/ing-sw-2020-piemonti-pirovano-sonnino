package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.server.answers.Answer;

public class PlayerLostMessage implements Answer {
    private final String loser;

    public String getLoserColor() {
        return loserColor;
    }

    private final String loserColor;

    public PlayerLostMessage(String loser, String loserColor) {
        this.loser = loser;
        this.loserColor = loserColor;
    }
    public PlayerLostMessage(String loser) {
        this.loser = loser;
        this.loserColor = null;
    }
    public String getLoser() {
        return loser;
    }
    @Override
    public Object getMessage() {
        return null;
    }
}
