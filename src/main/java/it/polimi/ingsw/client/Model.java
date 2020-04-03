package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.GameError;

import java.util.Observable;

public class Model extends Observable {

    private Answer serverAnswer;
    private CLI cli;

    public Model(CLI cli) {
        this.cli = cli;
    }

    public void answerHandler(Answer answer) {
        if(answer instanceof RequestPlayersNumber) {
            serverAnswer = answer;
            setChanged();
            notifyObservers("RequestPlayerNumber");
        }
        else if(answer instanceof CustomMessage) {
            serverAnswer = answer;
            setChanged();
            notifyObservers("CustomMessage");
        }
        else if(answer instanceof ConnectionClosed) {
            serverAnswer = answer;
            setChanged();
            notifyObservers("ConnectionClosed");
            cli.toggleActiveGame(false);
        }
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }
}
