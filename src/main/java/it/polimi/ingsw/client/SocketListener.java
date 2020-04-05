package it.polimi.ingsw.client;

import it.polimi.ingsw.server.answers.SerializedAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketListener implements Runnable{

    private Socket socket;
    private ConnectionSocket connectionSocket;
    private Model model;

    private ObjectInputStream inputStream;

    public SocketListener(Socket socket, ConnectionSocket connectionSocket, Model model, ObjectInputStream inputStream) {
        this.model = model;
        this.connectionSocket = connectionSocket;
        this.socket = socket;
        this.inputStream = inputStream;
    }

    public void process(SerializedAnswer serverMessage) {
        model.answerHandler(serverMessage.getServerAnswer());
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
