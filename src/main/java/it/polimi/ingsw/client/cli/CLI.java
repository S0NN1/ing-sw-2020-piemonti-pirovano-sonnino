package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.server.answers.*;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CLI implements UI, Runnable, Observer {
    private Scanner input;
    private PrintStream output;
    private boolean activeGame;
    private boolean canInput;
    private Model model;

    ConnectionSocket connection;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        model = new Model(this);
        activeGame = true;
    }

    public void toggleActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public void setup() {
        output.println("Hi, welcome to Santorini!");
        String nickname=null;
        boolean confirmation = false;
        while (confirmation==false) {
            do {
                output.println(">Insert your nickname: ");
                output.print(">");
                nickname = input.next();
            } while (nickname == null);

            output.println(">You chose: " + nickname);
            output.println(">Is it ok? [y/n] ");
            output.print(">");
            if(input.next().equalsIgnoreCase("y")) {
                confirmation=true;
            }
            else {
                nickname=null;
            }
        }
        connection = new ConnectionSocket();
        connection.setup(nickname, model);
        output.println("Socket Connection setup completed!");
        model.addObserver(this);

    }

    public void action(String command) {
        switch (command.toUpperCase()) {
            case "PLAYERNUMBER":
                int selection;
                while(true) {
                    try {
                        output.print(">");
                        selection = input.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("Invalid parameter, it must be a number.\nApplication will now quit...");
                        System.exit(-1);
                    }
                }
                connection.send(new NumberOfPlayers(selection));
                break;
            case "QUIT":
                connection.send(new Disconnect());
                output.println("Disconnected from the server.");
                System.exit(0);
        }
        canInput = false;
    }

    @Override
    public void run() {
        setup();

        while(activeGame) {
            if (canInput) {
                action(input.nextLine());
            }
        }
        input.close();
        output.close();
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.run();
    }

    @Override
    public void update(Observable o, Object arg) {
        String value = arg.toString();
        switch (value) {
            case "RequestPlayerNumber":
                output.println(((RequestPlayersNumber)model.getServerAnswer()).getMessage());
                action("PlayerNumber");
                break;
            case "CustomMessage":
                output.println(((CustomMessage)model.getServerAnswer()).getMessage());
                break;
            case "ConnectionClosed":
                output.println(((ConnectionClosed)model.getServerAnswer()).getMessage());
                System.err.println("Application will now close...");
                System.exit(0);
        }
    }
}
