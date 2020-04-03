package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.SerializedMessage;

/**
 * Virtual client interface; this is a representation of the virtual instance of the client, which is connected
 * through server socket.
 * It's used for preparing an answer for sending and for general operations on the client too.
 * @author Luca Pirovano
 */
public class VirtualClient {
    private int clientID;
    private String nickname;
    private SocketClientConnection socketClientConnection;
    private GameHandler gameHandler;

    public VirtualClient(int clientID, String nickname, SocketClientConnection socketClientConnection, GameHandler gameHandler) {
        this.nickname = nickname;
        this.clientID = clientID;
        this.socketClientConnection = socketClientConnection;
        this.gameHandler = gameHandler;
    }

    /**
     * @return the unique ID of the client, which identifies him and his connection in the server system.
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @return true if the connection with the client is active yet.
     */
    public boolean isConnected() {
        return(!(socketClientConnection==null));
    }

    public void setSocketClientConnection(SocketClientConnection clientConnection) {
        this.socketClientConnection = clientConnection;
    }

    /**
     * @return the game manager of his match.
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    /**
     * @return the nickname of the player in String format.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Prepares the answer for sending it through the network, putting it in a serialized package, called SerializedMessage,
     * then sends the packaged answer to the transmission protocol, located in the socket-client handler.
     * @see it.polimi.ingsw.server.SocketClientConnection for more details.
     * @param serverAnswer the answer to be sent to the user.
     */
    public void send(Answer serverAnswer) {
        SerializedMessage message = new SerializedMessage();
        message.setServerAnswer(serverAnswer);
        socketClientConnection.sendSocketMessage(message);
    }
}