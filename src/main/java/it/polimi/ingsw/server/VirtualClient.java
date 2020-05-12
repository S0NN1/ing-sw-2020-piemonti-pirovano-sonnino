package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.ChallengerMessages;
import it.polimi.ingsw.server.answers.SerializedAnswer;

import java.util.Observable;
import java.util.Observer;

/**
 * Virtual client interface; this is a representation of the virtual instance of the client, which is connected
 * through server socket.
 * It's used for preparing an answer for sending and for general operations on the client too.
 * @author Luca Pirovano
 */
public class VirtualClient implements Observer {
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

    public VirtualClient() {
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
        return(socketClientConnection!=null);
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

    public SocketClientConnection getConnection() {
        return socketClientConnection;
    }

    /**
     * Prepares the answer for sending it through the network, putting it in a serialized package, called SerializedMessage,
     * then sends the packaged answer to the transmission protocol, located in the socket-client handler.
     * @see it.polimi.ingsw.server.SocketClientConnection for more details.
     * @param serverAnswer the answer to be sent to the user.
     */
    public void send(Answer serverAnswer) {
        SerializedAnswer message = new SerializedAnswer();
        message.setServerAnswer(serverAnswer);
        socketClientConnection.sendSocketMessage(message);
    }

    /**
     * Send the message to all playing clients, thanks to the GameHandler sendAll method.
     * @param serverAnswer the message to be sent.
     */
    public void sendAll(Answer serverAnswer) {
        gameHandler.sendAll(serverAnswer);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ChallengerMessages) {
            send((ChallengerMessages)arg);
        }
        else if(arg instanceof CustomMessage){
            send((CustomMessage)arg);
        }
    }
}