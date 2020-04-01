package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.*;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.RequestPlayersNumber;
import it.polimi.ingsw.server.answers.SerializedMessage;
import it.polimi.ingsw.server.answers.WaitMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * An instance of this class is created from SocketServer when it accepts a new connection.
 * It handles a connection between client and server, permitting sending and receiving messages and doing other
 * class-useful operations too.
 * @author Luca Pirovano
 */
public class SocketClientConnection implements ClientConnection, Runnable {
    private Socket socket;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Integer clientID;
    private boolean active;

    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Constructor of the class: it instantiates an input/output stream from the socket received as parameters, and
     * add the main server to his attributes too.
     * @param socket the socket which accepted the client connection.
     * @param server the main server class.
     */
    public SocketClientConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            clientID = -1;
            active = true;
        }
        catch (IOException e) {
            System.err.println(Constants.getErr() + "Error during initialization of the client!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return the connection socket.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Close the connection with the client, terminating firstly input and output streams, and then invoking the server
     * method called "unregisterClient", which will remove the active virtual client from the list.
     * @see it.polimi.ingsw.server.Server#unregisterClient for more details.
     */
    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            server.unregisterClient(this.getClientID());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Read a serializable object from the input stream, using ObjectInputStream library.
     * @throws IOException if the client is not online anymore.
     * @throws ClassNotFoundException if the serializable object is not part of any class.
     */
    public synchronized void readFromStream() throws IOException, ClassNotFoundException {
        Message command = (Message) inputStream.readObject();
        actionHandler(command);
    }

    @Override
    public void run() {
    try {
        while (isActive()) {
            readFromStream();
        }
    }
    catch (IOException e) {
        System.err.println(e.getMessage());
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    close();
    }

    /**
     * Handles an action by receiving a message from the client. The "Message" interface permits splitting the information
     * into several types of messages. This method invokes another one relying on the implementation type of the message received.
     * @param command the Message interface type command, which needs to be checked in order to perform an action.
     */
    public void actionHandler(Message command) {
        if(command instanceof SetupConnection) {
            try {
                SetupConnection cmd = (SetupConnection) command;
                clientID = server.registerConnection(cmd.getNickname(), this);
                if (clientID == null) {
                    active = false;
                    return;
                }
                server.lobby(this);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        else if(command instanceof Setup) {
            try {
                server.setupPlayer(clientID, (Setup) command);
            }
            catch (DuplicateNicknameException | DuplicateColorException e) {
                System.err.println(e.getMessage());
            }
        }
        else if(command instanceof Disconnect) {
            server.unregisterClient(clientID);
        }
    }

    /**
     * Setup method. It permits setting the number of the players in the match, which is decided by the first user connected
     * to the server. It waits for a NumberOfPlayers Message type, then extracts the information about the number of
     * players, passing it as a parameter to the server function "setTotalPlayers".
     * @param message the action received from the user. This method iterates on it until it finds a NumberOfPlayers type.
     */
    public void setPlayers(RequestPlayersNumber message) {
        SerializedMessage ans = new SerializedMessage();
        ans.setServerAnswer(message);
        sendSocketMessage(ans);
        while(true) {
            try {
                Message command = (Message) inputStream.readObject();
                if (command instanceof NumberOfPlayers) {
                    try {
                        int playerNumber = (((NumberOfPlayers) command).playersNumber);
                        server.setTotalPlayers(playerNumber);
                        server.getClientByID(this.clientID).send(new CustomMessage("Success: player number set to " + playerNumber));
                        break;
                    } catch (OutOfBoundException e) {
                        server.getClientByID(this.clientID).send(new CustomMessage("Error: not a valid input! Please provide a value of 2 or 3."));
                        server.getClientByID(this.clientID).send(new RequestPlayersNumber());
                    }
                }
                else {
                    ans.setServerAnswer(new WaitMessage());
                }
            } catch (ClassNotFoundException | IOException e) {
                close();
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Sending to the client method. It allows dispatching serverAnswer to the correct client. The type SerializedMessage
     * contains an Answer type, which represents an interface for server answer, like the client Message one.
     * @param serverAnswer the serialized server answer (interface Answer).
     */
    public void sendSocketMessage(SerializedMessage serverAnswer) {
        try {
            outputStream.reset();
            outputStream.writeObject(serverAnswer);
            //System.out.println("Message sent!");
            outputStream.flush();
        }
        catch (IOException e) {

            e.printStackTrace();
            //TODO: Disconnect Client
        }
    }

    /**
     * @return the client ID of the actual connection.
     */
    public Integer getClientID() {
        return clientID;
    }
}
