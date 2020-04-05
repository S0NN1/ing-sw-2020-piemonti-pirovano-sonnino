package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.answers.Answer;

public class WinMessage implements Answer {

    Worker winner;

    public WinMessage(Worker worker){
        winner = worker;
    }

    @Override
    public Worker getMessage() {
        return winner;
    }
}
