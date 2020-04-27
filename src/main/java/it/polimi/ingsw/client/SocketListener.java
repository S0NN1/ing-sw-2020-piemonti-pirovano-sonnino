package it.polimi.ingsw.client;

import it.polimi.ingsw.server.answers.SerializedAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Listens for a server answer on the socket, passing it to the client model class.
 * @author Luca Pirovano
 */
public class SocketListener implements Runnable{

    private Socket socket;
    private ConnectionSocket connectionSocket;
    private final ModelView modelView;
    private ActionHandler actionHandler;

    private ObjectInputStream inputStream;

    public SocketListener(Socket socket, ConnectionSocket connectionSocket, ModelView modelView, ObjectInputStream inputStream, ActionHandler actionHandler) {
        this.modelView = modelView;
        this.connectionSocket = connectionSocket;
        this.socket = socket;
        this.inputStream = inputStream;
        this.actionHandler = actionHandler;
    }

    /**
     * Processes the serialized answer received from the server, passing it to the answer handler.
     * @see ModelView for more information.
     * @param serverMessage the serialized answer.
     */
    public void process(SerializedAnswer serverMessage) {
        modelView.setServerAnswer(serverMessage.getServerAnswer());
        actionHandler.answerHandler();
    }

    @Override
    public void run() {
        try {
            while (true) {
                SerializedAnswer message = (SerializedAnswer) inputStream.readObject();
                process(message);
            }
        }
        catch (IOException e) {
            System.err.println("Connection closed by the server. Quitting...");
            //e.printStackTrace();
            System.exit(0);
        }
        catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                inputStream.close();
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
