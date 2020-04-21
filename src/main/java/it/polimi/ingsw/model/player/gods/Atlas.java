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
 * @author Alice Piemonti
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
}
