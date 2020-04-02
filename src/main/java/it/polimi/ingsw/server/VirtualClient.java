package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.SerializedMessage;

public class VirtualClient {
    private int clientID;
    private String nickname;
    private SocketClientConnection socketClientConnection;
    private Game gameManager;

    public VirtualClient(int clientID, String nickname, SocketClientConnection socketClientConnection, Game gameManager) {
        this.nickname = nickname;
        this.clientID = clientID;
        this.socketClientConnection = socketClientConnection;
        this.gameManager = gameManager;
    }

    public int getClientID() {
        return clientID;
    }

    public boolean isConnected() {
        return(!(socketClientConnection==null));
    }

    public void setSocketClientConnection(SocketClientConnection clientConnection) {
        this.socketClientConnection = clientConnection;
    }

    public Game getGameManager() {
        return gameManager;
    }

    public String getNickname() {
        return nickname;
    }

    public void send(Answer serverAnswer) {
        SerializedMessage message = new SerializedMessage();
        message.setServerAnswer(serverAnswer);
        socketClientConnection.sendSocketMessage(message);
    }

}