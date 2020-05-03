package it.polimi.ingsw.server;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.player.PlayerColors;
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
     * This hashmap permits finding client nickname relying on his unique ID.
     * The client has to be connected to the server.
     */
    private Map<Integer, String> idMAPname;

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
     * Active lobby Game handler
     */
    private GameHandler currentGame;

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
        socketServer = new SocketServer(Constants.PORT, this);
        IDmapClient = new HashMap<>();
        nameMAPid = new HashMap<>();
        clientToConnection = new HashMap<>();
        idMAPname = new HashMap<>();
        totalPlayers = -1;
    }

    /**
     * Return the game handler by having the client ID. It's useful for getting the game handler from the socket handler.
     * @param ID the client ID.
     * @return the associated game handler.
     */
    public GameHandler getGameByID(int ID) {
        return IDmapClient.get(ID).getGameHandler();
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
     * Return the user nickname from the hashmap explained above.
     * @param ID the id of the client.
     * @return the nickname of the associated player.
     */
    public String getNicknameByID(int ID) {
        return idMAPname.get(ID);
    }

    public int getIDByNickname(String nickname) { return nameMAPid.get(nickname); }

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
            c.setPlayers(new RequestPlayersNumber(IDmapClient.get(c.getClientID()).getNickname() + ", you are the lobby host.\nChoose the number of players! [2/3]", false));
        }
        else if (waiting.size()== totalPlayers) {
            System.err.println(Constants.getInfo() + "Minimum player number reached. The match is starting.");
            for(int i=3; i>0; i--) {
                currentGame.sendAll(new CustomMessage("Match starting in " + i, false));
                TimeUnit.SECONDS.sleep(1);
            }
            currentGame.sendAll(new CustomMessage("The match has started!", false));
            waiting.clear();
            PlayerColors.reset();
            currentGame.setup();
        }
        else {
            currentGame.sendAll(new CustomMessage((totalPlayers - waiting.size()) + " slots left.", false));
        }
    }

    /**
     * Delete a client from the hashmaps and active lists, deregistering his connection with the server.
     * @param clientID the ID of the virtual client to be removed.
     */
    public synchronized void unregisterClient(int clientID) {
        getGameByID(clientID).unregisterPlayer(clientID);
        VirtualClient client = IDmapClient.get(clientID);
        System.out.println(Constants.getInfo() + "Unregistering client " + client.getNickname() + "...");
        //client.getGameManager().removePlayer(client.getGameManager().getPlayerByNickname(client.getNickname()));
        IDmapClient.remove(clientID);
        nameMAPid.remove(client.getNickname());
        waiting.remove(clientToConnection.get(client));
        idMAPname.remove(client.getClientID());
        clientToConnection.remove(client);
        System.out.println(Constants.getInfo() + "Client has been successfully unregistered.");
        //currentGame.sendAll(new CustomMessage("Client " + client.getNickname() + " left the game.\n" + (totalPlayers - waiting.size()) + " slots left.", false));
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
            if(waiting.size()==0) {
                currentGame = new GameHandler(this);
            }
            if(nameMAPid.keySet().stream().anyMatch(nickname::equalsIgnoreCase)) {
                SerializedAnswer error = new SerializedAnswer();
                error.setServerAnswer(new GameError(ErrorsType.DUPLICATENICKNAME));
                socketClientHandler.sendSocketMessage(error);
                return null;
            }
            clientID = createClientID();
            currentGame.setupPlayer(nickname, clientID);
            VirtualClient client = new VirtualClient(clientID, nickname, socketClientHandler, currentGame);
            if(totalPlayers !=-1 && waiting.size()>= totalPlayers) {
                client.send(new GameError(ErrorsType.FULLSERVER));
                return null;
            }
            IDmapClient.put(clientID, client);
            nameMAPid.put(nickname, clientID);
            idMAPname.put(clientID, nickname);
            clientToConnection.put(client, socketClientHandler);
            System.out.println(Constants.getInfo() + "Client " + client.getNickname() + ", identified by ID " + client.getClientID() + ", has successfully connected!");
            client.send(new ConnectionMessage("Connection was successfully set-up! You are now connected.", 0));
            if(waiting.size()>1) {
                currentGame.sendAll(new CustomMessage("Client " + client.getNickname() + " joined the game", false));
            }
        }
        else {
            VirtualClient client = IDmapClient.get(clientID);
            if(client.isConnected()) {
                SerializedAnswer ans = new SerializedAnswer();
                ans.setServerAnswer(new GameError(ErrorsType.DUPLICATENICKNAME));
                socketClientHandler.sendSocketMessage(ans);
                return null;
            }
        }
        return clientID;
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
        System.out.println(Constants.getInfo() + "Instantiating server class...");
        executor.submit(server.socketServer);
    }
}
