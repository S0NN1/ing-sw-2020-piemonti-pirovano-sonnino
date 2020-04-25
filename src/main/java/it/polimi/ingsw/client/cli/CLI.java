package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.*;

/**
 * Main CLI client class; it manages the game if the player decides to play with Command Line Interface.
 * @author Luca Pirovano
 * @version 1.0.0
 */
public class CLI implements UI, Runnable, PropertyChangeListener {
    private final Scanner input;
    private final PrintStream output;
    private final PrintStream err;
    private boolean activeGame;
    private final Model model;
    private ConnectionSocket connection;
    private final PropertyChangeSupport observers = new PropertyChangeSupport(this);

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        model = new Model(this);
        err = new PrintStream(System.err);
        activeGame = true;
    }

    /**
     * Change the value of the parameter activeGame, which states if the game is active or if it has finished.
     * @param activeGame a true or false value based on the status of the game.
     */
    public void toggleActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    /**
     * Initial setup method, which is called when a client instance has started. It asks player's nickname and tries to
     * establish a connection to the remote server through the socket interface. If the connection is active, displays
     * a message on the CLI.
     */
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
            output.println(Constants.ANSI_GREEN + "Socket Connection setup completed!" + Constants.ANSI_RESET);
        } catch (DuplicateNicknameException e) {
            setup();
        }
        observers.addPropertyChangeListener(new ActionParser(connection, model));
    }



    /**
     * The main execution loop of the client side. If the input has toggled (through the apposite method) it calls the
     * action one and parses the player's input.
     */
    public void loop() {
        if(model.getCanInput()) {
               System.out.print(">");
               String cmd = input.nextLine();
               observers.firePropertyChange("action", null, cmd);
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

    /**
     * This method lets the first-connected user to decides the match capacity.
     * Terminates the client if the player inserts an incorrect type of input.
     */
    public void choosePlayerNumber() {
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

    /**
     * Lets the player decide his color, relying on the available ones. If the player is the last in a three-players
     * match, the server automatically assign him the last color.
     * @param available the list of available colors, which will be printed out.
     */
    public void chooseColor(ArrayList<PlayerColors> available) {
        input.nextLine();
        while (true) {
            output.println(">Make your choice!");
            output.print(">");
            try {
                PlayerColors color = PlayerColors.parseInput(input.nextLine());
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

    public void chooseStartingPlayer(int len) {
        output.print("> ");
        String starting = input.nextLine();
        int startingPlayer = Integer.parseInt(starting);
        if(0<startingPlayer || startingPlayer<len-1) {
            connection.send(new ChallengerPhaseAction(startingPlayer));
        }
        else {
            err.println("Error: invalid selection!");
            chooseStartingPlayer(len);
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String value = evt.getNewValue().toString();
        switch (value) {
            case "RequestPlayerNumber":
                output.println(Constants.ANSI_GREEN + ((RequestPlayersNumber)model.getServerAnswer()).getMessage() + Constants.ANSI_RESET);
                choosePlayerNumber();
                break;
            case "RequestColor":
                output.println(Constants.ANSI_GREEN + ((RequestColor)model.getServerAnswer()).getMessage() + "\nRemaining:" + Constants.ANSI_RESET);
                ((RequestColor)model.getServerAnswer()).getRemaining().forEach(n -> output.print(n + ", "));
                output.print("\n");
                chooseColor(((RequestColor)model.getServerAnswer()).getRemaining());
                break;
            case "GodRequest":
                ChallengerMessages req = (ChallengerMessages)model.getServerAnswer();
                if(req.startingPlayer && req.players!=null) {
                    output.println(req.message);
                    req.players.forEach(n -> output.println(req.players.indexOf(n) + ": " + n + ","));
                    chooseStartingPlayer(req.players.size());
                    return;
                }
                else if (req.godList!=null) {
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
                break;
            case "ConnectionClosed":
                output.println(((ConnectionMessage)model.getServerAnswer()).getMessage());
                err.println("Application will now close...");
                System.exit(0);
        }
    }

    /**
     * The main class of CLI client. It instantiates a new CLI class, running it.
     * @param args the standard java main parameters.
     */
    public static void main(String[] args) {
        System.out.println("Hi, welcome to Santorini!");
        CLI cli = new CLI();
        cli.run();
    }

}