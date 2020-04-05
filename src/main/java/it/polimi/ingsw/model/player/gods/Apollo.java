package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

public class Apollo extends Worker {
    public Apollo(PlayerColors color) {
        super(color);
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
        if(space == null) throw new IllegalArgumentException();
        return (space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
                (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
                (space.getX() != position.getX() || space.getY() != position.getY()) &&
                !space.getTower().isCompleted() &&
                (space.getTower().getHeight() - this.position.getTower().getHeight() < 2);
    }

    /**
     * change the worker's position while check winning condition. This worker can also change his position with a neighboring worker
     * @param space the new position
     * @throws IllegalArgumentException if space is null
     * @return super
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        else if(!space.isEmpty()) {
            Worker other = space.getWorker();
            other.setPosition(position);
            return super.move(space);
        }
        else return super.move(space);
    }
}
