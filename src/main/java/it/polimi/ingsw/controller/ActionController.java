package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.workerActions.*;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.Atlas;
import it.polimi.ingsw.model.player.gods.Minotaur;

import java.beans.PropertyChangeListener;

/**
 * @author Alice Piemonti
 */
public class ActionController {

    private GameBoard gameBoard;
    private Worker worker;

    private int phase;

    public ActionController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * this method menages the sequence of worker's action
     *
     * @throws IllegalStateException if worker is blocked
     */
    public void startAction(Worker currentWorker) throws NullPointerException {
        if (currentWorker == null) throw new NullPointerException();
        worker = currentWorker;
        phase = 0;
    }

    /**
     * check if there are must phases left
     *
     * @return true if it's allowed to end the turn
     */
    public boolean endAction() {
        int phaseTemp = phase;
        while (worker.getPhase(phase) != null && !worker.getPhase(phase).isMust()) {
            phase++;
        }
        if (worker.getPhase(phase) == null) {
            return true;
        }
        phase = phaseTemp;  //there's still a must phase: worker can't end the turn now
        return false;
    }

    /**
     * notify the player with the moves his worker can make
     *
     * @param action tells only the player wants to receive the list of spaces in which the worker can move
     * @return false if the worker is blocked, or it isn't the correct phase of turn or gameBoard is null
     */
    public boolean readMessage(SelectMoveAction action) {
        int phaseTemp = phase;
        while (worker.getPhase(phase) != null &&
                worker.getPhase(phase).getAction() != Action.SELECTMOVE &&
                !worker.getPhase(phase).isMust()) {
            phase++;
        }
        if (worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECTMOVE) {
            try {
                worker.notifyWithMoves(gameBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                return false;
            }
            phase++;
            return true;
        }
        //case getPhase == null || (!Action.SELECTMOVE && isMust)
        phase = phaseTemp;
        return false;
    }

    /**
     * notify the player with a list of spaces in which his worker can build
     *
     * @param action tells only the player wants to receive the space in which the worker can build
     * @return false if it isn't the correct phase of the turn or gameBoard is null
     */
    public boolean readMessage(SelectBuildAction action) {
        int phaseTemp = phase;
        while (worker.getPhase(phase) != null &&
                worker.getPhase(phase).getAction() != Action.SELECTBUILD &&
                !worker.getPhase(phase).isMust()) {
            phase++;
        }
        if (worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECTBUILD) {
            try {
                worker.notifyWithBuildable(gameBoard);
            } catch (IllegalArgumentException e) {
                return false;
            }
            phase++;
            return true;
        }
        //case getPhase == null || (!Action.SELECTBUILD && isMust)
        phase = phaseTemp;
        return false;
    }

    /**
     * move the worker into the space received
     *
     * @param action a couple of int which refers to the space
     * @return false if it isn't the correct phase or if the worker cannot move into this space
     */
    public boolean readMessage(MoveAction action) {
        if (worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.MOVE) return false;
        Couple couple = action.getMessage();
        Space space = gameBoard.getSpace(couple.getX(), couple.getY());
        if (worker instanceof Minotaur) {
            if (worker.isSelectable(space) && !space.isEmpty() && worker.move(space, gameBoard)) {
                phase++;
                return true;
            }
        }
        if (worker.isSelectable(space) && worker.move(space)) {
            phase++;
            return true;
        } else return false;
    }

    /**
     * build into the space received
     *
     * @param action a couple of int which refers to the space
     * @return false if it isn't the correct phase or if it isn't possible to build into this space
     */
    public boolean readMessage(BuildAction action) {
        if (worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.BUILD) return false;
        Couple couple = action.getMessage();
        if (worker instanceof Atlas && action instanceof AtlasBuildAction) {     //if Atlas worker, he can build a dome instead of a block
            boolean dome = ((AtlasBuildAction) action).isDome();
            if (worker.build(gameBoard.getSpace(couple.getX(), couple.getY()), dome)) {
                phase++;
                return true;
            }
        } else if (worker.build(gameBoard.getSpace(couple.getX(), couple.getY()))) {
            phase++;
            return true;
        }
        return false;
    }
}