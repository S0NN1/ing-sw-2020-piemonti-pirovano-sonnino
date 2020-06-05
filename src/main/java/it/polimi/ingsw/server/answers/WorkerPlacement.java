package it.polimi.ingsw.server.answers;

import java.util.List;

/**
 * Class WorkerPlacement is a Answer used for displaying available places for setup worker phase.
 *
 * @author Luca Pirovano
 * @see Answer
 */
public class WorkerPlacement implements Answer{
    private final String message;
    private final List<int[]> availableCoordinates;

    /**
     * Constructor WorkerPlacement creates a new WorkerPlacement instance.
     *
     * @param message of type String - the message received.
     * @param coords of type List<int[]> - the list with avaible spaces.
     */
    public WorkerPlacement(String message, List<int[]> coords) {
        this.message = message;
        availableCoordinates = coords;
    }

    /**
     * Method getAvailableCoordinates returns the availableCoordinates of this WorkerPlacement object.
     *
     *
     *
     * @return the availableCoordinates (type List<int[]>) of this WorkerPlacement object.
     */
    public List<int[]> getAvailableCoordinates() {
        return availableCoordinates;
    }

    /** @see Answer#getMessage() */
    @Override
    public Object getMessage() {
        return message;
    }
}
