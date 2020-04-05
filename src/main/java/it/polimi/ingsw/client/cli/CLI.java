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
            case "GODLIST":
                connection.send(new GodSelectionAction("LIST"));
                canInput=false;
                break;
            case "GODDESC":
                connection.send(new GodSelectionAction("DESC", Card.parseInput(input.next())));
                canInput=false;
                break;
            case "QUIT":
                connection.send(new Disconnect());
                output.println("Disconnected from the server.");
                System.exit(0);
            default:
                output.println("Unknown input, please try again!");
                String cmd = input.next();
                action(cmd);
        }
    }

    @Override
    public void run() {
        setup();

        while(activeGame) {
            /*if (canInput) {
                action(input.nextLine());
            }*/
        }
        input.close();
        output.close();
    }

    public static void main(String[] args) {
        System.out.println("Hi, welcome to Santorini!");
        CLI cli = new CLI();
        cli.run();
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
    public void update(Observable o, Object arg) {
        String value = arg.toString();
        switch (value) {
            case "RequestPlayerNumber":
                output.println(((RequestPlayersNumber)model.getServerAnswer()).getMessage());
                action("PlayerNumber");
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
                    output.print(">");
                    String cmd = input.next();
                    action(cmd);
                }
                else {
                    output.println(req.message);
                    output.print(">");
                    String cmd = input.next();
                    action(cmd);
                }
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
