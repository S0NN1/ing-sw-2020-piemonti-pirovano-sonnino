package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.listeners.DoubleMoveListener;
import it.polimi.ingsw.server.VirtualClient;

/**
 * @author Alice Piemonti
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
        listeners.addPropertyChangeListener("MinotaurDoubleMove",new DoubleMoveListener(client));
    }

    /**
     * @param space space
     * @return true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        if( canMoveTo(space) &&
            (space.getTower().getHeight() - position.getTower().getHeight() < 2)){
            if(space.isEmpty()){
                return true;
            }
            else{
                if(space.getRow() == 0 || space.getRow() == 4) {
                    if (space.getColumn() == 0 || space.getColumn() == 4) {   //space is a corner
                        return false;
                    }
                    return space.getRow() == position.getRow(); //space is on border: position must be on the same border
                }
                else if(space.getColumn() == 0 || space.getColumn() == 4){
                     return space.getColumn() == position.getColumn();   //space is on border
                }
                else return true;   //space is not on border
            }
        }
        else  return false;
    }

    /**
     * change the worker's position while check winning condition
     * requires this.isSelectable(space)
     *
     * @param space the new position
     * @return false if the worker can't move into this space or if space isn't empty
     * @throws IllegalArgumentException if space is null
     */
    @Override
    public boolean move(Space space) throws IllegalArgumentException {
        if(!space.isEmpty()) return false;
        return super.move(space);
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
        if(mySpace.getRow() > position.getRow()) x = mySpace.getRow() + 1;    //find coordinates of otherSpace
        else if(mySpace.getRow() < position.getRow()) x = mySpace.getRow() - 1;
        else x = mySpace.getRow();
        if(mySpace.getColumn() > position.getColumn()) y = mySpace.getColumn() + 1;
        else if(mySpace.getColumn() < position.getColumn()) y = mySpace.getColumn() - 1;
        else y = mySpace.getColumn();

        otherSpace = gameBoard.getSpace(x,y);       //move Minotaur and force other worker
        mySpace.getWorker().setPosition(otherSpace);
        Space oldPosition = position;
        oldPosition.setWorker(null);
        this.setPosition(mySpace);
        Move myMove = new Move(oldPosition.getRow(),oldPosition.getColumn(),position.getRow(),position.getColumn());
        Move otherMove = new Move(position.getRow(),position.getColumn(),otherSpace.getRow(),otherSpace.getColumn());
        listeners.firePropertyChange("MinotaurDoubleMove", myMove, otherMove);
        return true;
        }
}




