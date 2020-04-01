package it.polimi.ingsw.server;

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
import java.util.concurrent.TimeUnit;

/**
 * This class is the main one of the server side. The server allows clients to connect and play together.
 * It also starts the match.
 * @author Luca Pirovano
 * @version 1.0.0
 */
public class Server {
    Date date = new Date();
    private SocketServer socketServer;

    /**
     * This hashmap permits identifying a Virtual Client relying on his client ID, which was set at the join.
     * The client has to be connected to the server.
     */
    private Map<Integer, VirtualClient> IDmapClient;

    /**
     * This hashmap permits finding client ID relying on his unique nickname.
     * The client has to be connected to the server.
     */
    private Map<String, Integer> nameMAPid;

    /**
     * This hashmap permits identifying a Virtual Client relying on his active connection with the server.
     * The client has to be connected to the server.
     */
    private Map<VirtualClient, SocketClientConnection> clientToConnection;

    /**
     * Unique Client ID reference, which is used in the ID generation method.
     */
    private int nextClientID;

    /**
     * The main game class (model).
     */
    private Game game;

    /**
     * List of clients waiting in the lobby.
     */
    private List<SocketClientConnection> waiting = new ArrayList<>();

    /**
     * Number of players, decided by the first connected user.
     */
    private int totalPlayers;

    /**
     * Constructor of the class, it creates the instance of the server, based on a socket and the mapping between VirtualClient,
     * nicknames and client ids. It also creates a new game session.
     */
    public Server() {
        System.out.println(Constants.getInfo() + "Instantiating server class...");
        socketServer = new SocketServer(Constants.PORT, this);
        IDmapClient = new HashMap<>();
        nameMAPid = new HashMap<>();
        clientToConnection = new HashMap<>();
        game = new Game();
        totalPlayers =-1;
    }

    /**
     * @return the active game on the server instance.
     */
    public Game getCurrentGame() {
        return game;
    }

    /**
     * Set the maximum number of player relying on the input provided by the first user who connects; he is also called
     * the "lobby host".
     * @param totalPlayers the number of players provided by the first user connected.
     * @throws OutOfBoundException if the input is not in the correct player range.
     * @see it.polimi.ingsw.constants.Constants for the max/min player parameters.
     */
    protected void setTotalPlayers(int totalPlayers) throws OutOfBoundException {
        if(totalPlayers <Constants.MIN_PLAYERS || totalPlayers >Constants.MAX_PLAYERS) {
            throw new OutOfBoundException();
        }
        else {
            this.totalPlayers = totalPlayers;
        }
    }

    /**
     * Return a link to the desired virtual client, in order to make operations on it (like send, etc).
     * @param ID the id of the virtual client needed.
     * @return the correct virtual client.
     */
    public VirtualClient getClientByID(int ID) {
        return IDmapClient.get(ID);
    }

    /**
     * Creates or handle a lobby, which is a common room used before a match. In this room, connected players are waiting
     * for other ones, in order to reach the correct players number for playing.
     * If the waiting clients queue is empty, the server creates a new lobby and ask the first player to choose the
     * number of playing players. After that, when a client connects, it checks if the players number has been reached;
     * if true, starts the match.
     * @param c a single client connection, which is used for common operations(like sending/receiving commands, etc).
     * @throws InterruptedException if TimeUnit throws it.
     */
    public synchronized void lobby(SocketClientConnection c) throws InterruptedException{
        waiting.add(c);
        if (waiting.size()==1) {
            IDmapClient.get(c.getClientID()).send(new CustomMessage(IDmapClient.get(c.getClientID()).getNickname() + ", you are the lobby host."));
            c.setPlayers(new RequestPlayersNumber());
            broadcast(new CustomMessage((totalPlayers - waiting.size()) + " slots left."));
        }
        else if (waiting.size()== totalPlayers) {
            System.err.println(Constants.getInfo() + "Minimum player number reached. The match is starting.");
            for(int i=5; i>0; i--) {
                broadcast(new CustomMessage("Match starting in " + i));
                TimeUnit.SECONDS.sleep(1);
            }
            broadcast(new CustomMessage("The match has started!"));
        }
        else {
            broadcast(new CustomMessage((totalPlayers - waiting.size()) + " slots left."));
        }
    }

    public void run() {
        while (true) {
        }
    }

    /**
     * Delete a client from the hashmaps and active lists, deregistering his connection with the server.
     * @param clientID the ID of the virtual client to be removed.
     */
    public void unregisterClient(int clientID) {
        VirtualClient client = IDmapClient.get(clientID);
        System.out.println(Constants.getInfo() + "Unregistering client " + client.getNickname() + "...");
        //client.getGameManager().removePlayer(client.getGameManager().getPlayerByNickname(client.getNickname()));
        IDmapClient.remove(clientID);
        nameMAPid.remove(client.getNickname());
        waiting.remove(clientToConnection.get(client));
        clientToConnection.remove(client);
        System.out.println(Constants.getInfo() + "Client has been successfully unregistered.");
        broadcast(new CustomMessage("Client " + client.getNickname() + " left the game.\n" + (totalPlayers - waiting.size()) + " slots left."));
    }

    /**
     * It registers a new connection between the client and the server, by inserting him in the registry hashmaps.
     * If the nickname has already connected, it simply ignores this step and notify the client about this fact.
     * @param nickname the nickname chosen by the client.
     * @param socketClientHandler the active connection between server socket and client socket.
     * @return the client ID if everything goes fine, null otherwise.
     */
    public synchronized Integer registerConnection(String nickname, SocketClientConnection socketClientHandler) {
        Integer clientID = nameMAPid.get(nickname);

        if(clientID==null) {    //Player has never connected to the server before.
            clientID = createClientID();
            VirtualClient client = new VirtualClient(clientID, nickname, socketClientHandler, game);
            if(totalPlayers !=-1 && waiting.size()>= totalPlayers) {
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
            //TODO: reconnection handler after a connection drop
            //client.setSocketClientConnection(socketClientHandler);
        }
        return clientID;
    }

    /**
     *
     * @param clientID
     * @param setup
     * @throws DuplicateNicknameException
     * @throws DuplicateColorException
     */
    public synchronized void setupPlayer(int clientID, Setup setup) throws DuplicateNicknameException, DuplicateColorException {
        VirtualClient client = IDmapClient.get(clientID);
        Game game = client.getGameManager();
        game.createNewPlayer(new Player(client.getNickname(), setup.getColor()));
    }

    /**
     * @return a new client ID for a fresh-connected client. It's based on an attribute which considers the number
     * of people connected to this server since his startup.
     */
    public synchronized int createClientID() {
        int ID = nextClientID;
        nextClientID++;
        return ID;
    }

    /**
     * Transmits a global message to all active clients connected to the server, iterating inside the virtual client
     * hashmap.
     * @param answer the message to transmit.
     */
    public void broadcast(Answer answer) {
        for(int i: IDmapClient.keySet()) {
            IDmapClient.get(i).send(answer);
        }
    }

    /**
     * The main of the server. It simply creates a new server class, adding a server socket to an executor.
     * @param args the main args, like any Java application.
     */
    public static void main(String[] args) {
        System.out.println("Santorini Server | Welcome!");
        System.err.println(Constants.getInfo() + "Starting Socket Server");
        Server server = new Server();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.socketServer);
    }
}
