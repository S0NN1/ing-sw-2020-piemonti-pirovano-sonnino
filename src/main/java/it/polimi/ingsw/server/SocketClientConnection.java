package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.*;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateColorException;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.server.answers.SerializedMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

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

    @Override
    public void run() {
    try {
        while (isActive()) {
            Message command = (Message) inputStream.readObject();
            actionHandler(command);
        }
    }
    catch (IOException e) {
        System.err.println(e.getMessage());
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    finally {
        close();
    }
    }

    public void actionHandler(Message command) {
        if(command instanceof SetupConnection) {
            SetupConnection cmd = (SetupConnection) command;
            clientID = server.registerConnection(cmd.getNickname(), this);
            if (clientID == null) {
                active = false;
                return;
            }
            server.lobby(this, false);
        }
        else if(command instanceof StartMatch) {
            server.lobby(this, true);
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
