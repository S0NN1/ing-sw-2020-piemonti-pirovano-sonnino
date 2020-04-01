package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.StartMatch;
import it.polimi.ingsw.server.answers.ConnectionClosed;
import it.polimi.ingsw.server.answers.ConnectionConfirmation;
import it.polimi.ingsw.server.answers.CustomMessage;
import it.polimi.ingsw.server.answers.FullServer;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CLI implements UI, Runnable, Observer {
    private Scanner input;
    private PrintStream output;
    boolean activeGame;
    Model model;

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
            case "START":
                connection.send(new StartMatch());
                break;
            case "QUIT":
                connection.send(new Disconnect());
                output.println("Disconnected from the server.");
                System.exit(0);
        }
    }

    @Override
    public void run() {
        setup();

        while(activeGame) {
            action(input.nextLine());
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
        if(arg.toString().equals("ConnectionConfirmation")) {
            ConnectionConfirmation answer = (ConnectionConfirmation)model.getServerAnswer();
            output.println(answer.getMessage());
        }
        else if(arg.toString().equals("FullServer")) {
            FullServer answer = (FullServer) model.getServerAnswer();
            System.err.println(answer.getMessage() + "\nApplication will now close...");
            System.exit(0);
        }
        else if(arg.toString().equals("CustomMessage")) {
            CustomMessage answer = (CustomMessage)model.getServerAnswer();
            output.println(answer.getMessage());
        }
        else if(arg.toString().equals("ConnectionClosed")) {
            ConnectionClosed answer = (ConnectionClosed)model.getServerAnswer();
            output.println(answer.getMessage());
            System.err.println("Application will now close...");
            System.exit(0);
        }
    }
}
