package it.polimi.ingsw.client;

import it.polimi.ingsw.server.answers.SerializedAnswer;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listens for a server answer on the socket, passing it to the client model class.
 * @author Luca Pirovano
 */
public class SocketListener implements Runnable{

    private Socket socket;
    private ConnectionSocket connectionSocket;
    private final ModelView modelView;
    private ActionHandler actionHandler;
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
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
                if(modelView.getCli()!=null && !modelView.getCli().isActiveGame()) {
                    break;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Connection closed by the server. Quitting...");
            if(modelView.getGui()!=null) {
                modelView.getGui().propertyChange(new PropertyChangeEvent(this, "connectionClosed", null, modelView.getServerAnswer().getMessage()));
            } else {
                System.exit(0);
            }
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
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
