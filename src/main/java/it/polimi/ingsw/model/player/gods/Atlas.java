package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.observer.workerListeners.AtlasBuildListener;
import it.polimi.ingsw.server.VirtualClient;

import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * @author alice
 */
public class Atlas extends Worker {

    public Atlas(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * build a dome at any level or build a block
     * @param buildDome true if he wants to build a dome instead of a block
     * @param space space
     * @return super
     */
    @Override
    public boolean build(Space space, boolean buildDome) throws IllegalArgumentException{
        if(space == null) throw new IllegalArgumentException();
        if(buildDome){
            space.getTower().setDome(true);
            return true;
        }
        else return super.build(space);
    }

    /**
     * notify the selectSpaceListener with all the spaces on which the worker can build
     * notify the DomeListener: this worker can build a dome everywhere
     * @param gameBoard gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     */
    @Override
    public void notifyWithBuildable(GameBoard gameBoard) {
        if(gameBoard == null) throw new IllegalArgumentException();
        ArrayList<Space> buildable = getBuildableSpaces(gameBoard);
        listeners.firePropertyChange("AtlasListener",null,buildable);
    }

    /**
     * create the Map of listeners
     * Atlas has a DomeListener
     * @param client virtualClient
     */
    @Override
    public void createListeners(VirtualClient client) {
        super.createListeners(client);
        listeners.addPropertyChangeListener("AtlasListener", new AtlasBuildListener(client));
    }
}
