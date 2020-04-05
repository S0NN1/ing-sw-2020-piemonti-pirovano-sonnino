package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.GameBoard;
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
    public void startAction(Worker currentWorker) throws IllegalStateException{
        worker = currentWorker;
        worker.getMoves(gameBoard);
    }

}
