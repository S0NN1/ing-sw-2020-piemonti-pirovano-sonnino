package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.GameError;

import java.util.Observable;

/**
 * This class contains a small representation of the game model, and contains linking to the main client actions, which
 * will be invoked after an instance control.
 */
public class Model extends Observable {

    private Answer serverAnswer;
    private CLI cli;
    private boolean canInput;

    public Model(CLI cli) {
        this.cli = cli;
    }

    /**
     * This method toggles the input of the main user class.
     * @see it.polimi.ingsw.client.cli.CLI for more information.
     */
    public synchronized void toggleInput() {
        canInput = true;
    }

    /**
     * This method untoggles the input of the main user class.
     * @see it.polimi.ingsw.client.cli.CLI for more information.
     */
    public synchronized void untoggleInput() {
        canInput = false;
    }

    /**
     * @return the value of the input enabler variable.
     */
    public synchronized boolean getCanInput() {
        return canInput;
    }

    /**
     * Handles the answer received from the server. It calls the client interface passing values relying on the type
     * of answer the server has sent.
     * @param answer the answer received from the server.
     */
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

    /**
     * @return the server answer, containing all the information for the client for action-performing.
     */
    public Answer getServerAnswer() {
        return serverAnswer;
    }
}
