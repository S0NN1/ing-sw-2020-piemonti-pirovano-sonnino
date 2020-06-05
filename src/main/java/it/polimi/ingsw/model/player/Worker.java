package it.polimi.ingsw.model.player;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
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
 * Worker class defines an abstract class used by every god card .
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
     * Constructor Worker creates a new Worker instance.
     *
     * @param color of type PlayerColors - the player's color.
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
     * Method getCanMoveUp returns the canMoveUp of this Worker object.
     *
     *
     *
     * @return the canMoveUp (type boolean) of this Worker object.
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
     * Method setPhases sets phases.
     */
    public abstract void setPhases();


    /**
     * Method setNormalPhases sets normal turn phases.
     */
    protected void setNormalPhases(){
        phases.add(new Phase(Action.SELECT_MOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECT_BUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }


    /**
     * Method setTwoBuildPhases sets double build phases.
     */
    protected void setTwoBuildPhases() {
        phases.add(new Phase(Action.SELECT_MOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECT_BUILD,true));
        phases.add(new Phase(Action.BUILD,true));
        phases.add(new Phase(Action.SELECT_BUILD,false));
        phases.add(new Phase(Action.BUILD,false));
    }


    /**
     * Method setTwoMovePhases sets double move phases.
     */
    protected void setTwoMovePhases() {
        phases.add(new Phase(Action.SELECT_MOVE,true));
        phases.add(new Phase(Action.MOVE,true));
        phases.add(new Phase(Action.SELECT_MOVE, false));
        phases.add(new Phase(Action.MOVE,false));
        phases.add(new Phase(Action.SELECT_BUILD,true));
        phases.add(new Phase(Action.BUILD,true));
    }


    /**
     * Method getPhase gets a specific worker's phase.
     *
     * @param index of type int - the index of the required action.
     * @return Phase - the phase needed.
     */
    public Phase getPhase(int index){
        if (index < phases.size()) return phases.get(index);
        return null;
    }

    /**
     * Method createListeners creates the Map of listeners.
     * @param client virtualClient - the VirtualClient on the server.
     */
    public void createListeners(VirtualClient client){
        listeners.addPropertyChangeListener(SELECT_SPACES_LISTENER, new SelectSpacesListener(client));
        listeners.addPropertyChangeListener(MOVE_LISTENER, new MoveListener(client));
        listeners.addPropertyChangeListener(WIN_LISTENER, new WinListener(client));
        listeners.addPropertyChangeListener(BUILD_LISTENER,new BuildListener(client));

    }


    /**
     * Method getWorkerColor returns the workerColor of this Worker object.
     *
     *
     *
     * @return the workerColor (type String) of this Worker object.
     */
    public String getWorkerColor() {
        return workerColor;
    }

    /**
     * Method setPosition changes worker's position.
     * @throws IllegalArgumentException when space is null.
     * @param space of type Space - the new position.
     */
    public void setPosition(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        this.position = space;
        space.setWorker(this);
    }


    /**
     * Method getPosition returns the position of this Worker object.
     *
     *
     *
     * @return the position (type Space) of this Worker object.
     */
    public Space getPosition() {
        return this.position;
    }


    /**
     * Method isBlocked returns the blocked of this Worker object.
     *
     *
     *
     * @return the blocked (type boolean) of this Worker object.
     */
    public boolean isBlocked() {
        return this.isBlocked;
    }


    /**
     * Method setBlocked sets the blocked of this Worker object.
     *
     *
     *
     * @param isBlocked the blocked of this Worker object.
     *
     */
    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    /**
     * Method move changes the worker's position while checking winning condition.
     * @throws IllegalArgumentException when space is null.
     * @param space of type Space - the new position.
     * @return boolean false if the worker can't move into this space, true otherwise.
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
     * Method move returns false if it isn't a Minotaur worker.
     * @param mySpace of type Space - the space where worker wants to move to.
     * @param gameBoard of type GameBoard - the game board.
     * @return boolean false if it isn't a Minotaur, true otherwise.
     */
    public boolean move(Space mySpace, GameBoard gameBoard){
        return false;
    }

    /**
     * Method winCondition checks if win condition is satisfied.
     *
     * @param space of type Space - the space provided.
     * @return boolean true if worker has won, false otherwise.
     */
    public boolean winCondition(Space space){
        return position.getTower().getHeight() == 3 && space.getTower().getHeight() == 2;
    }

    /**
     * Method isSelectable returns true if the worker can move to the space received.
     * @throws IllegalArgumentException when space is null.
     * @param space of type Space - the space provided.
     * @return boolean true if space is selectable, false otherwise.
     */
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return canMoveTo(space) &&
                (space.getTower().getHeight() - position.getTower().getHeight() < 2) &&
                space.isEmpty();
    }

    /**
     * Method canMoveto returns true if the space is neighbor to worker's position and if it's possible to move to that
     * space.
     * @param space of type Space - the space provided.
     * @return boolean true if space is reachable and at the correct height, false otherwise.
     */
    protected boolean canMoveTo(Space space){
        return isReachable(space) &&
                !(!canMoveUp && space.getTower().getHeight() - position.getTower().getHeight() > 0);
    }


    /**
     * Method notifyWithMoves notifies the selectSpacesListener with all the moves the worker can do.
     * @param gameBoard of type GameBoard - the game board.
     * @throws IllegalArgumentException when gameBoard is null.
     * @throws IllegalStateException when the worker is blocked.
     */
    public void notifyWithMoves(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
        if(gameBoard == null) throw new IllegalArgumentException();
        List<Space> moves = selectMoves(gameBoard);
        if(moves.isEmpty()) {
            isBlocked = true;
            throw new IllegalStateException();
        }
        listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECT_MOVE, moves);
    }

    /**
     * Method selectMoves returns a List that contains the spaces which the worker can move to.
     * @throws IllegalArgumentException when gameBoard is null.
     * @throws IllegalThreadStateException when the worker is blocked, so it cannot move.
     * @param gameBoard of type GameBoard - the game board.
     * @return List<Space> - the list of spaces.
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
     * Method build returns false if it isn't an Atlas worker.
     * @param space of type Space - the provided Space.
     * @param buildDome of type boolean - checker used for PLACEDOME action.
     * @return boolean
     */
    public boolean build(Space space, boolean buildDome){
        return false;
    }

    /**
     * Method build checks if the space is buildable and build on the space received.
     * @param space of type Space - the space provided.
     * @throws IllegalArgumentException when space is null.
     * @return boolean false if it's impossible to build on the space or if OutOfBoundException is thrown, true otherwise.
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
     * Method canBuildOnto returns true if the worker can build into the space received.
     * @throws IllegalArgumentException when space is null
     * @param space of type Space - the space provided.
     * @return boolean true if space can build onto, false otherwise.
     */
    public boolean canBuildOnto(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return isReachable(space) &&
                space.isEmpty();
    }


    /**
     * Method isReachable checks if provided space is at unitary distance or without a tower completed.
     *
     * @param space of type Space - the provided space.
     * @return boolean true if reachable, false otherwise.
     */
    protected boolean isReachable(Space space) {
        return (space.getRow() - position.getRow() < 2) && (position.getRow() - space.getRow() < 2) &&
                (space.getColumn() - position.getColumn() < 2) && (position.getColumn() - space.getColumn() < 2) &&
                (space.getRow() != position.getRow() || space.getColumn() != position.getColumn()) &&
                !space.getTower().isCompleted();
    }

    /**
     * Method notifyWithBuildable notifies the selectSpaceListener with all the spaces on which the worker can build
     * onto.
     * @throws IllegalArgumentException when gameBoard is null.
     * @param gameBoard of type GameBoard - the game board.
     */
   public void notifyWithBuildable(GameBoard gameBoard) throws IllegalArgumentException, IllegalStateException {
       if(gameBoard == null) throw new IllegalArgumentException();
       List<Space> buildable = getBuildableSpaces(gameBoard);
       if(buildable.isEmpty()) {
            throw new IllegalStateException();
       }
       listeners.firePropertyChange(SELECT_SPACES_LISTENER, Action.SELECT_BUILD, buildable);

   }

    /**
     * Method getBuildableSpaces returns an List which contains all the buildable spaces.
     * @param gameBoard of type GameBoard - GameBoard reference.
     * @return List<Space> - the list of spaces.
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
     * Method isPerimeter indicates whether the space is a perimeter space or not.
     *
     * @param space of type Space - the selected space.
     * @return boolean true if space is a perimeter space, false if it is not.
     */
    public boolean isPerimeter(Space space) {
        return (space.getRow() == Constants.GRID_MIN_SIZE || space.getRow() == (Constants.GRID_MAX_SIZE - 1) ||
                (space.getColumn() == Constants.GRID_MIN_SIZE || space.getColumn() == (Constants.GRID_MAX_SIZE - 1)));
    }


    /**
     * Method exists indicates whether the coordinates exists into the gameBoard.
     *
     * @param coordinates of type Couple - the coordinates of the provided space.
     * @return boolean true if a space is associated to the coordinates, false if coordinates exceed gameBoard bounds.
     */
    public boolean exists( Couple coordinates) {
        return coordinates.getRow() >= Constants.GRID_MIN_SIZE
                && coordinates.getRow() < Constants.GRID_MAX_SIZE
                && coordinates.getColumn() >= Constants.GRID_MIN_SIZE
                && coordinates.getColumn() < Constants.GRID_MAX_SIZE;
    }


    /**
     * Method canForceOn indicates whether an opponent worker can be forced into the space.
     *
     * @param space of type Space - the space selected.
     * @return boolean true if the opponent worker can be forced into the space, false if it can not (for example,
     * whether the space is not empty or there is a completed tower on it).
     */
    public boolean canForceOn(Space space) {
        return space.isEmpty() && !space.getTower().isCompleted();
    }
}

