package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.model.player.PlayerColors;

/**
 * Message sent by client to server containing player's chosen color.
 * @author Luca Pirovano
 * @see Message
 */
public class ChosenColor implements Message {
    private final PlayerColors color;

    /**
     * Constructor ChosenColor creates a new ChosenColor instance.
     *
     * @param color of type PlayerColors - the color chosen.
     */
    public ChosenColor(PlayerColors color) {
        this.color=color;
    }

    /**
     * Method getColor returns the color of this ChosenColor object.
     *
     *
     *
     * @return the color (type PlayerColors) of this ChosenColor object.
     */
    public PlayerColors getColor() {
        return color;
    }
}
