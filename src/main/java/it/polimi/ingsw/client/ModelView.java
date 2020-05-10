package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.answers.*;

/**
 * This class contains a small representation of the game model, and contains linking to the main client actions, which
 * will be invoked after an instance control.
 */
public class ModelView {

    private Answer serverAnswer;
    private final CLI cli;
    private boolean canInput;
    private int gamePhase;
    private final ClientBoard clientBoard;
    private final GUI gui;
    private volatile int started;
    private int activeWorker;
    private String god;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color;

    public String getGod() {
        return god;
    }

    public void setGod(String god) {
        this.god = god;
    }

    public int getActiveWorker() {
        return activeWorker;
    }

    public void setActiveWorker(int activeWorker) {
        this.activeWorker = activeWorker;
    }



    public ModelView(CLI cli) {
        this.cli = cli;
        gamePhase = 0;
        clientBoard = new ClientBoard();
        gui = null;
    }

    public ModelView(GUI gui) {
        this.gui = gui;
        this.clientBoard = new ClientBoard();
        this.cli = null;
        gamePhase = 0;
    }

    public synchronized ClientBoard getBoard() {
        return clientBoard;
    }

    public CLI getCli() {
        return cli;
    }

    public GUI getGui() {
        return gui;
    }

    public synchronized void setStarted(int val) {
        started = val;
    }

    public synchronized int getStarted() {
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
    //TODO Update gamePhase counter

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
