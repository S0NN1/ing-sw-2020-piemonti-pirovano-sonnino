package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.observer.workerListeners.BuildListener;
import it.polimi.ingsw.observer.workerListeners.MoveListener;
import it.polimi.ingsw.observer.workerListeners.SelectMovesListeners;
import it.polimi.ingsw.observer.workerListeners.WinListener;
import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alice Piemonti
 */
public class Worker {

    protected Space position;
    protected boolean isBlocked;
    protected final String workerColor;
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * Constructor
     * @param color player color
     */
    public Worker(PlayerColors color) {
        this.isBlocked = false;
        this.position = null;
        switch (color) {
            case RED:
                this.workerColor = "\u001B[31m";
                break;
            case GREEN:
                this.workerColor = "\u001B[32m";
                break;
            case BLUE:
                this.workerColor = "\u001B[34m";
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * create the Map of listeners
     * @param client virtualClient
     */
    public void createListeners(VirtualClient client){
        listeners.addPropertyChangeListener("moveListener", new MoveListener(client));
        listeners.addPropertyChangeListener("winListener", new WinListener(client));
        listeners.addPropertyChangeListener("selectMovesListener", new SelectMovesListeners(client));
        listeners.addPropertyChangeListener("buildListener",new BuildListener(client));
    }

    /**
     * get worker color
     * @return worker color
     */
    public String getWorkerColor() {
        return workerColor;
    }

    /**
     * set a new position to worker. You can only call this method once
     * @throws IllegalArgumentException if space is null
     * @throws IllegalStateException if the worker has already a position
     * @param space space, the unit of the GameBoard
     */
    public void setPosition(Space space) throws IllegalArgumentException, IllegalStateException {
        if(space == null) throw new IllegalArgumentException();
        else if(position != null) throw new IllegalStateException();
        this.position = space;
        space.setWorker(this);
    }

    /**
     * get the worker's current position
     * @return space, the worker's current position
     */
    public Space getPosition() {
        return this.position;
    }

    /**
     * return true if the worker is blocked (it cannot move anymore)
     * @return boolean value
     */
    public boolean isBlocked() {
        return this.isBlocked;
    }

    /**
     * return true if this worker has won the game
     * @return boolean value
     */
    private boolean hasWon() {   return true;
        /*
        -----------------DA COMPLETARE----------------
         */
    } //METODO NON SENSE

    /**
     * change the worker's position while check winning condition
     * @throws IllegalArgumentException if space is null
     * @param space the new position
     */
    public void move(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        Space oldPosition = position;
        position.setWorker(space.getWorker());
        space.setWorker(this);
        position = space;
        listeners.firePropertyChange("moveListener", oldPosition, position);
        if(position.getTower().getHeight() == 3 && oldPosition.getTower().getHeight() == 2) {
            listeners.firePropertyChange("winListener", null, null);
        }
    }

    /**
     * return true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     * @param space a space of the GameBoard
     * @return boolean value
     */
    protected boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return ((space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
                (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
                (space.getX() != position.getX() || space.getY() != position.getY()) &&
                !space.getTower().isCompleted() &&
                (space.getTower().getHeight() - position.getTower().getHeight() < 2) &&
                space.isEmpty());
    }

    /**
     * notify the selectMovesListener with all the moves the worker can do
     * @param gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalStateException if the worker is blocked
     */
    public void showMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        if(gameBoard == null) throw new IllegalArgumentException();
        ArrayList<Space> moves = getMoves(gameBoard);
        if(moves.isEmpty()) {
            isBlocked = true;
            throw new IllegalStateException();
        }
        listeners.firePropertyChange("selectMovesListener",null,moves);
    }

    /**
     * get an ArrayList that contains the spaces which the worker can move to
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalThreadStateException if the worker is blocked, so it cannot move
     * @param gameBoard GameBoard of the game
     * @return ArrayList of Spaces
     */
    public ArrayList<Space> getMoves(GameBoard gameBoard) {
        ArrayList<Space> moves = new ArrayList<Space>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Space space = gameBoard.getSpace(i, j);
                if (isSelectable(space)) {
                    moves.add(space);
                }
            }
        }
        return moves;
    }

    /**
     * build on the space received
     * @param space space
     * @throws OutOfBoundException if it's impossible to build on this space
     * @throws IllegalArgumentException if space is null
     */
    public void build(Space space, Boolean buildDome) throws IllegalArgumentException, OutOfBoundException {
        if(space == null)throw new IllegalArgumentException();
        space.getTower().addLevel();
        listeners.firePropertyChange("buildListener",null,space);
    }

    /**
     * return true if the worker can build into the space received
     * @throws IllegalArgumentException if space is null
     * @param space space of the GameBoard
     * @return boolean value
     */
    private boolean isBuildable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return (space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
                (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
                (space.getX() != position.getX() || space.getY() != position.getY()) &&
                !space.getTower().isCompleted() &&
                space.isEmpty();
    }

    /**
     * get an ArrayList that contains the spaces on which the worker can build
     * @throws IllegalArgumentException if gameBoard is null
     * @param gameBoard gameBoard of the game
     * @return ArrayList of spaces
     */
    public ArrayList<Space> getBuildableSpaces(GameBoard gameBoard) throws IllegalArgumentException {
        if(gameBoard == null) throw new IllegalArgumentException();
        ArrayList<Space> buildable = new ArrayList<Space>();
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                Space space = gameBoard.getSpace(i,j);
                if(isBuildable(space)){ buildable.add(space);}
            }
        }
        return buildable;
    }
}

