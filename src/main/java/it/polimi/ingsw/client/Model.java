package it.polimi.ingsw.client;

import it.polimi.ingsw.server.answers.*;

import java.util.Observable;

public class Model extends Observable {

    private Answer serverAnswer;

    public void answerHandler(Answer answer) {
        if(answer instanceof ConnectionConfirmation) {
            serverAnswer = answer;
            setChanged();
            notifyObservers("ConnectionConfirmation");
        }
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }
}
