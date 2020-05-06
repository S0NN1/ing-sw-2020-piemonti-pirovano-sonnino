package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.SerializedMessage;
import it.polimi.ingsw.client.messages.SetupConnection;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.server.answers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the connection between the client and the server.
 * @author Luca Pirovano
 */
public class ConnectionSocket {
    private Socket socket;
    private int clientID;
    private final Logger logger = Logger.getLogger(getClass().getName());

    private ObjectOutputStream outputStream;
    SocketListener listener;

    private String serverAddress;
    private int serverPort;

    public ConnectionSocket() {
        this.serverAddress = Constants.ADDRESS;
        this.serverPort = Constants.PORT;
    }

    /**
     * Initializes a new socket connection and handles the nickname-choice response. It loops until the server confirms
     * the successful connection (with no nickname duplication and with a correctly configured match lobby).
     * @param nickname the username chosen by the user.
     * @param modelView the game model.
     * @throws DuplicateNicknameException if the selected nickname has already been taken (case-insensitive).
     */
    public void setup(String nickname, ModelView modelView, ActionHandler actionHandler) throws DuplicateNicknameException{
        try {
            System.out.println(Constants.ANSI_YELLOW + "Configuring socket connection..." + Constants.ANSI_RESET);
            System.out.println(Constants.ANSI_YELLOW + "Opening a socket server communication on port " + serverPort + "..." + Constants.ANSI_RESET);
            this.socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            while(true) {
                try {
                    send(new SetupConnection(nickname));
                    if(nicknameChecker(input.readObject())) {
                        break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                    return;
                }
            }
            listener = new SocketListener(socket, this, modelView, input, actionHandler);
            Thread thread = new Thread(listener);
            thread.start();
        }
        catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            logger.log(Level.SEVERE,e.getMessage(), e);
            System.exit(0);
        }
    }

    /**
     * Handles the nickname validation phase, after the server answer about the availability of the desired username.
     * @param input the server ObjectStream answer.
     * @return true if the nickname is available and set, false instead.
     * @throws DuplicateNicknameException if the nickname has already been chosen.
     */
    public boolean nicknameChecker(Object input) throws DuplicateNicknameException{
        SerializedAnswer answer = (SerializedAnswer)input ;
        if (answer.getServerAnswer() instanceof ConnectionMessage && ((ConnectionMessage) answer.getServerAnswer()).getType()==0) {
            return true;
        }
        else if(answer.getServerAnswer() instanceof GameError) {
            if(((GameError) answer.getServerAnswer()).getError().equals(ErrorsType.DUPLICATENICKNAME)) {
                System.err.println("This nickname is already in use! Please choose one other.");
                throw new DuplicateNicknameException();
            }
            else if(((GameError)answer.getServerAnswer()).getError().equals(ErrorsType.FULLSERVER)) {
                System.err.println("This match is already full, please try again later!\nApplication will now close...");
                System.exit(0);
            }
        }
        return false;
    }

    /**
     * This method handles the sending of a new message to the server. It encapsulates the object in a
     * SerializedMessage type, which will be unpacked and read by the server.
     * @param message the message to be sent to the server.
     */
    public void send(Message message) {
        SerializedMessage output = new SerializedMessage(message);
        try {
            outputStream.reset();
            outputStream.writeObject(output);
            outputStream.flush();
        }
        catch (IOException e) {
            System.err.println("Error during send process.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method handles the sending of a new action to the server. It encapsulates the object in a
     * SerializedMessage type, which will be unpacked and read by the server.
     * @param action the action to be sent to the server.
     */
    public void send(UserAction action) {
        SerializedMessage output = new SerializedMessage(action);
        try {
            outputStream.reset();
            outputStream.writeObject(output);
            outputStream.flush();
        }
        catch (IOException e) {
            System.err.println("Error during send process.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}