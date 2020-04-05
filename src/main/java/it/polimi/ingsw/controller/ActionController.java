package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.workerActions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workerActions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workerActions.WorkerAction;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Atlas;
import it.polimi.ingsw.model.player.Worker;

/**
 * @author Alice Piemonti
 */
public class ActionController {

    GameBoard gameBoard;
    Worker worker;

    public ActionController(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    /**
     * this method menages the sequence of worker's action
     * @throws IllegalStateException if worker is blocked
     */
    public void startAction(Worker currentWorker) throws IllegalStateException, NullPointerException {
        if(currentWorker == null) throw new NullPointerException();
        worker = currentWorker;
    }

    public void readMessage(WorkerAction action) throws OutOfBoundException {
        if(action instanceof BuildAction){
            BuildAction buildAction = (BuildAction)action;
            Couple couple = (Couple)buildAction.getMessage();
            worker.build(gameBoard.getSpace(couple.getX(),couple.getY()), false);
        }
        else if(action instanceof MoveAction){
            MoveAction moveAction = (MoveAction)action;
            Couple couple = (Couple)moveAction.getMessage();
            worker.move(gameBoard.getSpace(couple.getX(), couple.getY()));
        }
    }

}
