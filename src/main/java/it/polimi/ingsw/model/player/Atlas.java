package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.Space;

public class Atlas extends Worker {
    public Atlas(PlayerColors color) {
        super(color);
    }

    @Override
    public void build(Space space, Boolean buildDome) throws OutOfBoundException, IllegalArgumentException {
        if(!buildDome) {
            super.build(space);
        }
        else{

        }
    }
}
