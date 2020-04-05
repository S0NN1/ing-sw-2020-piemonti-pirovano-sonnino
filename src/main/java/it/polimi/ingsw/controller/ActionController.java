package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.workerActions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workerActions.WorkerAction;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author Alice Piemonti
 */
public class ActionController {

    GameBoard gameBoard;
    Worker worker;
    int phase;

    public ActionController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * this method menages the sequence of worker's action
     *
     * @throws IllegalStateException if worker is blocked
     */
    public boolean startAction(Worker currentWorker) throws NullPointerException {
        if (currentWorker == null) throw new NullPointerException();
        worker = currentWorker;
        phase = 0;
        return nextPhase();
    }

    /**
     * start the next phase of turn
     * @return false if the turn is ended
     */
    public boolean nextPhase() {
        if (worker.getPhase(phase) == null) return false;
        else if (worker.getPhase(phase) == Phase.SELECTMOVE) {
            phase++;
            worker.getMoves(gameBoard);
        } else if (worker.getPhase(phase) == Phase.SELECTBUILD) {
            phase++;
            worker.notifyWithBuildable(gameBoard);
        }
        return true;
    }

    /**
     * build into the space received
     * @param action a couple of int which refers to the space
     * @return false if it isn't the correct phase or if it isn't possible to build into this space
     */
    public boolean readMessage(BuildAction action){
        if(worker.getPhase(phase) != Phase.BUILD) return false;
        Couple couple = action.getMessage();
        if(worker.build(gameBoard.getSpace(couple.getX(), couple.getY()), false)){
            phase++;
            return true;
        }
        else return false;
    }

    /**
     * move the worker into the space received
     * @param action a couple of int which refers to the space
     * @return false if it isn't the correct phase or if the worker cannot move into this space
     */
    public boolean readMessage(MoveAction action) {
        if(worker.getPhase(phase) != Phase.MOVE) return false;
        Couple couple = action.getMessage();
        if(worker.move(gameBoard.getSpace(couple.getX(), couple.getY()))){
            phase++;
            return true;
        }
        else return false;
    }
}