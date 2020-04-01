package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.*;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.RequestPlayersNumber;
import it.polimi.ingsw.server.answers.SerializedMessage;
import it.polimi.ingsw.server.answers.WaitMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection implements ClientConnection, Runnable {
    private Socket socket;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Integer clientID;
    private boolean active;

    public synchronized boolean isActive() {
        return active;
    }

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

    public Socket getSocket() {
        return socket;
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            server.unregisterClient(this.getClientID());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public synchronized void readFromStream() throws IOException, ClassNotFoundException {
        Message command = (Message) inputStream.readObject();
        actionHandler(command);
    }

    @Override
    public void run() {
    try {
        while (isActive()) {
            readFromStream();
        }
    }
    catch (IOException e) {
        System.err.println("LMAO " + e.getMessage());
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    close();
    }

    public void actionHandler(Message command) {
        if(command instanceof SetupConnection) {
            try {
                SetupConnection cmd = (SetupConnection) command;
                clientID = server.registerConnection(cmd.getNickname(), this);
                if (clientID == null) {
                    active = false;
                    return;
                }
                server.lobby(this);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        else if(command instanceof Setup) {
            try {
                server.setupPlayer(clientID, (Setup) command);
            }
            catch (DuplicateNicknameException | DuplicateColorException e) {
                System.err.println(e.getMessage());
            }
        }
        else if(command instanceof Disconnect) {
            server.unregisterClient(clientID);
        }
    }

    public void setPlayers(RequestPlayersNumber message) {
        SerializedMessage ans = new SerializedMessage();
        ans.setServerAnswer(message);
        sendSocketMessage(ans);
        while(true) {
            try {
                Message command = (Message) inputStream.readObject();
                if (command instanceof NumberOfPlayers) {
                    try {
                        int playerNumber = (((NumberOfPlayers) command).playersNumber);
                        server.setMaxPlayers(playerNumber);
                        server.getClientByID(this.clientID).send(new CustomMessage("Success: player number set to " + playerNumber));
                        break;
                    } catch (OutOfBoundException e) {
                        server.getClientByID(this.clientID).send(new CustomMessage("Error: not a valid input! Please provide a value of 2 or 3."));
                        server.getClientByID(this.clientID).send(new RequestPlayersNumber());
                    }
                }
                else {
                    ans.setServerAnswer(new WaitMessage());
                }
            } catch (ClassNotFoundException | IOException e) {
                close();
                System.err.println(e.getMessage());
            }
        }
    }

    public void sendSocketMessage(SerializedMessage serverAnswer) {
        try {
            outputStream.reset();
            outputStream.writeObject(serverAnswer);
            //System.out.println("Message sent!");
            outputStream.flush();
        }
        catch (IOException e) {

            e.printStackTrace();
            //TODO: Disconnect Client
        }
    }

    public Integer getClientID() {
        return clientID;
    }
}
