package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.GameError;

import java.util.Observable;

public class Model extends Observable {

    private Answer serverAnswer;
    private CLI cli;
    private boolean canInput;

    public Model(CLI cli) {
        this.cli = cli;
    }

    public synchronized void toggleInput() {
        canInput = true;
    }

    public synchronized void untoggleInput() {
        canInput = false;
    }

    public synchronized boolean getCanInput() {
        return canInput;
    }

    public void answerHandler(Answer answer) {
        serverAnswer = answer;
        if(answer instanceof RequestPlayersNumber) {
            setChanged();
            notifyObservers("RequestPlayerNumber");
        }
        else if(answer instanceof RequestColor) {
            setChanged();
            notifyObservers("RequestColor");
        }
        else if(answer instanceof GodRequest) {
            setChanged();
            notifyObservers("GodRequest");
        }
        else if(answer instanceof CustomMessage) {
            setChanged();
            notifyObservers("CustomMessage");
        }
        else if(answer instanceof ConnectionClosed) {
            setChanged();
            notifyObservers("ConnectionClosed");
            cli.toggleActiveGame(false);
        }
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }
}
