package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.Setup;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.answers.ConnectionConfirmation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private SocketServer socketServer;
    private Map<Integer, VirtualClient> IDmapClient;
    private Map<String, Integer> nameMAPid;
    private int nextClientID;
    private Game game;

    public Server() {
        socketServer = new SocketServer(Constants.PORT, this);
        IDmapClient = new HashMap<>();
        nameMAPid = new HashMap<>();
        game = new Game();
    }

    public Game getCurrentGame() {
        return game;
    }

    public void lobby(ClientConnection c, String name) {

    }

    public void run() {
        while (true) {

        }
    }

    public void unregisterClient(int clientID) {
        VirtualClient client = IDmapClient.get(clientID);
        System.out.println("Unregistering client " + client.getNickname() + "...");
        client.getGameManager().removePlayer(client.getGameManager().getPlayerByNickname(client.getNickname()));
        IDmapClient.remove(clientID);
        nameMAPid.remove(client.getNickname());
    }

    public synchronized Integer registerConnection(String nickname, SocketClientConnection socketClientHandler) {
        Integer clientID = nameMAPid.get(nickname);

        if(clientID==null) {    //Player has never connected to the server before.
            clientID = newClientID();
            VirtualClient client = new VirtualClient(clientID, nickname, socketClientHandler, game);

            IDmapClient.put(clientID, client);
            nameMAPid.put(nickname, clientID);
            System.out.println("Client " + client.getNickname() + ", identified by ID " + client.getClientID() + ", has successfully connected!");
            client.send(new ConnectionConfirmation());
        }
        else {
            VirtualClient client = IDmapClient.get(clientID);
            if(client.isConnected()) {
                return null;
            }
            //client.setSocketClientConnection(socketClientHandler);
        }
        return clientID;
    }

    public synchronized void setupPlayer(int clientID, Setup setup) throws DuplicateNicknameException, DuplicateColorException {
        VirtualClient client = IDmapClient.get(clientID);
        Game game = client.getGameManager();
        game.createNewPlayer(new Player(client.getNickname(), setup.getColor()));
    }

    public synchronized int newClientID() {
        int ID = nextClientID;
        nextClientID++;
        return ID;
    }

    public static void main(String[] args) {
        Server server = new Server();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.socketServer);
    }
}
