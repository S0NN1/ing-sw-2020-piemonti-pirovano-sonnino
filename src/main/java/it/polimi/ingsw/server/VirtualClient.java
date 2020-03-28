package it.polimi.ingsw.server;

public class AbstractClient {
    private int clientID;
    private String nickname;
    private SocketClientConnection socketClientConnection;

    public AbstractClient(int clientID, String nickname, SocketClientConnection socketClientConnection) {
        this.nickname = nickname;
        this.clientID = clientID;
        this.socketClientConnection = socketClientConnection;
    }

    public int getClientID() {
        return clientID;
    }
}
