package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

public class Minotaur extends Worker {

    public Minotaur(PlayerColors color) {
        super(color);
    }

    /**
     * @param space space
     * @return true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        if( (space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
            (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
            (space.getX() != position.getX() || space.getY() != position.getY()) &&
            !space.getTower().isCompleted() &&
            (space.getTower().getHeight() - this.position.getTower().getHeight() < 2)){
            if(space.isEmpty()){
                return true;
            }
            else{
                if(space.getX() == 0 || space.getX() == 4) {
                    if (space.getY() == 0 || space.getY() == 4) {   //space is a corner
                        return false;
                    }
                    return space.getX() == position.getX(); //space is on border: position must be on the same border
                }
                else if(space.getY() == 0 || space.getY() == 4){
                     return space.getY() == position.getY();   //space is on border
                }
                else return true;   //space is not on border
            }
        }
        else  return false;
    }

    /**
     * change the worker's position while check winning condition
     *
     * @param space the new position
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if(space.isEmpty()){
            return super.move(space);
        }
        else{
            if(position.getX() - space.getX() < 0){
            }
            return false; ///////////////////space.getWorker().setPosition();
        }
    }
}




