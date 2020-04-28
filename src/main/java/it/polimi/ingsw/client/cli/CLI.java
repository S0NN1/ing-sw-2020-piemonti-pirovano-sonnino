package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.ChallengerMessages;
import it.polimi.ingsw.server.answers.GameError;
import it.polimi.ingsw.server.answers.RequestColor;
import it.polimi.ingsw.server.answers.RequestPlayersNumber;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main CLI client class; it manages the game if the player decides to play with Command Line Interface.
 *
 * @author Luca Pirovano, Nicolò Sonnino
 * @version 1.0.0
 */
public class CLI implements UI, Runnable {
    public final String green = Constants.ANSI_GREEN;
    public final String yellow = Constants.ANSI_YELLOW;
    private final String red = Constants.ANSI_RED;
    private final String rst = Constants.ANSI_RESET;
    private final PrintStream output;
    private final Scanner input;
    private final PrintStream err;
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final PropertyChangeSupport observers = new PropertyChangeSupport(this);
    private final DisplayCell[][] grid;
    private Printable printable;
    private boolean activeGame;
    private ConnectionSocket connection;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        err = new PrintStream(System.err);
        activeGame = true;
        grid = new DisplayCell[5][5];
    }

    /**
     * The main class of CLI client. It instantiates a new CLI class, running it.
     *
     * @param args the standard java main parameters.
     */
    public static void main(String[] args) {
        System.out.println("Hi, welcome to Santorini!");
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * Change the value of the parameter activeGame, which states if the game is active or if it has finished.
     *
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
        String nickname = null;
        boolean confirmation = false;
        while (confirmation == false) {
            do {
                output.println(">Insert your nickname: ");
                output.print(">");
                nickname = input.next();
            } while (nickname == null);

            output.println(">You chose: " + nickname);
            output.println(">Is it ok? [y/n] ");
            output.print(">");
            if (input.next().equalsIgnoreCase("y")) {
                confirmation = true;
            } else {
                nickname = null;
            }
        }
        connection = new ConnectionSocket();
        try {
            connection.setup(nickname, modelView, actionHandler);
            output.println(green + "Socket Connection setup completed!" + rst);
        } catch (DuplicateNicknameException e) {
            setup();
        }
        observers.addPropertyChangeListener(new ActionParser(connection, modelView));
    }

    /**
     * The main execution loop of the client side. If the input has toggled (through the apposite method) it calls the
     * action one and parses the player's input.
     */
    public void loop() {
        input.reset();
        String cmd = input.nextLine();
        observers.firePropertyChange("action", null, cmd);
    }

    @Override
    public void run() {
        setup();
        while (activeGame) {
            //TODO match input enabler confirmation (from server)
            if (modelView.getStarted() == 3) {
                loop();
            }
        }
        input.close();
        output.close();
    }

    private void firstBuildBoard(DisplayCell[][] grid) {
        int i = 0;
        int j = 0;
        for (i = 0; i <= 4; i++) {
            for (j = 0; j <= 4; j++) {
                String[] rows = printable.lvl0.split("\n");
                String[] fifthRow = rows[4].split("u");
                String[] sixthRow = rows[5].split("u");
                grid[i][j].setfirstCellRow(rows[0]);
                grid[i][j].setsecondCellRow(rows[1]);
                grid[i][j].setthirdCellRow(rows[2]);
                grid[i][j].setfourthCellRow(rows[3]);
                grid[i][j].setfifthCellRow(fifthRow[0] + "█" + fifthRow[1]);
                grid[i][j].setsixthCellRow(sixthRow[0] + "█" + sixthRow[1]);
                grid[i][j].setseventhCellRow(rows[6]);
                grid[i][j].seteighthCellRow(rows[7]);
                grid[i][j].setninthCellRow(rows[8]);
                grid[i][j].settenthCellRow(rows[9]);
                grid[i][j].seteleventhCellRow(rows[10]);
            }
            j = 0;
        }
    }

    private void  boardUpdater(DisplayCell[][] grid){
    int i=0;
    int j=0;
    }

    private void printBoard(DisplayCell[][] grid) {
        int i = 0;
        int j = 0;
        System.out.println(printable.rowWave);
        System.out.println(printable.rowWave);
        System.out.println(printable.coupleRowWave + yellow + printable.lineBlock + rst + printable.coupleRowWave);
        for (i = 0; i <= 4; i++) {
            System.out.print(printable.coupleRowWave + yellow + "█" + rst);
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getfirstCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getsecondCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getthirdCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getfourthCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getfifthCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getsixthCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getseventhCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].geteighthCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].getninthCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].gettenthCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");

            for (j = 0; j <= 4; j++) {
                System.out.print(grid[i][j].geteleventhCellRow() + yellow + "█" + rst);
            }
            j = 0;
            System.out.print(printable.coupleRowWave + "\n");
        }
        System.out.println(printable.coupleRowWave + yellow + printable.lineBlock + rst + printable.coupleRowWave);
        System.out.println(printable.rowWave);
        System.out.println(printable.rowWave);
    }


    /**
     * This method lets the first-connected user to decides the match capacity.
     * Terminates the client if the player inserts an incorrect type of input.
     */
    public void choosePlayerNumber() {
        int selection;
        while (true) {
            try {
                output.print(">");
                selection = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                output.println(red + "Invalid parameter, it must be a number.\nApplication will now quit..." + rst);
                System.exit(-1);
            }
        }
        connection.send(new NumberOfPlayers(selection));
        modelView.setStarted(1);
    }

    /**
     * Lets the player decide his color, relying on the available ones. If the player is the last in a three-players
     * match, the server automatically assign him the last color.
     *
     * @param available the list of available colors, which will be printed out.
     */
    public void chooseColor(ArrayList<PlayerColors> available) {
        input.nextLine();
        while (true) {
            output.println(">Make your choice!");
            output.print(">");
            try {
                PlayerColors color = PlayerColors.parseInput(input.nextLine());
                if (available.contains(color)) {
                    connection.send(new ChosenColor(color));
                    modelView.setStarted(2);
                    return;
                } else {
                    output.println("Color not available!");
                }
            } catch (IllegalArgumentException e) {
                output.println("Invalid input! Please provide one of the accepted colors.");
            }
        }
    }

    public void chooseStartingPlayer(int len) {
        output.print("> ");
        String starting = input.nextLine();
        int startingPlayer;
        try {
            startingPlayer = Integer.parseInt(starting);
            if (0 < startingPlayer || startingPlayer < len - 1) {
                connection.send(new ChallengerPhaseAction(startingPlayer));
            } else {
                output.println(red + "Error: invalid selection!" + rst);
                chooseStartingPlayer(len);
            }
        } catch (NumberFormatException e) {
            output.println(red + "Error: it must be a numeric value!" + rst);
            chooseStartingPlayer(len);
        }
    }

    /**
     * Handles an error received from the server, following a user action or saying him he cannot perform any action in that moment.
     *
     * @param error the error received from the server.
     */
    public void errorHandling(GameError error) {
        switch (error.getError()) {
            case CELLOCCUPIED -> {
                output.println(red + "The following cells are already occupied, please choose them again." + rst);
                error.getCoordinates().forEach(n -> output.print(red + Arrays.toString(n) + ", " + rst));
            }
            case INVALIDINPUT -> {
                if (error.getMessage() != null) {
                    output.println(red + error.getMessage() + rst);
                }
            }
        }
        modelView.toggleInput();
    }

    /**
     * Handles the messages received from the server during the initial phase like, for example, the request of the number
     * of player.
     *
     * @param value the answer received from the server.
     */
    public void initialPhaseHandling(String value) {
        switch (value) {
            case "RequestPlayerNumber" -> {
                output.println(green + ((RequestPlayersNumber) modelView.getServerAnswer()).getMessage() + rst);
                choosePlayerNumber();
            }
            case "RequestColor" -> {
                output.println(green + ((RequestColor) modelView.getServerAnswer()).getMessage() + "\nRemaining:" + rst);
                ((RequestColor) modelView.getServerAnswer()).getRemaining().forEach(n -> output.print(n + ", "));
                output.print("\n");
                chooseColor(((RequestColor) modelView.getServerAnswer()).getRemaining());
            }
            case "GodRequest" -> {
                ChallengerMessages req = (ChallengerMessages) modelView.getServerAnswer();
                if (req.startingPlayer && req.players != null) {
                    output.println(req.message);
                    req.players.forEach(n -> output.println(req.players.indexOf(n) + ": " + n + ","));
                } else if (req.godList != null) {
                    req.godList.forEach(n -> output.print(n + ", "));
                    output.println();
                } else {
                    output.println(req.message);
                }
                modelView.toggleInput();
            }
        }
    }

    /**
     * Listener method: it waits for a server response, which is previously processed by the ActionHandler.
     *
     * @param evt the property change event, containing information about the response type and its new value.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String command = evt.getNewValue().toString();
        switch (evt.getPropertyName()) {
            case "gameError" -> {
                errorHandling((GameError) evt.getNewValue());
            }
            case "initialPhase" -> {
                initialPhaseHandling(command);
            }
            case "customMessage" -> {
                output.println(evt.getNewValue());
                if (modelView.getStarted() == 2) {
                    modelView.setStarted(3);
                }
            }
            case "connectionClosed" -> {
                output.println(evt.getNewValue());
                output.println(red + "Application will now close..." + rst);
                System.exit(0);
            }
        }
    }

}