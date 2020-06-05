package it.polimi.ingsw.model.player.gods.simplegods;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.listeners.DoubleMoveListener;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.List;

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
     * @return boolean true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     */
    public boolean isSelectable(Space space, GameBoard gameBoard) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        if( canMoveTo(space) &&
            (space.getTower().getHeight() - position.getTower().getHeight() < 2)){
            if(space.isEmpty()){
                return true;
            }
            else{
                Couple coordinates = calculateCoordinates(space);
                return exists(coordinates) && canForceOn(gameBoard.getSpace(coordinates.getRow(), coordinates.getColumn()));
            }
        }
        else  return false;
    }


    /**
     * Method calculateCoordinates gets the coordinates where the opponent worker is forced to move
     *
     * @param space of type Space
     * @return Couple of coordinates
     */
    private Couple calculateCoordinates(Space space) {
        int row;
        int column;
        //calculate row
        if (space.getRow() == position.getRow()) {
            row = position.getRow();
        }
        else if (space.getRow() > position.getRow()) { //move toward south
            row = space.getRow() + 1;
        }
        else { //move toward north
            row = space.getRow() - 1;
        }
        //calculate column
        if (space.getColumn() == position.getColumn()) {
            column = position.getColumn();
        }
        else if (space.getColumn() > position.getColumn()) { //move toward east
            column = space.getColumn() + 1;
        }
        else {  //move toward west
            column = space.getColumn() - 1;
        }

        return new Couple(row, column);
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
    public List<Space> selectMoves(GameBoard gameBoard) {
        ArrayList<Space> moves = new ArrayList<>();
        for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
            for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
                Space space = gameBoard.getSpace(i, j);
                if (isSelectable(space, gameBoard)) {
                    moves.add(space);
                }
            }
        }
        return moves;
    }

    /**
     * change the worker's position while check winning condition
     * requires this.isSelectable(space)
     *
     * @param space the new position
     * @return boolean false if the worker can't move into this space or if space isn't empty
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
     * @return boolean false if otherSpace isn't valid
     */
    @Override
    public boolean move(Space mySpace, GameBoard gameBoard) throws IllegalArgumentException {
        if (mySpace == null) throw new IllegalArgumentException();
        Space otherSpace;
        Couple coordinates = calculateCoordinates(mySpace);

        otherSpace = gameBoard.getSpace(coordinates.getRow(), coordinates.getColumn());       //move Minotaur and force other worker
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




