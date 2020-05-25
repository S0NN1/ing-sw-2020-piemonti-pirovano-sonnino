package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.workeractions.*;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.simplegods.Atlas;
import it.polimi.ingsw.model.player.gods.simplegods.Minotaur;


/**
 * @author Alice Piemonti
 */
public class ActionController {

    private final GameBoard gameBoard;
    private Worker worker;

    protected int phase;

    /**
     * Constructor ActionController creates a new ActionController instance.
     *
     * @param gameBoard of type GameBoard
     */
    public ActionController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * this method menages the sequence of worker's action
     *
     * @throws IllegalStateException if worker is blocked
     */
    public boolean startAction(Worker currentWorker) {
        if (currentWorker == null || currentWorker.isBlocked()) return false;
        worker = currentWorker;
        if(phase!=0 && worker.getPhase(phase)!=null) {
            return false;
        }
        phase = 0;
        if (worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECTMOVE && worker.getPhase(phase).isMust()) {
            try {
                worker.notifyWithMoves(gameBoard);
            } catch (IllegalStateException | IllegalArgumentException e) {
                return false;
            }
            phase++;
            return true;
        }
        else return worker.getPhase(phase) != null && worker.getPhase(phase).getAction() == Action.SELECTBUILD;
    }

    /**
     * check if there are must phases left
     *
     * @return true if it's allowed to end the turn
     */
    public boolean endAction() {
        int phaseTemp = phase;
        if (worker==null) return false;
        while (worker.getPhase(phase) != null && !worker.getPhase(phase).isMust()) {
            phase++;
        }
        if (worker.getPhase(phase) == null) {
            phase=0;
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
            } catch (IllegalArgumentException | IllegalStateException e) {
                return false;
            }
            phase++;
            return true;
        }
        phase = phaseTemp;
        return false;
    }


    /**
     * Method minotaurDiagonalMove states if diagonal move is permitted, checking if it's occupied or contains a dome.
     *
     * @param space of type Space the cell in which the worker wants to move.
     * @param i of type int the index needed for cardinal points check.
     * @param j of type int the index needed for cardinal points check.
     * @return boolean true if the cell is reachable, boolean false otherwise.
     */
    protected boolean minotaurDiagonalMove(Space space, int i, int j) {
        return gameBoard.getSpace(space.getRow() + i, space.getColumn() + j).isEmpty() &&
                !gameBoard.getSpace(space.getRow() + i, space.getColumn() + j).getTower().isCompleted();
    }

    /**
     * Method minotaurCheckMove checks if the neighbour cells are selectable.
     * @param space of type Space the cell in which the worker wants to move.
     * @return boolean true if the cell is reachable, boolean false otherwise.
     */
    protected boolean minotaurCheckMove(Space space) {
        Space position = worker.getPosition();
        if(space.getRow() == position.getRow()) {   // SAME ROW MOVEMENT
            if(space.getColumn() > position.getColumn() && (!gameBoard.getSpace(space.getRow(), space.getColumn() + 1).isEmpty() ||
                    gameBoard.getSpace(space.getRow(), space.getColumn() + 1).getTower().isCompleted())) {     //WEST-EAST
                return false;
            }
            else if(space.getColumn() < position.getColumn() && (!gameBoard.getSpace(space.getRow(), space.getColumn() - 1).isEmpty() ||
                    gameBoard.getSpace(space.getRow(), space.getColumn() - 1).getTower().isCompleted())) {     //EAST-WEST
                return false;
            }
        }
        else if(space.getColumn() == position.getColumn()) {  //SAME COLUMN MOVEMENT
            if(space.getRow() > position.getRow() && (!gameBoard.getSpace(space.getRow() + 1, space.getColumn()).isEmpty() ||
                    gameBoard.getSpace(space.getRow() + 1, space.getColumn()).getTower().isCompleted())) {     //NORTH-SOUTH
                return false;
            }
            else if(space.getRow() < position.getRow() && (!gameBoard.getSpace(space.getRow() - 1, space.getColumn()).isEmpty() ||
                    !gameBoard.getSpace(space.getRow() - 1, space.getColumn()).getTower().isCompleted())) {    //SOUTH-NORTH
                return false;
            }
        }
        else if(space.getRow() > position.getRow() && space.getColumn() > position.getColumn()) {     //SOUTH-EAST
            return minotaurDiagonalMove(space, 1, 1);
        }
        else if(space.getRow() < position.getRow() && space.getColumn() > position.getColumn()) {       //NORTH-EAST
            return minotaurDiagonalMove(space,  -1, 1);
        }
        else if(space.getRow() > position.getRow() && space.getColumn() < position.getColumn()) {       //SOUTH-WEST
            return minotaurDiagonalMove(space, 1, -1);
        }
        else if(space.getRow() < position.getRow() && space.getColumn() < position.getColumn()) {       // NORTH-WEST
            return minotaurDiagonalMove(space,  -1, -1);
        }
        return true;
    }

    /**
     * move the worker into the space received
     *
     * @param action a couple of int which refers to the space
     * @return false if it isn't the correct phase or if the worker cannot move into this space
     */
   /* public boolean readMessage(MoveAction action) {
        if (worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.MOVE) return false;
        Couple couple = action.getMessage();
        Space space = gameBoard.getSpace(couple.getRow(), couple.getColumn());
        if (worker instanceof Minotaur && worker.isSelectable(space) && !space.isEmpty()) {
            if(minotaurCheckMove(space) && worker.move(space, gameBoard)) {
                phase++;
                return true;
            }
            return false;
        }
        else if (worker.isSelectable(space) && worker.move(space)) {
            phase++;
            return true;
        } else return false;
    }*/

    public boolean readMessage(MoveAction action) {
        if (worker.getPhase(phase) == null || worker.getPhase(phase).getAction() != Action.MOVE) return false;
        Couple couple = action.getMessage();
        Space space = gameBoard.getSpace(couple.getRow(), couple.getColumn());
        if (worker instanceof Minotaur && ((Minotaur) worker).isSelectable(space, gameBoard) && !space.isEmpty()) {
            if(worker.move(space, gameBoard)) {
                phase++;
                return true;
            }
            return false;
        }
        else if (worker.isSelectable(space) && worker.move(space)) {
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

    public Worker getWorker() {
        return worker;
    }

    public int getPhase() {
        return phase;
    }
}