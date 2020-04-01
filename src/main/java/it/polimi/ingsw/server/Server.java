package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.Setup;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.answers.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    Date date = new Date();
    private SocketServer socketServer;
    private Map<Integer, VirtualClient> IDmapClient;
    private Map<String, Integer> nameMAPid;
    private Map<VirtualClient, SocketClientConnection> clientToConnection;
    private int nextClientID;
    private Game game;
    private List<SocketClientConnection> waiting = new ArrayList<>();
    private int maxPlayers;
    private Server server;

    public Server() {
        System.out.println(Constants.getInfo() + "Instantiating server class...");
        socketServer = new SocketServer(Constants.PORT, this);
        IDmapClient = new HashMap<>();
        nameMAPid = new HashMap<>();
        clientToConnection = new HashMap<>();
        game = new Game();
        maxPlayers=-1;
        server = this;
    }

    public Game getCurrentGame() {
        return game;
    }

    protected void setMaxPlayers(int maxPlayers) throws OutOfBoundException {
        if(maxPlayers<Constants.MIN_PLAYERS || maxPlayers>Constants.MAX_PLAYERS) {
            throw new OutOfBoundException();
        }
        else {
            this.maxPlayers = maxPlayers;
        }
    }

    public VirtualClient getClientByID(int ID) {
        return IDmapClient.get(ID);
    }

    public synchronized void lobby(SocketClientConnection c) throws InterruptedException{
        waiting.add(c);
        if (waiting.size()==1) {
            IDmapClient.get(c.getClientID()).send(new CustomMessage(IDmapClient.get(c.getClientID()).getNickname() + ", you are the lobby host."));
            c.setPlayers(new RequestPlayersNumber());
            broadcast(new CustomMessage((maxPlayers - waiting.size()) + " slots left."));
        }
        else if (waiting.size()==maxPlayers) {
            System.err.println(Constants.getInfo() + "Minimum player number reached. The match has started.");
            broadcast(new CustomMessage("The match has started!"));
        }
        else {
            broadcast(new CustomMessage((maxPlayers - waiting.size()) + " slots left."));
        }
    }

    public void run() {
        while (true) {
        }
    }

    public void unregisterClient(int clientID) {
        VirtualClient client = IDmapClient.get(clientID);
        System.out.println(Constants.getInfo() + "Unregistering client " + client.getNickname() + "...");
        //client.getGameManager().removePlayer(client.getGameManager().getPlayerByNickname(client.getNickname()));
        IDmapClient.remove(clientID);
        nameMAPid.remove(client.getNickname());
        waiting.remove(clientToConnection.get(client));
        clientToConnection.remove(client);
        System.out.println(Constants.getInfo() + "Client has been successfully unregistered.");
        broadcast(new CustomMessage("Client " + client.getNickname() + " left the game.\n" + (maxPlayers - waiting.size()) + " slots left."));
    }

    public synchronized Integer registerConnection(String nickname, SocketClientConnection socketClientHandler) {
        Integer clientID = nameMAPid.get(nickname);

        if(clientID==null) {    //Player has never connected to the server before.
            clientID = createClientID();
            VirtualClient client = new VirtualClient(clientID, nickname, socketClientHandler, game);
            if(maxPlayers!=-1 && waiting.size()>=maxPlayers) {
                client.send(new FullServer());
                return null;
            }
            IDmapClient.put(clientID, client);
            nameMAPid.put(nickname, clientID);
            clientToConnection.put(client, socketClientHandler);
            System.out.println(Constants.getInfo() + "Client " + client.getNickname() + ", identified by ID " + client.getClientID() + ", has successfully connected!");
            client.send(new ConnectionConfirmation());
            broadcast(new CustomMessage("Client " + client.getNickname() + " joined the game"));
        }
        else {
            VirtualClient client = IDmapClient.get(clientID);
            if(client.isConnected()) {
                SerializedMessage ans = new SerializedMessage();
                ans.setServerAnswer(new ConnectionClosed("A client with the name " + client.getNickname() + " has already connected!"));
                socketClientHandler.sendSocketMessage(ans);
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

    public synchronized int createClientID() {
        int ID = nextClientID;
        nextClientID++;
        return ID;
    }

    public void broadcast(Answer answer) {
        for(int i: IDmapClient.keySet()) {
            IDmapClient.get(i).send(answer);
        }
    }

    public static void main(String[] args) {
        System.out.println("Santorini Server | Welcome!");
        System.err.println(Constants.getInfo() + "Starting Socket Server");
        Server server = new Server();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.socketServer);
    }
}
