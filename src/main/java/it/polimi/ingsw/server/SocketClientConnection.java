package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.*;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An instance of this class is created from SocketServer when it accepts a new connection.
 * It handles a connection between client and server, permitting sending and receiving messages and doing other
 * class-useful operations too.
 * @author Luca Pirovano
 * @version 1.0.1
 */
public class SocketClientConnection implements ClientConnection, Runnable {
    private final Socket socket;
    private final Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Integer clientID;
    private boolean active;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public synchronized boolean isActive() {
        return active;
    }


    /**
     * Constructor SocketClientConnection instantiates an input/output stream from the socket received as parameters, and
     * adds the main server to his attributes too.
     *
     * @param socket of type Socket the socket which accepted the client connection.
     * @param server of type Server the main server class.
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
     * Method getSocket returns the socket of this SocketClientConnection object.
     *
     * @return the socket (type Socket) of this SocketClientConnection object.
     */
    public Socket getSocket() {
        return socket;
    }


    /**
     * Method close terminates the connection with the client, closing firstly input and output streams, then invoking the server
     * method called "unregisterClient", which will remove the active virtual client from the list.
     * @see it.polimi.ingsw.server.Server#unregisterClient for more details.
     */
    public void close() {
        server.unregisterClient(this.getClientID());
        try {
            socket.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Method readFromStream reads a serializable object from the input stream, using ObjectInputStream library.
     *
     * @throws IOException when the client is not online anymore.
     * @throws ClassNotFoundException when the serializable object is not part of any class.
     */
    public synchronized void readFromStream() throws IOException, ClassNotFoundException {
        SerializedMessage input = (SerializedMessage)inputStream.readObject();
        if(input.message!=null) {
            Message command = input.message;
            actionHandler(command);
        }
        else if(input.action!=null) {
            UserAction action = input.action;
            actionHandler(action);
        }
    }

    /**
     * Method run is the overriding runnable class method, which is called on a new client connection.
     */
    @Override
    public void run() {
    try {
        while (isActive()) {
            readFromStream();
        }
    }
    catch (IOException e) {
        GameHandler game = server.getGameByID(clientID);
        String player = server.getNicknameByID(clientID);
        server.unregisterClient(clientID);
        if(game.isStarted()>0) {
            game.endGame(player);
        }
        System.err.println(Constants.getInfo() + e.getMessage());
    } catch (ClassNotFoundException e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }
    }


    /**
     * Method actionHandler handles an action by receiving a message from the client.
     * The "Message" interface permits splitting the information into several types of messages.
     * This method invokes another one relying on the implementation type of the message received.
     *
     * @param command of type Message the Message interface type command, which needs to be checked in order to perform an action.
     */
    public void actionHandler(Message command) {
        if(command instanceof SetupConnection) {
            checkConnection((SetupConnection) command);
        }
        else if(command instanceof ChosenColor) {
            if(PlayerColors.isChosen(((ChosenColor)command).getColor())) {
                server.getClientByID(clientID).send(new ColorMessage("Error! This color is not available anymore. Please choose another one!"));
                return;
            }
            server.getGameByID(clientID).getController().setColor(((ChosenColor)command).getColor(), server.getClientByID(clientID).getNickname());
            PlayerColors.choose(((ChosenColor)command).getColor());
            server.getGameByID(clientID).singleSend(new ColorMessage(null,((ChosenColor) command).getColor().toString()), clientID);
            server.getGameByID(clientID).setup();
        }
        else if(command instanceof Disconnect) {
            server.getGameByID(clientID).sendAllExcept(new CustomMessage("Client " + server.getNicknameByID(clientID) +
                    " disconnected from the server.", false), clientID);
            server.getGameByID(clientID).endGame(server.getNicknameByID(clientID));
            close();
        }
    }


    /**
     * Method checkConnection checks the validity of the connection message received from the client.
     *
     * @param command of type SetupConnection the connection command.
     */
    private void checkConnection(SetupConnection command) {
        try {
            clientID = server.registerConnection(command.getNickname(), this);
            if (clientID == null) {
                active = false;
                return;
            }
            server.lobby(this);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    /**
     * Method actionHandler handles an action by receiving a message from the client.
     * The "Action" interface permits splitting the information into several types of action (like move, build, etc).
     * This method invokes the correct part of the server relying on the action type received.
     *
     * @param action of type UserAction the Action interface type command received from the client.
     */
    public void actionHandler(UserAction action) {
        if(server.getGameByID(clientID).getCurrentPlayerID()!=clientID) {
            server.getGameByID(clientID).singleSend(new GameError(ErrorsType.NOTYOURTURN), clientID);
            return;
        }
        if(action instanceof ChallengerPhaseAction) {
            if (server.getGameByID(clientID).isStarted()>3) {
                server.getGameByID(clientID).singleSend(new GameError(ErrorsType.INVALIDINPUT, "Not in correct game phase to perform this command!"), clientID);
                return;
            }
            server.getGameByID(clientID).makeAction(action, "ChallengerPhase");
        }
        else if(action instanceof WorkerSetupMessage) {
            if (server.getGameByID(clientID).isStarted()!=5) {
                server.getGameByID(clientID).singleSend(new GameError(ErrorsType.INVALIDINPUT, "Not in correct game phase to perform this command!"), clientID);
                return;
            }
            server.getGameByID(clientID).makeAction(action, "WorkerPlacement");
        }
        else {
            server.getGameByID(clientID).makeAction(action, "turnController");
        }
    }


    /**
     * Method setPlayers is a setup method.
     * It permits setting the number of the players in the match, which is decided by the first user connected to the server.
     * It waits for a NumberOfPlayers Message type, then extracts the information about the number of players, passing
     * it as a parameter to the server function "setTotalPlayers".
     *
     * @param message of type RequestPlayersNumber the action received from the user. This method iterates on it until it finds a NumberOfPlayers type.
     */
    public void setPlayers(RequestPlayersNumber message) {
        SerializedAnswer ans = new SerializedAnswer();
        ans.setServerAnswer(message);
        sendSocketMessage(ans);
        while(true) {
            try {
                SerializedMessage input = (SerializedMessage)inputStream.readObject();
                Message command = input.message;
                if (command instanceof NumberOfPlayers) {
                    try {
                        int playerNumber = (((NumberOfPlayers) command).playersNumber);
                        server.setTotalPlayers(playerNumber);
                        server.getGameByID(clientID).setPlayersNumber(playerNumber);
                        server.getClientByID(this.clientID).send(new CustomMessage("Success: player number set to " + playerNumber, false));
                        break;
                    } catch (OutOfBoundException e) {
                        server.getClientByID(this.clientID).send(new CustomMessage("Error: not a valid input! Please provide a value of 2 or 3.", false));
                        server.getClientByID(this.clientID).send(new RequestPlayersNumber("Choose the number of players! [2/3]", false));
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                close();
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Method sendSocketMessage allows dispatching the server's Answer to the correct client.
     * The type SerializedMessage contains an Answer type object, which represents an interface for server answer,
     * like the client Message one.
     *
     * @param serverAnswer of type SerializedAnswer the serialized server answer (interface Answer).
     */
    public void sendSocketMessage(SerializedAnswer serverAnswer) {
        try {
            outputStream.reset();
            outputStream.writeObject(serverAnswer);
            outputStream.flush();
        }
        catch (IOException e) {
            close();
        }
    }


    /**
     * Method getClientID returns the clientID of this SocketClientConnection object.
     *
     *
     *
     * @return the clientID (type Integer) of this SocketClientConnection object.
     */
    public Integer getClientID() {
        return clientID;
    }
}
