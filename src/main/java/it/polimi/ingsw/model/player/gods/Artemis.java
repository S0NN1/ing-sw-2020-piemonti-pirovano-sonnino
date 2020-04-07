package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.ArrayList;

public class Artemis extends Worker {

    private int phase = 0;

    public Artemis(PlayerColors color) {
        super(color);
    }

    /**
     * set the order of actions allowed by this worker
     */
    @Override
    protected void setPhases() {
        phases.add(new Phase(Action.SELECTMOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECTMOVE, false));
        phases.add(new Phase(Action.MOVE,false));
        phases.add(new Phase(Action.SELECTBUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }

    /**
     * return an ArrayList that contains the spaces which the worker can move to
     *
     * @param gameBoard GameBoard of the game
     * @return ArrayList of Spaces
     * @throws IllegalArgumentException    if gameBoard is null
     * @throws IllegalThreadStateException if the worker is blocked, so it cannot move
     */
    @Override
    public ArrayList<Space> selectMoves(GameBoard gameBoard) {
        if (phase == 2){    //if selectMoves required, he chose to move another time
            phases.get(3).changeMust(true);
        }
        return super.selectMoves(gameBoard);
    }

    /**
     * change the worker's position while check winning condition
     * requires this.isSelectable(space)
     *
     * @param space the new position
     * @return false if the worker can't move into this space
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if(super.move(space)){
            phases.get(3).changeMust(false);
            return true;
        }
        return false;
    }
}
