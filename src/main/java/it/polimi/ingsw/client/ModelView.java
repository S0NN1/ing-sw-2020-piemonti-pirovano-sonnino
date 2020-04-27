package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.server.answers.*;

import java.beans.PropertyChangeSupport;

/**
 * This class contains a small representation of the game model, and contains linking to the main client actions, which
 * will be invoked after an instance control.
 */
public class ModelView {

    private Answer serverAnswer;
    private CLI cli;
    private boolean canInput;
    private int gamePhase;
    private final ClientBoard clientBoard;
    private volatile int started;

    public ModelView(CLI cli) {
        this.cli = cli;
        gamePhase = 0;
        clientBoard = new ClientBoard();
    }

    public synchronized ClientBoard getBoard() {
        return clientBoard;
    }

    public CLI getCli() {
        return cli;
    }

    public synchronized void setStarted(int val) {
        started = val;
    }

    public int getStarted() {
        return started;
    }

    /**
     * Set the game phase variable to the value provided:
     * - 0: setup phase
     * - 1: game phase
     * - 2: __coming soon__
     * @param phase the current phase of the game.
     */
    public void setGamePhase(int phase) {
        gamePhase = phase;
    }

    /**
     * @return the current game phase.
     */
    public int getGamePhase() {
        return gamePhase;
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
     * Set the canInput variable to the value provided.
     * @param value states if user can make an input or not.
     */
    public synchronized void setCanInput(boolean value) {
        canInput = value;
    }

    public void setServerAnswer(Answer answer) {
        this.serverAnswer = answer;
    }

    /**
     * @return the server answer, containing all the information for the client for action-performing.
     */
    public Answer getServerAnswer() {
        return serverAnswer;
    }
}
