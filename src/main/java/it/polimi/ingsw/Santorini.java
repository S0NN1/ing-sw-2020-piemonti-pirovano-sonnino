package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class Santorini {

    public static void main(String[] args){
        switch (args[0].toUpperCase()) {
            case "SERVER":
                Server.main(null);
                break;
            case "CLI":
                //TODO insert server IP
                CLI.main(null);
                break;
            case "GUI":
                //TODO insert server IP
                //TODO
            default:
                System.err.println("Invalid argument, please run the executable again with one of these options:\n1.server\n2.client");
        }
    }
}
