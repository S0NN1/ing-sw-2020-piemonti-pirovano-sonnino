package it.polimi.ingsw.client.messages;

import java.io.Serializable;

/**
 * This interface represents a message type which is sent to the server. It's implemented by several message types,
 * relying on the situation.
 * @author Luca Pirovano
 */
public interface Message extends Serializable {}
