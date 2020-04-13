package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.observer.workerListeners.DoubleMoveListener;
import it.polimi.ingsw.server.VirtualClient;

/**
 * @author alice
 */
public class Minotaur extends Worker {

    public Minotaur(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }

    /**
     * create the Map of listeners
     *
     * @param client virtualClient
     */
    @Override
    public void createListeners(VirtualClient client) {
        super.createListeners(client);
        listeners.addPropertyChangeListener("doubleMoveListener",new DoubleMoveListener(client));
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
            (space.getTower().getHeight() - position.getTower().getHeight() < 2)){
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
     *  move Minotaur to mySpace and force the other worker to move from mySpace to otherSpace
     * @param mySpace where Minotaur wants to move
     * @param gameBoard in order to select the space where other worker is forced to move
     * @return false if otherSpace isn't valid
     */
    @Override
    public boolean move(Space mySpace, GameBoard gameBoard) throws IllegalArgumentException {
        if (mySpace == null) throw new IllegalArgumentException();
        Space otherSpace;
        int x;
        int y;
        if(mySpace.getX() > position.getX()) x = mySpace.getX() + 1;    //find coordinates of otherSpace
        else if(mySpace.getX() < position.getX()) x = mySpace.getX() - 1;
        else x = mySpace.getX();
        if(mySpace.getY() > position.getY()) y = mySpace.getY() + 1;
        else if(mySpace.getY() < position.getY()) y = mySpace.getY() - 1;
        else y = mySpace.getY();

        otherSpace = gameBoard.getSpace(x,y);       //move Minotaur and force other worker
        mySpace.getWorker().setPosition(otherSpace);
        Space oldPosition = position;
        oldPosition.setWorker(null);
        this.setPosition(mySpace);
        Move myMove = new Move(oldPosition.getX(),oldPosition.getY(),position.getX(),position.getY());
        Move otherMove = new Move(position.getX(),position.getY(),otherSpace.getX(),otherSpace.getY());
        listeners.firePropertyChange("doubleMoveListener", myMove, otherMove);
        return true;
        }
}




