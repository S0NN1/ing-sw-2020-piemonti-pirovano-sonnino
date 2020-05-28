package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.SerializedMessage;
import it.polimi.ingsw.client.messages.SetupConnection;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.InvalidNicknameException;
import it.polimi.ingsw.server.answers.ConnectionMessage;
import it.polimi.ingsw.server.answers.ErrorsType;
import it.polimi.ingsw.server.answers.GameError;
import it.polimi.ingsw.server.answers.SerializedAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionSocket handles the connection between the client and the server.
 * @author Luca Pirovano
 */
public class ConnectionSocket {
  private final Logger logger = Logger.getLogger(getClass().getName());
  private final String serverAddress;
  private final int serverPort;
  SocketListener listener;
  private Socket socket;
  private ObjectOutputStream outputStream;

  /**
   * Constructor ConnectionSocket creates a new ConnectionSocket instance.
   */
  public ConnectionSocket() {
    this.serverAddress = Constants.getADDRESS();
    this.serverPort = Constants.getPORT();
  }

  /**
   * Method setup initializes a new socket connection and handles the nickname-choice response. It loops until the
   * server confirms the successful connection (with no nickname duplication and with a correctly configured match
   * lobby).
   *
   * @param nickname of type String - the username chosen by the user.
   * @param modelView of type ModelView - the structure, stored into the client, containing simple
   *     logic of the model.
   * @param actionHandler of type ActionHandler - the class handling the answers.
   * @throws DuplicateNicknameException when the nickname is already in use.
   * @throws InvalidNicknameException when the nickname contains illegal characters (like "-").
   */

  public void setup(String nickname, ModelView modelView, ActionHandler actionHandler)
      throws DuplicateNicknameException, InvalidNicknameException {
    try {
      System.out.println(
          Constants.ANSI_YELLOW + "Configuring socket connection..." + Constants.ANSI_RESET);
      System.out.println(
          Constants.ANSI_YELLOW
              + "Opening a socket server communication on port "
              + serverPort
              + "..."
              + Constants.ANSI_RESET);
      this.socket = new Socket(serverAddress, serverPort);
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
      while (true) {
        if (readInput(nickname, input)) {
          break;
        }
      }
      listener = new SocketListener(socket, modelView, input, actionHandler);
      Thread thread = new Thread(listener);
      thread.start();
    } catch (IOException e) {
      System.err.println("Error during socket configuration! Application will now close.");
      logger.log(Level.SEVERE, e.getMessage(), e);
      System.exit(0);
    }
  }


  /**
   * Method readInput handles the input reading in order to reduce the setup complexity.
   *
   * @param nickname of type String - the chosen nickname.
   * @param input of type ObjectInputStream - the input socket stream.
   * @return boolean true if nickname is available and set, false otherwise.
   * @throws DuplicateNicknameException when the nickname has already been chosen.
   * @throws InvalidNicknameException when the nickname contains illegal characters (like "-").
   */
  private boolean readInput(String nickname, ObjectInputStream input)
      throws DuplicateNicknameException, InvalidNicknameException {
    try {
      send(new SetupConnection(nickname));
      if (nicknameChecker(input.readObject())) {
        return true;
      }
    } catch (IOException | ClassNotFoundException e) {
      System.err.println(e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Method nicknameChecker handles the nickname validation phase after the server answer about the availability of
   * the desired username.
   *
   * @param input of type Object - the server ObjectStream answer.
   * @return boolean true if the nickname is available and set, false otherwise.
   * @throws DuplicateNicknameException when the nickname has already been chosen.
   * @throws InvalidNicknameException when the nickname contains illegal characters (like "-").
   */
  public boolean nicknameChecker(Object input)
      throws DuplicateNicknameException, InvalidNicknameException {
    SerializedAnswer answer = (SerializedAnswer) input;
    if (answer.getServerAnswer() instanceof ConnectionMessage
        && ((ConnectionMessage) answer.getServerAnswer()).getType() == 0) {
      return true;
    } else if (answer.getServerAnswer() instanceof GameError) {
      if (((GameError) answer.getServerAnswer()).getError().equals(ErrorsType.DUPLICATENICKNAME)) {
        System.err.println("This nickname is already in use! Please choose one other.");
        throw new DuplicateNicknameException();
      }
      if (((GameError) answer.getServerAnswer()).getError().equals(ErrorsType.INVALIDNICKNAME)) {
        System.err.println("Nickname can't contain - character");
        throw new InvalidNicknameException();
      } else if (((GameError) answer.getServerAnswer()).getError().equals(ErrorsType.FULLSERVER)) {
        System.err.println(
            "This match is already full, please try again later!\nApplication will now close...");
        System.exit(0);
      }
    }
    return false;
  }

  /**
   * Method send sends a new message to the server, encapsulating the object in a SerializedMessage type unpacked and
   * read later by the server.
   *
   * @param message of type Message - the message to be sent to the server.
   */
  public void send(Message message) {
    SerializedMessage output = new SerializedMessage(message);
    try {
      outputStream.reset();
      outputStream.writeObject(output);
      outputStream.flush();
    } catch (IOException e) {
      System.err.println("Error during send process.");
      System.err.println(e.getMessage());
    }
  }

  /**
   * Method send sends a new action to the server, encapsulating the object in a SerializedMessage type unpacked and
   * read later by the server.
   *
   * @param action of type UserAction - the action to be sent to the server.
   */
  public void send(UserAction action) {
    SerializedMessage output = new SerializedMessage(action);
    try {
      outputStream.reset();
      outputStream.writeObject(output);
      outputStream.flush();
    } catch (IOException e) {
      System.err.println("Error during send process.");
      logger.log(Level.SEVERE, e.getMessage(), e);
    }
  }
}
