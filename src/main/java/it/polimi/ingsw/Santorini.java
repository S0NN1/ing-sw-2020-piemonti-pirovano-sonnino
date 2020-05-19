package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.server.Server;

public class Santorini {

    public static void main(String[] args){
        switch (args[0].toUpperCase()) {
            case "SERVER" -> Server.main(null);
            case "CLI" -> CLI.main(null); //TODO insert server IP
            case "GUI" -> {
                //TODO insert server IP and launch GUI interface
            }
            default -> System.err.println("Invalid argument, please run the executable again with one of these options:\n1.server\n2.client");
        }
    }
}
