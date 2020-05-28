package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.client.messages.actions.UserAction;

import java.io.Serializable;

/**
 * SerializedMessage class is used to serialize messages.
 * @author Luca Pirovano
 * @see Serializable
 */
public class SerializedMessage implements Serializable {
    public final Message message;
    public final UserAction action;

    /**
     * Constructor SerializedMessage creates a new SerializedMessage instance.
     *
     * @param message of type Message - the message needed to be encapsulated.
     */
    public SerializedMessage(Message message) {
        this.message = message;
        this.action = null;
    }

    /**
     * Constructor SerializedMessage creates a new SerializedMessage instance.
     *
     * @param action of type UserAction - the action needed to be encapsulated.
     */
    public SerializedMessage(UserAction action) {
        this.action = action;
        this.message = null;
    }
}
