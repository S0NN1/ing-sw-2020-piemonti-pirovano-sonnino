package it.polimi.ingsw.model.player;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.listeners.BuildListener;
import it.polimi.ingsw.listeners.MoveListener;
import it.polimi.ingsw.listeners.SelectSpacesListener;
import it.polimi.ingsw.listeners.WinListener;
import it.polimi.ingsw.server.VirtualClient;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alice Piemonti
 */
public abstract class Worker {

    public static final String SELECT_SPACES_LISTENER = "selectSpacesListener";
    public static final String WIN_LISTENER = "winListener";
    public static final String MOVE_LISTENER = "moveListener";
    public static final String BUILD_LISTENER = "buildListener";
    protected Space position;
    protected boolean isBlocked;
    protected boolean canMoveUp;
    protected final String workerColor;
    protected final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    protected final ArrayList<Phase> phases = new ArrayList<>();

    /**
     * Constructor
     * @param color player color
     */
    public Worker(PlayerColors color) {
        this.isBlocked = false;
        this.position = null;
        this.canMoveUp = true;
        switch (color) {
            case RED -> this.workerColor = "RED";
            case GREEN -> this.workerColor = "GREEN";
            case BLUE -> this.workerColor = "BLUE";
            default -> throw new IllegalArgumentException();
        }
        setPhases();
    }

    /**
     * canMoveUp getter
     * @return a boolean
     */
    public boolean getCanMoveUp() {
        return canMoveUp;
    }


    /**
     * Method setCanMoveUp sets the canMoveUp of this Worker object.
     *
     *
     *
     * @param canMoveUp the canMoveUp of this Worker object.
     *
     */
    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    /**
     * set the order of action allowed by this worker
     */
    public abstract void setPhases();

    /**
     * The worker has normal phases
     */
    protected void setNormalPhases(){
        phases.add(new Phase(Action.SELECTMOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECTBUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }

    /**
     * The worker can build twice in a turn
     */
    protected void setTwoBuildPhases() {
        phases.add(new Phase(Action.SELECTMOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECTBUILD,true));
        phases.add(new Phase(Action.BUILD,true));
        phases.add(new Phase(Action.SELECTBUILD,false));
        phases.add(new Phase(Action.BUILD,false));
    }

    /**
     * The worker can move twice in a turn
     */
    protected void setTwoMovePhases() {
        phases.add(new Phase(Action.SELECTMOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECTMOVE, false));
        phases.add(new Phase(Action.MOVE,false));
        phases.add(new Phase(Action.SELECTBUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }

    /**
     * get element phase at the specified index
     * @param index of element
     * @return phase
     */
    public Phase getPhase(int index){
        if (index < phases.size()) return phases.get(index);
        return null;
    }

    /**
     * create the Map of listeners
     * @param client virtualClient
     */
    public void createListeners(VirtualClient client){
        listeners.addPropertyChangeListener(SELECT_SPACES_LISTENER, new SelectSpacesListener(client));
        listeners.addPropertyChangeListener(MOVE_LISTENER, new MoveListener(client));
        listeners.addPropertyChangeListener(WIN_LISTENER, new WinListener(client));
        listeners.addPropertyChangeListener(BUILD_LISTENER,new BuildListener(client));

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
     * Set the isBlocked attribute if the worker cannot move anymore.
     * @param isBlocked boolean value
     */
    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

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
        setPosition(space);
        listeners.firePropertyChange(MOVE_LISTENER, oldPosition, position);
        if(winCondition(oldPosition)) {
            listeners.firePropertyChange(WIN_LISTENER, null, null);
        }
        return true;
    }

    /**
     * return false if it isn't a Minotaur worker
     * @param mySpace where worker wants to move
     * @param gameBoard in order to select the space where other worker is forced to move
     * @return false if it isn't a Minotaur
     */
    public boolean move(Space mySpace, GameBoard gameBoard){
        return false;
    }

    /**
     * Check if win condition is satisfied
     *
     * @param space of type Space
     * @return boolean
     */
    public boolean winCondition(Space space){
        return position.getTower().getHeight() == 3 && space.getTower().getHeight() == 2;
    }

    /**
     * return true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     * @param space a space of the GameBoard
     * @return boolean value
     */
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return canMoveTo(space) &&
                (space.getTower().getHeight() - position.getTower().getHeight() < 2) &&
                space.isEmpty();
    }

    /**
     * return true if the space is neighbor to worker's position and if it's possible to move to that space
     * @param space space
     * @return boolean
     */
    protected boolean canMoveTo(Space space){
        return isReachable(space) &&
                !(!canMoveUp && space.getTower().getHeight() - position.getTower().getHeight() > 0);
    }


    /**
     * notify the selectSpacesListener with all the moves the worker can do
     * @param gameBoard of the game
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalStateException if the worker is blocked
     */
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        if(gameBoard == null) throw new IllegalArgumentException();
        List<Space> moves = selectMoves(gameBoard);
        if(moves.isEmpty()) {
            isBlocked = true;
            throw new IllegalStateException();
        }
        listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECTMOVE, moves);
    }

    /**
     * return an ArrayList that contains the spaces which the worker can move to
     * @throws IllegalArgumentException if gameBoard is null
     * @throws IllegalThreadStateException if the worker is blocked, so it cannot move
     * @param gameBoard GameBoard of the game
     * @return ArrayList of Spaces
     */
    public List<Space> selectMoves(GameBoard gameBoard) {
        ArrayList<Space> moves = new ArrayList<>();
        for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
            for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
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
     * check if the space is buildable and build on the space received
     * @param space space
     * @throws IllegalArgumentException if space is null
     * @return false if it's impossible to build on the space or if OutOfBoundException is thrown
     */
    public boolean build(Space space) throws IllegalArgumentException{
        if(space == null)throw new IllegalArgumentException();
        else if(!canBuildOnto(space)) return false;
        try {
            space.getTower().addLevel();
        } catch (OutOfBoundException e) {
            return false;
        }
        listeners.firePropertyChange(BUILD_LISTENER,false,space);
        return true;
    }

    /**
     * return true if the worker can build into the space received
     * @throws IllegalArgumentException if space is null
     * @param space space of the GameBoard
     * @return boolean value
     */
    public boolean canBuildOnto(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return isReachable(space) &&
                space.isEmpty();
    }


    /**
     * Method isReachable ...
     *
     * @param space of type Space
     * @return boolean
     */
    protected boolean isReachable(Space space) {
        return (space.getRow() - position.getRow() < 2) && (position.getRow() - space.getRow() < 2) &&
                (space.getColumn() - position.getColumn() < 2) && (position.getColumn() - space.getColumn() < 2) &&
                (space.getRow() != position.getRow() || space.getColumn() != position.getColumn()) &&
                !space.getTower().isCompleted();
    }

    /**
     * notify the selectSpaceListener with all the spaces on which the worker can build
     * @throws IllegalArgumentException if gameBoard is null
     * @param gameBoard gameBoard of the game
     */
   public void notifyWithBuildable(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
       if(gameBoard == null) throw new IllegalArgumentException();
       List<Space> buildable = getBuildableSpaces(gameBoard);
       if(buildable.isEmpty()) {
            throw new IllegalStateException();
       }
       listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECTBUILD, buildable);

   }

    /**
     * return an ArrayList which contains all the buildable spaces
     * @param gameBoard gameBoard
     * @return an ArrayList of spaces
     */
    public List<Space> getBuildableSpaces(GameBoard gameBoard){
        ArrayList<Space> buildable = new ArrayList<>();
        for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++){
            for(int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++){
                Space space = gameBoard.getSpace(i,j);
                if(canBuildOnto(space)){ buildable.add(space);}
            }
        }
        return buildable;
    }

    /**
     * Method isPerimetric indicates whether the space is a perimeter space or not.
     *
     * @param space of type Space
     * @return boolean true if space is a perimeter space, false if it is not.
     */
    public boolean isPerimetric(Space space) {
        return (space.getRow() == Constants.GRID_MIN_SIZE || space.getRow() == (Constants.GRID_MAX_SIZE - 1) ||
                (space.getColumn() == Constants.GRID_MIN_SIZE || space.getColumn() == (Constants.GRID_MAX_SIZE - 1)));
    }
}

