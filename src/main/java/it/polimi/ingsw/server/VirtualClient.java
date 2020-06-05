package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.ChallengerMessages;
import it.polimi.ingsw.server.answers.SerializedAnswer;
import it.polimi.ingsw.server.answers.worker.LoseMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Virtual client interface; this is a representation of the virtual instance of the client, which is connected
 * through server socket.
 * It's used for preparing an answer for sending and for general operations on the client too.
 * @author Luca Pirovano

 */
public class VirtualClient implements PropertyChangeListener {
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

    public VirtualClient() {}


    /**
     * Method getClientID returns the clientID of this VirtualClient object.
     *
     *
     *
     * @return the unique ID (type int) of the client, which identifies him and his connection in the server system.
     */
    public int getClientID() {
        return clientID;
    }


    /**
     * Method isConnected returns the connected of this VirtualClient object.
     *
     *
     *
     * @return true if the connection with the client is active yet.
     */
    public boolean isConnected() {
        return(socketClientConnection!=null);
    }


    /**
     * Method getGameHandler returns the gameHandler of this VirtualClient object.
     *
     *
     *
     * @return the game manager (type GameHandler) of the client's match.
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    /**
     * Method getNickname returns the nickname of this VirtualClient object.
     *
     *
     *
     * @return the nickname (type String) of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Method getConnection returns the connection of this VirtualClient object.
     *
     *
     *
     * @return the connection (type SocketClientConnection) of this client.
     */
    public SocketClientConnection getConnection() {
        return socketClientConnection;
    }

    /**
     * Method send prepares the answer for sending it through the network, putting it in a serialized package, called
     * SerializedMessage, then sends the packaged answer to the transmission protocol, located in the socket-client handler.
     * @see it.polimi.ingsw.server.SocketClientConnection for more details.
     * @param serverAnswer of type Answer the answer to be sent to the user.
     */
    public void send(Answer serverAnswer) {
        SerializedAnswer message = new SerializedAnswer();
        message.setServerAnswer(serverAnswer);
        socketClientConnection.sendSocketMessage(message);
    }

    /**
     * Method win sends the win confirmation to the winner and the lose communication to all the others.
     *
     * @param win of type Answer the message to be sent to the winner.
     */
    public void win(Answer win) {
        SerializedAnswer winner = new SerializedAnswer();
        winner.setServerAnswer(win);
        socketClientConnection.sendSocketMessage(winner);
        gameHandler.sendAllExcept(new LoseMessage(nickname), clientID);
        gameHandler.endGame();
    }


    /**
     * Method sendAll sends the message to all playing clients, thanks to the GameHandler sendAll method. It's triggered
     * from the model's listeners after a player action.
     *
     * @param serverAnswer of type Answer the message to be sent.
     */
    public void sendAll(Answer serverAnswer) {
        gameHandler.sendAll(serverAnswer);
    }

    /**
     * Method propertyChange states the listener property changes, forwarding the message to the correct method.
     *
     * @param evt of type PropertyChangeEvent the property change firing event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getNewValue() instanceof ChallengerMessages) {
            send((ChallengerMessages)evt.getNewValue());
        }
        else if(evt.getNewValue() instanceof CustomMessage){
            send((CustomMessage)evt.getNewValue());
        }
    }
}