package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Phase;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import java.util.List;

/**
 * Prometheus class defines Prometheus card.
 *
 * @author Alice Piemonti
 * @see Worker
 */
public class Prometheus extends Worker {
    /**
     * Constructor Worker creates a new Worker instance.
     *
     * @param color of type PlayerColors - the player's color.
     */
    public Prometheus(PlayerColors color) {
        super(color);
    }

    private boolean powerUsed = true;

    /**
     * Method setPhases sets phases.
     * @see Worker#setPhases()
     */
    @Override
    public void setPhases() {
        phases.add(new Phase(Action.SELECT_BUILD,false));
        phases.add(new Phase(Action.BUILD,false));
        phases.add(new Phase(Action.SELECT_MOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECT_BUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }

    /**
     * Method getBuildableSpaces returns a List containing all the buildable spaces.
     *
     * @param gameBoard of type GameBoard - GameBoard reference.
     * @return List&lt;Space&gt; - the list of selectable spaces.
     * @see Worker#getBuildableSpaces(GameBoard)
     */
    @Override
    public List<Space> getBuildableSpaces(GameBoard gameBoard) {
        if(powerUsed){   //build before move
            powerUsed = false;
            phases.get(1).changeMust(true);
        }
        else {  //build after move
            powerUsed = true;
            phases.get(1).changeMust(false);
        }
        return super.getBuildableSpaces(gameBoard);
    }

    /**
     * Method isSelectable returns true if the worker can move to the space received.
     *
     * @param space of type Space - the space provided.
     * @return boolean true if the space provided is electable, false otherwise.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#isSelectable(Space)
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(!powerUsed && space.getTower().getHeight() - position.getTower().getHeight() == 1) return false;
        return super.isSelectable(space);
    }

    /**
     * Method move changes the worker's position while check winning condition.
     *
     * @param space of type Space - the space provided.
     * @return boolean false if the worker can't move into this space, true otherwise.
     * @throws IllegalArgumentException when space is null.
     * @see Worker#move(Space)
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if(super.move(space)){
            powerUsed = false;
            return true;
        }
        return false;
    }
}
