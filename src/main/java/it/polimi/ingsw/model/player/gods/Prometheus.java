package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

import java.util.ArrayList;

public class Prometheus extends Worker {
    public Prometheus(PlayerColors color) {
        super(color);
    }

    boolean canMoveUp = true;

    @Override
    public void setPhases() {
        phases.add(new Phase(Action.SELECTBUILD,false));
        phases.add(new Phase(Action.BUILD,false));
        phases.add(new Phase(Action.SELECTMOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECTBUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }

    /**
     * return an ArrayList which contains all the buildable spaces
     *
     * @param gameBoard gameBoard
     * @return an ArrayList of spaces
     */
    @Override
    public ArrayList<Space> getBuildableSpaces(GameBoard gameBoard) {
        if(canMoveUp){   //first build
            canMoveUp = false;
            phases.get(1).changeMust(true);
        }
        else {  //second build
            canMoveUp = true;
            phases.get(1).changeMust(false);
        }
        return super.getBuildableSpaces(gameBoard);
    }

    //TODO voglio modificare player canMoveUp o fare il controllo internamente al worker?


    /**
     * notify the selectSpacesListener with all the moves the worker can do
     *
     * @param gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalStateException    if the worker is blocked
     */
    @Override
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        super.notifyWithMoves(gameBoard);
        canMoveUp = false;
    }

    /**
     * return true if the worker can move to the space received
     *
     * @param space a space of the GameBoard
     * @return boolean value
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(!canMoveUp && space.getTower().getHeight() - position.getTower().getHeight() == 1) return false;
        return super.isSelectable(space);
    }
}
