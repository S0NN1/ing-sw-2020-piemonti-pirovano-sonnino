package it.polimi.ingsw.server.answers;

import java.util.List;

/**
 * Class GameError is a Answer used for reporting a game error.
 *
 * @author Luca Pirovano
 * @see Answer
 */
public class GameError implements Answer {
    private final ErrorsType error;
    private final String message;
    private final List<int[]> coordinates;

    /**
     * Constructor GameError creates a new GameError instance.
     *
     * @param error of type ErrorsType- the error to display.
     */
    public GameError(ErrorsType error) {
        this.error = error;
        this.message = null;
        this.coordinates = null;
    }

    /**
     * Constructor GameError creates a new GameError instance.
     *
     * @param error of type ErrorsType - the error to display.
     * @param message of type String - the message of the error.
     */
    public GameError(ErrorsType  error, String message) {
        this.error= error;
        this.message = message;
        this.coordinates = null;
    }

    /**
     * Constructor GameError creates a new GameError instance.
     *
     * @param error of type ErrorsType - the error to display.
     * @param message of type String - the message of the error.
     * @param coordinates of type List&lt;int[]&gt; - the list of coordinates.
     */
    public GameError(ErrorsType  error, String message, List<int[]> coordinates) {
        this.error = error;
        this.message = message;
        this.coordinates = coordinates;
    }

    /**
     * Method getMessage returns the message of this GameError object.
     *
     *
     *
     * @return the message (type String) of this GameError object.
     * @see Answer#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Method getError returns the error of this GameError object.
     *
     *
     *
     * @return the error (type ErrorsType) of this GameError object.
     */
    public ErrorsType getError() {
        return error;
    }

    /**
     * Method getCoordinates returns the coordinates of this GameError object.
     *
     *
     *
     * @return the coordinates (type List&lt;int[]&gt;) of this GameError object.
     */
    public List<int[]> getCoordinates() {
        return coordinates;
    }
}
