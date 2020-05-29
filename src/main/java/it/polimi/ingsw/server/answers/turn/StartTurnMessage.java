package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

public class StartTurnMessage implements Answer {
    private String currentPlayer;

    public StartTurnMessage(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Object getMessage() {
        return currentPlayer;
    }
}
