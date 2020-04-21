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
import java.net.SocketException;

/**
 * This class handles the connection between the client and the server.
 * @author Luca Pirovano
 */
public class ConnectionSocket {
    private Socket socket;
    private int clientID;

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
     * @param model the game model.
     * @throws DuplicateNicknameException if the selected nickname has already been taken (case-insensitive).
     */
    public void setup(String nickname, Model model) throws DuplicateNicknameException{
        try {
            System.out.println("Configuring socket connection...");
            System.out.println("Opening a socket server communication on port " + serverPort + "...");
            this.socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            while(true) {
                try {
                    send(new SetupConnection(nickname));
                    SerializedAnswer answer = (SerializedAnswer) input.readObject();
                    if (answer.getServerAnswer() instanceof ConnectionConfirmation) {
                        break;
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
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                    return;
                }
            }
            listener = new SocketListener(socket, this, model, input);
            Thread thread = new Thread(listener);
            thread.start();
        }
        catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            e.printStackTrace();
            System.exit(0);
        }
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
            e.printStackTrace();
        }
    }
}