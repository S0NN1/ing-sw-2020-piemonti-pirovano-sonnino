package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.player.PlayerColors;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ColorMessage is an Answer used for sending a color.
 *
 * @author Luca Pirovano
 * @see Answer
 */
public class ColorMessage implements Answer {
    private final String message;
    private final String color;
    private List<PlayerColors> remaining = new ArrayList<>();

    /**
     * Constructor ColorMessage creates a new ColorMessage instance.
     *
     * @param message of type String - the message to be displayed.
     */
    public ColorMessage(String message) {
        this.message = message;
        this.color=null;
    }
    /**
     * Constructor ColorMessage creates a new ColorMessage instance.
     *
     * @param message of type String - the message to be displayed.
     * @param color of type String - the color parsed.
     */
    public ColorMessage(String message, String color) {
        this.message = message;
        this.color=color;
    }

    /**
     * Method getColor returns the color of this ColorMessage object.
     *
     *
     *
     * @return the color (type String) of this ColorMessage object.
     */
    public String getColor() {
        return color;
    }

    /**
     * Method addRemaining updates the list of reamining colors.
     *
     * @param colors of type List<PlayerColors> - the list of remaining colors.
     */
    public void addRemaining(List<PlayerColors> colors) {
        remaining = colors;
    }

    /**
     * Method getRemaining returns the remaining of this ColorMessage object.
     *
     *
     *
     * @return the remaining (type List<PlayerColors>) of this ColorMessage object.
     */
    public List<PlayerColors> getRemaining() {
        return remaining;
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
