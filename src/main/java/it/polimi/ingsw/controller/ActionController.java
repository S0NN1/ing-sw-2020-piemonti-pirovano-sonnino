package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.workeractions.*;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.advancedgods.Ares;
import it.polimi.ingsw.model.player.gods.advancedgods.Charon;
import it.polimi.ingsw.model.player.gods.simplegods.Atlas;
import it.polimi.ingsw.model.player.gods.simplegods.Minotaur;


/**
 * ActionController class calls model's method after receiving right turn's actions from TurnController.
 *
 * @author Alice Piemonti
 */
public class ActionController {

    private final GameBoard gameBoard;
    protected int phase;
    private Worker worker;

    /**
     * Constructor ActionController creates a new ActionController instance.
     *
     * @param gameBoard of type GameBoard - GameBoard reference.
     */
    public ActionController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }


    /**
     * Method startAction checks if selected worker is blocked or in a wrong turn phase.
     *
     * @param currentWorker of type Worker - the value stored identifying selected worker if assigned.
     * @return boolean true if selection is correct, false otherwise.
     */
    public boolean startAction(Worker currentWorker) {
        if (currentWorker == null || currentWorker.isBlocked()) return false;
        worker = currentWorker;
        if (phase != 0 && worker.getPhase(phase) != null) {
            return false;
        }
        phase = 0;
        if (worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECT_MOVE &&
                worker.getPhase(phase).isMust()) {
            try {
                worker.notifyWithMoves(gameBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                return false;
            }
            phase++;
            return true;
        }
        else return worker.getPhase(phase) != null && (worker.getPhase(phase).getAction() == Action.SELECT_BUILD ||
                worker.getPhase(phase).getAction() == Action.SELECT_FORCE_WORKER);
    }


    /**
     * Method endAction checks if player can end turn.
     *
     * @return boolean true if result is positive, false otherwise.
     */
    public boolean endAction() {
        int phaseTemp = phase;
        if (worker == null) return false;
        while (worker.getPhase(phase) != null && !worker.getPhase(phase).isMust()) {
            phase++;
        }
        if (worker.getPhase(phase) == null) {
            phase = 0;
            return true;
        }
        phase = phaseTemp;  //there's still a must phase: worker can't end the turn now
        return false;
    }

  /**
   * Method readMessage notifies the player with the moves his worker can make.
   *
   * @param action of type SelectMoveAction - action received from the client.
   * @return boolean  false if the worker is blocked, or it isn't the correct phase of turn or gameBoard is null, true
   * otherwise.
   */
  public boolean readMessage(SelectMoveAction action) {
        if (action.getMessage() == Action.FORCE_WORKER) return selectForceWorkerReadMessage();
        int phaseTemp = phase;
        while (worker.getPhase(phase) != null &&
                worker.getPhase(phase).getAction() != Action.SELECT_MOVE &&
                !worker.getPhase(phase).isMust()) {
            phase++;
        }
        if (worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECT_MOVE) {
            try {
                worker.notifyWithMoves(gameBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                return false;
            }
            phase++;
            return true;
        }
        phase = phaseTemp;
        return false;
    }

    /**
     * Method selectForceWorkerReadMessage notify the player with the spaces where Charon can use his power.
     *
     * @return boolean true if the listener has been fired properly, false if it has not or it is not the correct phase or if the active worker is not Charon.
     */
    private boolean selectForceWorkerReadMessage() {
        if ( worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECT_FORCE_WORKER && worker instanceof Charon) {
            try {
                ((Charon) worker).notifyWithForceWorkerSpaces(gameBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                return false;
            }
            phase ++;
            return true;
        }
        return false;
    }

    /**
     * Method readMessage notifies the player with a list of spaces in which his worker can build.
     *
     * @param action of type SelectBuildAction - action received from the client.
     * @return boolean false if it isn't the correct phase of the turn or gameBoard is null, true otherwise.
     */
    public boolean readMessage(SelectBuildAction action) {
        if (action.getMessage() == Action.SELECT_BUILD) {
            int phaseTemp = phase;
            while (worker.getPhase(phase) != null &&
                    worker.getPhase(phase).getAction() != Action.SELECT_BUILD &&
                    !worker.getPhase(phase).isMust()) {
                phase++;
            }
            if (worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECT_BUILD) {
                try {
                    worker.notifyWithBuildable(gameBoard);
                } catch (IllegalArgumentException | IllegalStateException e) {
                    return false;
                }
                phase++;
                return true;
            }
            phase = phaseTemp;
            return false;
        }
        return false;
    }

    /**
     * Method readMessage notify the player with a list of spaces where Ares can use his power.
     *
     * @param action of type SelectBuildAction must be Action.SELECT_REMOVE.
     * @param unmovedWorkerPosition of type Space is the position of the unmoved worker, which must be the same color as the current worker.
     * @return boolean true if the notification is successful, false if it is not a SELECT_REMOVE action or current worker is not ares or the current phase is not SELECT_REMOVE action.
     */
    private boolean readMessage(SelectBuildAction action, Space unmovedWorkerPosition) {
        if (action.getMessage() == Action.SELECT_REMOVE && ( worker instanceof Ares) && worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECT_REMOVE) {
            try {
                ((Ares) worker).notifyWithRemovable(gameBoard, unmovedWorkerPosition);
            } catch (IllegalArgumentException | IllegalStateException e) {
                return false;
            }
        }
        return false;
    }


    /**
     * Method readMessage moves the worker into the space received.
     *
     * @param action of type MoveAction - action received from teh server.
     * @return false if it isn't the correct phase or if the worker cannot move into this space, true otherwise.
     */
    public boolean readMessage(MoveAction action) {
        if(action.getAction().equals(Action.MOVE)) {
            if (worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.MOVE) return false;
                Couple couple = action.getMessage();
                Space space = gameBoard.getSpace(couple.getRow(), couple.getColumn());
                if (worker instanceof Minotaur && ((Minotaur) worker).isSelectable(space, gameBoard) && !space.isEmpty()) {
                    if (worker.move(space, gameBoard)) {
                        phase++;
                        return true;
                    }
                    return false;
                } else if (worker.isSelectable(space) && worker.move(space)) {
                    phase++;
                    return true;
                } else return false;
            }
        else if (action.getAction() == Action.FORCE_WORKER) return forceWorkerReadMessage(action);
        return false;
    }

    /**
     * Method readMessage builds the worker into the space received.
     *
     * @param action of type BuildAction - action received from teh server.
     * @return false if it isn't the correct phase or if the worker cannot build into this space, true otherwise.
     */
    public boolean readMessage(BuildAction action) {
        if (action.getAction() != Action.BUILD || worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.BUILD) return false;
        Couple couple = action.getMessage();
        if (worker instanceof Atlas && action instanceof AtlasBuildAction) {     //if Atlas worker, he can build a
            // dome instead of a block
            boolean dome = ((AtlasBuildAction) action).isDome();
            if (worker.build(gameBoard.getSpace(couple.getRow(), couple.getColumn()), dome)) {
                phase++;
                return true;
            }
        } else if (worker.build(gameBoard.getSpace(couple.getRow(), couple.getColumn()))) {
            phase++;
            return true;
        }
        return false;
    }

    public boolean readMessage (BuildAction action, Space unmovedWorkerPosition) {
        if (action.getAction() != Action.REMOVE || !(worker instanceof Ares) || worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.REMOVE) return false;
        Couple couple = action.getMessage();
        Space space = gameBoard.getSpace(couple.getRow(), couple.getColumn());
        return ((Ares) worker).checkUnmovedWorkerPosition (unmovedWorkerPosition) && ((Ares) worker).canRemove(space,unmovedWorkerPosition) && ((Ares) worker).removeBlock(space);
    }

    /**
     * Method readMessage forces the worker from the space received with Charon power.
     *
     * @param action of type MoveAction - action received from the server.
     * @return boolean true if Charon forced the worker, false otherwise.
     */
    public boolean forceWorkerReadMessage (MoveAction action) {
        if ( !(worker instanceof Charon) || worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.FORCE_WORKER) return false;
        Couple coordinates = action.getMessage();
        Space space = gameBoard.getSpace(coordinates.getRow(), coordinates.getColumn());
        return ((Charon) worker).forceWorker(space, gameBoard);
    }

    /**
     * Method getWorker returns the worker of this ActionController object.
     *
     * @return the worker (type Worker) of this ActionController object.
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Method getPhase returns the phase of this ActionController object.
     *
     * @return the phase (type int) of this ActionController object.
     */
    public int getPhase() {
        return phase;
    }
}