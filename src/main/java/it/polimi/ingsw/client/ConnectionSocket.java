package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.SerializedMessage;
import it.polimi.ingsw.client.messages.SetupConnection;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.server.answers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
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

    public void setup(String nickname, Model model) throws DuplicateNicknameException{
        try {
            System.out.println("Configuring socket connection...");
            System.out.println("Opening a socket server communication on port " + serverPort + "...");
            this.socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            while(true) {
                try {
                    send(new SetupConnection(nickname));
                    SerializedAnswer answer = (SerializedAnswer) input.readObject();
                    if (answer.getServerAnswer() instanceof ConnectionConfirmation) {
                        break;
                    }
                    else if(answer.getServerAnswer() instanceof GameError) {
                        if(((GameError) answer.getServerAnswer()).getError().equals(ErrorsType.DUPLICATENICKNAME)) {
                            System.err.println("This nickname is already in use! Please choose one other.");
                            throw new DuplicateNicknameException();
                        }
                    }
                    else if (answer.getServerAnswer() instanceof FullServer) {
                        System.err.println(((FullServer)answer.getServerAnswer()).getMessage() + "\nApplication will now close...");
                        System.exit(0);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                    return;
                }
            }
            listener = new SocketListener(socket, this, model, input);
            Thread thread = new Thread(listener);
            thread.start();
        }
        catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void send(Message message) {
        SerializedMessage output = new SerializedMessage(message);
        try {
            outputStream.reset();
            outputStream.writeObject(output);
            outputStream.flush();
        }
        catch (IOException e) {
            System.err.println("Error during send process.");
            e.printStackTrace();
        }
    }

    public void send(UserAction message) {
        SerializedMessage output = new SerializedMessage(message);
        try {
            outputStream.reset();
            outputStream.writeObject(output);
            outputStream.flush();
        }
        catch (IOException e) {
            System.err.println("Error during send process.");
            e.printStackTrace();
        }
    }

}
