package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.server.answers.ConnectionConfirmation;
import it.polimi.ingsw.server.answers.KekMessage;

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
        model = new Model();
        activeGame = true;
    }

    public void setup() {
        output.println("Hi, welcome to Santorini!");
        String nickname=null;
        boolean confirmation = false;
        while (confirmation==false) {
            do {
                output.print(">Insert your nickname: ");
                nickname = input.nextLine();
            } while (nickname == null);

            output.println("You chose: " + nickname);
            output.print("Is it ok? [y/n]");
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

    public void action() {

    }

    @Override
    public void run() {
        setup();

        while(activeGame) {
            action();
        }

    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.run();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.toString().equalsIgnoreCase("ConnectionConfirmation")) {
            ConnectionConfirmation answer = (ConnectionConfirmation)model.getServerAnswer();
            output.println(answer.getMessage());
        }
    }
}
