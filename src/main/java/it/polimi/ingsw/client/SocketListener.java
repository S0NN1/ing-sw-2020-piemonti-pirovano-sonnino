package it.polimi.ingsw.client;

import it.polimi.ingsw.server.answers.SerializedAnswer;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Method SocketListeners listens for a server answer on the socket, passing it to the client model class.
 * @author Luca Pirovano
 * @see Runnable
 */
public class SocketListener implements Runnable{

    private final Socket socket;
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ObjectInputStream inputStream;

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket of type Socket
     * @param modelView of type ModelView
     * @param inputStream of type ObjectInputStream
     * @param actionHandler of type ActionHandler
     */
    public SocketListener(Socket socket, ModelView modelView, ObjectInputStream inputStream, ActionHandler
            actionHandler) {
        this.modelView = modelView;
        this.socket = socket;
        this.inputStream = inputStream;
        this.actionHandler = actionHandler;
    }

    /**
     * Method process processes the serialized answer received from the server, passing it to the answer handler.
     * @param serverMessage of type SerializedAnswer the serialized answer.
     */
    public void process(SerializedAnswer serverMessage) {
        modelView.setServerAnswer(serverMessage.getServerAnswer());
        actionHandler.answerHandler();
    }

    /**
     * Method run loops and sends messages..
     */
    @Override
    public void run() {
        try {
            do {
                SerializedAnswer message = (SerializedAnswer) inputStream.readObject();
                process(message);
            } while (modelView.getCli() == null || modelView.getCli().isActiveGame());
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Connection closed by the server. Quitting...");
            if(modelView.getGui()!=null) {
                modelView.getGui().propertyChange(new PropertyChangeEvent(this, "connectionClosed",
                        null, modelView.getServerAnswer().getMessage()));
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
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
