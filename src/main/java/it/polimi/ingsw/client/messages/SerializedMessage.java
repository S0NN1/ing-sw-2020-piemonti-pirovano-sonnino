package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.client.messages.actions.UserAction;

import java.io.Serializable;

public class SerializedMessage implements Serializable {
    public final Message message;
    public final UserAction action;

    public SerializedMessage(Message message) {
        this.message = message;
        this.action = null;
    }

    public SerializedMessage(UserAction action) {
        this.action = action;
        this.message = null;
    }
}
