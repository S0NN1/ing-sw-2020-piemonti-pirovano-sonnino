package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.SetupConnection;
import it.polimi.ingsw.constants.Constants;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionSocket {
    private Socket socket;
    private int clientID;

    private ObjectOutputStream outputStream;
    SocketListener listener;

    private String serverAddress;
    private int serverPort;

    public ConnectionSocket() {
        this.serverAddress = Constants.ADDRESS;
        this.serverPort = Constants.PORT;
    }

    public void setup(String nickname, Model model) {
        try {
            System.out.println("Configuring socket connection...");
            System.out.println("Opening a socket server communication on port " + serverPort + "...");
            this.socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            listener = new SocketListener(socket, this, model);
            Thread thread = new Thread(listener);
            thread.start();

            send(new SetupConnection(nickname));
        }
        catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void send(Message message) {
        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        }
        catch (IOException e) {
            System.err.println("Error during send process.");
            e.printStackTrace();
        }
    }

}
