package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;

import java.util.ArrayList;

/**
 * @author Alice Piemonti
 */
public class Worker {

    private Space position;
    private boolean isBlocked;
    private final String workerColor;

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
    public boolean hasWon() {   return true;
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
        if(space.getTower().getHeight() == 3 && position.getTower().getHeight() == 2) {
            System.out.println("bravo hai vinto");//WIIIIIIIIIIIIIIIIN
        }
        else position = space;
    }

    /**
     * return true if the worker can move to the space received
     * @throws IllegalArgumentException if space is null
     * @param space a space of the GameBoard
     * @return boolean value
     */
    public boolean isSelectable(Space space) throws IllegalArgumentException {
        if(space == null) throw new IllegalArgumentException();
        return (space.getX() - position.getX() < 2) && (position.getX() - space.getX() < 2) &&
                (space.getY() - position.getY() < 2) && (position.getY() - space.getY() < 2) &&
                (space.getX() != position.getX() || space.getY() != position.getY()) &&
                !space.getTower().isCompleted() &&
                (space.getTower().getHeight() - this.position.getTower().getHeight() < 2) &&
                space.isEmpty();
    }

    /**
     * get an ArrayList that contains the spaces which the worker can move to
     * @throws IllegalArgumentException if gambeBoard is null
     * @throws IllegalThreadStateException if the worker is blocked, so it cannot move
     * @param gameBoard GameBoard of the game
     * @return ArrayList of spaces
     */
    public ArrayList<Space> getMoves(GameBoard gameBoard) throws IllegalStateException, IllegalArgumentException {
        if(gameBoard == null) throw new IllegalArgumentException();
        ArrayList<Space> moves = new ArrayList<Space>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Space space = gameBoard.getSpace(i,j);
                if (isSelectable(space)) { moves.add(space);}
            }
        }
        if(moves.isEmpty()) {
            isBlocked = true;
            throw new IllegalStateException();
        }
        return  moves;
    }

    /**
     * build on the space received
     * @param space space
     * @throws OutOfBoundException if it's impossible to build on this space
     * @throws IllegalArgumentException if space is null
     */
    public void build(Space space) throws OutOfBoundException, IllegalArgumentException {
        if(space == null)throw new IllegalArgumentException();
        space.getTower().addLevel();
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
                (space.getX() != position.getX() && space.getY() != position.getY()) &&
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

