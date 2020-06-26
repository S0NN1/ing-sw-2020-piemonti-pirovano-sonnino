package it.polimi.ingsw.server.answers;
/**
 * Class ConnectionMessage is an Answer used for connection.
 *
 * @author Luca Pirovano
 * @see Answer
 */
// Connection was successfully set-up! You are now connected.
public class ConnectionMessage implements Answer {
  private final int type; // 0: connection confirmation, 1: connection termination
  private final String message;

  /**
   * Constructor ConnectionMessage creates a new ConnectionMessage instance.
   *
   * @param message of type String - the message tobe displayed.
   * @param type of type int - the type of connection event (like new connection or client
   *     disconnection).
   */
  public ConnectionMessage(String message, int type) {
    this.message = message;
    this.type = type;
  }

  /**
   * Method getMessage returns the message of this WorkerPlacement object.
   *
   * @return the message (type Object) of this WorkerPlacement object.
   */
  public String getMessage() {
    return message;
  }
  /**
   * Method getType returns the type of this ConnectionMessage object.
   *
   * @return the type (type int) of this ConnectionMessage object.
   */
  public int getType() {
    return type;
  }
}
