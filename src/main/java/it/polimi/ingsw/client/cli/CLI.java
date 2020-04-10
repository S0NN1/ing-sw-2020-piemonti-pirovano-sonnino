package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.*;

import java.io.PrintStream;
import java.util.*;

public class CLI implements UI, Runnable, Observer {
    private Scanner input;
    private PrintStream output;
    private PrintStream err;
    private boolean activeGame;
    private Model model;

    ConnectionSocket connection;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        model = new Model(this);
        err = new PrintStream(System.err);
        activeGame = true;
    }

    public void toggleActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public void setup() {
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
        try {
            connection.setup(nickname, model);
            output.println("Socket Connection setup completed!");
        } catch (DuplicateNicknameException e) {
            setup();
        }
        model.addObserver(this);
    }

    public synchronized void action() {
        output.print(">");
        String command = input.next();
        model.untoggleInput();
        switch (command.toUpperCase()) {
            case "GODLIST":
                connection.send(new GodSelectionAction("LIST"));
                break;
            case "GODDESC":
                try {
                    connection.send(new GodSelectionAction("DESC", Card.parseInput(input.next())));
                } catch (IllegalArgumentException e){
                    err.println("Not existing god with your input's name.");
                    output.println();
                    action();
                }
                break;
            case "ADDGOD":
                try {
                    connection.send(new GodSelectionAction("ADD", Card.parseInput(input.next())));
                } catch (IllegalArgumentException e){
                    err.println("Not existing god with your input's name.");
                    output.println();
                    action();
                }
                break;
            case "CHOOSE":
                try {
                    connection.send(new GodSelectionAction("CHOOSE", Card.parseInput(input.next())));
                } catch (IllegalArgumentException e){
                    err.println("Not existing god with your input's name.");
                    output.println();
                    action();
                }
                break;
            case "QUIT":
                connection.send(new Disconnect());
                output.println("Disconnected from the server.");
                System.exit(0);
            default:
                err.println("Unknown input, please try again!");
                action();
        }
    }

    public void loop() {
        if(model.getCanInput()) {
            action();
        }
    }

    @Override
    public void run() {
        setup();

        while(activeGame) {
            loop();
        }
        input.close();
        output.close();
    }

    public static void main(String[] args) {
        System.out.println("Hi, welcome to Santorini!");
        CLI cli = new CLI();
        cli.run();
    }

    public void chooseNickname() {
        int selection;
        while(true) {
            try {
                output.print(">");
                selection = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                err.println("Invalid parameter, it must be a number.\nApplication will now quit...");
                System.exit(-1);
            }
        }
        connection.send(new NumberOfPlayers(selection));
    }

    public void chooseColor(ArrayList<PlayerColors> available) {
        while (true) {
            output.println(">Make your choice!");
            output.print(">");
            try {
                PlayerColors color = PlayerColors.parseInput(input.next());
                if(available.contains(color)) {
                    connection.send(new ChosenColor(color));
                    return;
                }
                else {
                    output.println("Color not available!");
                }
            }catch (IllegalArgumentException e) {
                output.println("Invalid input! Please provide one of the accepted colors.");
            }
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        String value = arg.toString();
        switch (value) {
            case "RequestPlayerNumber":
                output.println(((RequestPlayersNumber)model.getServerAnswer()).getMessage());
                chooseNickname();
                break;
            case "RequestColor":
                output.println(((RequestColor)model.getServerAnswer()).getMessage() + "\nRemaining:");
                ((RequestColor)model.getServerAnswer()).getRemaining().forEach(output::println);
                chooseColor(((RequestColor)model.getServerAnswer()).getRemaining());
                break;
            case "GodRequest":
                GodRequest req = (GodRequest)model.getServerAnswer();
                if (req.godList!=null) {
                    req.godList.forEach(n -> output.print(n + ", "));
                    output.println();
                }
                else {
                    output.println(req.message);
                }
                model.toggleInput();
                break;
            case "CustomMessage":
                output.println(((CustomMessage)model.getServerAnswer()).getMessage());
                model.untoggleInput();
                break;
            case "ConnectionClosed":
                output.println(((ConnectionClosed)model.getServerAnswer()).getMessage());
                err.println("Application will now close...");
                System.exit(0);
        }
    }
}