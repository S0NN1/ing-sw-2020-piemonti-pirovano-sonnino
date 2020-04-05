package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.player.Worker;

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
