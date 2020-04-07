package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.observer.workerListeners.BuildListener;
import it.polimi.ingsw.observer.workerListeners.MoveListener;
import it.polimi.ingsw.observer.workerListeners.SelectSpacesListener;
import it.polimi.ingsw.observer.workerListeners.WinListener;
import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * @author Alice Piemonti
 */
public class Worker {

    protected Space position;
    protected boolean isBlocked;
    protected final String workerColor;
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private ArrayList<Phase> phases;

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
        setPhases();
    }

    /**
     * set the order of actions allowed by this worker
     */
    protected void setPhases(){
        phases = new ArrayList<>();
        phases.add(Phase.SELECTMOVE);
        phases.add(Phase.MOVE);
        phases.add(Phase.SELECTBUILD);
        phases.add(Phase.BUILD);
    }

    /**
     * get element phase at the specified index
     * @param index of element
     * @return phase
     */
    public Phase getPhase(int index){
        return phases.get(index);
    }

    /**
     * create the Map of listeners
     * @param client virtualClient
     */
    public void createListeners(VirtualClient client){
        listeners.addPropertyChangeListener("selectSpacesListener", new SelectSpacesListener(client));
        listeners.addPropertyChangeListener("moveListener", new MoveListener(client));
        listeners.addPropertyChangeListener("winListener", new WinListener(client));
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
     * set a new position to worker
     * @throws IllegalArgumentException if space is null
     * @param space space, the unit of the GameBoard
     */
    public void setPosition(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
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
     * requires this.isSelectable(space)
     * @throws IllegalArgumentException if space is null
     * @param space the new position
     * @return false if the worker can't move into this space
     */
    public boolean move(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        Space oldPosition = position;
        position.setWorker(space.getWorker());
        space.setWorker(this);
        position = space;
        listeners.firePropertyChange("moveListener", oldPosition, position);
        if(position.getTower().getHeight() == 3 && oldPosition.getTower().getHeight() == 2) {
            listeners.firePropertyChange("winListener", null, null);
        }
        return true;
    }

    /**
     * return true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     * @param space a space of the GameBoard
     * @return boolean value
     */
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return ((space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
                (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
                (space.getX() != position.getX() || space.getY() != position.getY()) &&
                !space.getTower().isCompleted() &&
                (space.getTower().getHeight() - position.getTower().getHeight() < 2) &&
                space.isEmpty());
    }

    /**
     * notify the selectSpacesListener with all the moves the worker can do
     * @param gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalStateException if the worker is blocked
     */
    public void getMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        if(gameBoard == null) throw new IllegalArgumentException();
        ArrayList<Space> moves = selectMoves(gameBoard);
        if(moves.isEmpty()) {
            isBlocked = true;
            throw new IllegalStateException();
        }
        listeners.firePropertyChange("selectSpacesListener",null,moves);
    }

    /**
     * return an ArrayList that contains the spaces which the worker can move to
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalThreadStateException if the worker is blocked, so it cannot move
     * @param gameBoard GameBoard of the game
     * @return ArrayList of Spaces
     */
    public ArrayList<Space> selectMoves(GameBoard gameBoard) {
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
     * return false if it isn't an Atlas worker
     * @param space space
     * @param buildDome boolean
     * @return false
     */
    public boolean build(Space space, boolean buildDome){
        return false;
    }

    /**
     * build on the space received
     * @param space space
     * @throws IllegalArgumentException if space is null
     * @return false if it's impossible to build on the space or if OutOfBoundException is thrown
     */
    public boolean build(Space space) throws IllegalArgumentException{
        if(space == null)throw new IllegalArgumentException();
        else if(!isBuildable(space)) return false;
        try {
            space.getTower().addLevel();
        } catch (OutOfBoundException e) {
            return false;
        }
        listeners.firePropertyChange("buildListener",null,space);
        return true;
    }

    /**
     * return true if the worker can build into the space received
     * @throws IllegalArgumentException if space is null
     * @param space space of the GameBoard
     * @return boolean value
     */
    public boolean isBuildable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return (space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
                (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
                (space.getX() != position.getX() || space.getY() != position.getY()) &&
                !space.getTower().isCompleted() &&
                space.isEmpty();
    }

    /**
     * notify the selectSpaceListener with all the spaces on which the worker can build
     * @throws IllegalArgumentException if gameBoard is null
     * @param gameBoard gameBoard of the game
     */
   public void notifyWithBuildable(GameBoard gameBoard){
       if(gameBoard == null) throw new IllegalArgumentException();
       ArrayList<Space> buildable = getBuildableSpaces(gameBoard);
       listeners.firePropertyChange("selectSpacesListener", null, buildable);

   }

    /**
     * return an ArrayList which contains all the buildable spaces
     * @param gameBoard gameBoard
     * @return an ArrayList of spaces
     */
    public ArrayList<Space> getBuildableSpaces(GameBoard gameBoard){
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

