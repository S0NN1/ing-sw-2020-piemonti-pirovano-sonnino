package it.polimi.ingsw.server;

import it.polimi.ingsw.constants.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable{
    private int port;
    private ExecutorService executorService;
    private Server server;

    public SocketServer(int port, Server server) {
        this.server = server;
        this.port = port;
        executorService = Executors.newCachedThreadPool();
    }

    public void acceptConnections(ServerSocket serverSocket) {
        while (true) {
            try {
                SocketClientConnection socketClient = new SocketClientConnection(serverSocket.accept(), server);
                executorService.submit(socketClient);
            }
            catch (IOException e) {
                System.err.println("Error! " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(Constants.getInfo() + "Socket Server started; listening on port " + port);
            acceptConnections(serverSocket);
        }
        catch (IOException e) {
            System.err.println(Constants.getErr() + "Error during Socket initialization, quitting...");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
