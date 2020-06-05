package it.polimi.ingsw.client.messages;

import java.io.Serializable;

/**
 * Message class defines an interface representing a message type sent by the client to the server.
 * It triggers a particular server action based on the type of the message.
 * @author Luca Pirovano
 * @see Serializable
 */
public interface Message extends Serializable {}
