package it.polimi.ingsw.server.answers;

/**
 * Class CustomMessage is an Answer used for displaying a custom message.
 *
 * @author Luca Pirovano
 */
public class CustomMessage implements Answer {
    private final String message;
    private final boolean input;

    /**
     * Constructor CustomMessage creates a new CustomMessage instance.
     *
     * @param message of type String
     * @param input of type boolean
     */
    public CustomMessage(String message, boolean input) {
        this.message = message;
        this.input = input;
    }

    /**
     * Method canInput returns if input is enabled.
     * @return boolean
     */
    public boolean canInput() {
        return input;
    }

    /**
     * Method getMessage returns the message of this WorkerPlacement object.
     *
     *
     *
     * @return the message (type Object) of this WorkerPlacement object.
     * @see Answer#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }
}
