package it.polimi.ingsw.server.answers;

/**
 * Class RequestPlayersNumber is a Answer used for requesting total amount of player.
 *
 * @author Luca Pirovano
 * @see Answer
 */
public class RequestPlayersNumber implements Answer {
    private final boolean challenger;
    private final String message;

    /**
     * Constructor RequestPlayersNumber creates a new RequestPlayersNumber instance.
     *
     * @param message of type String
     * @param challenger of type boolean
     */
    public RequestPlayersNumber(String message, boolean challenger) {
        this.message = message;
        this.challenger = challenger;
    }

    /**
     * Method getMessage returns the message of this RequestPlayersNumber object.
     *
     *
     *
     * @return the message (type String) of this RequestPlayersNumber object.
     * @see Answer#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Method isChallenger returns the challenger of this RequestPlayersNumber object.
     *
     *
     *
     * @return the challenger (type boolean) of this RequestPlayersNumber object.
     */
    public boolean isChallenger() {
        return challenger;
    }
}
