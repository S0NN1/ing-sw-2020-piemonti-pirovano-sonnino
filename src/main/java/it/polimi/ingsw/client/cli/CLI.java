package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
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
import java.util.*;

/**
 * Main CLI client class; it manages the game if the player decides to play with Command Line Interface.
 *
 * @author Luca Pirovano, Nicolò Sonnino
 * @version 1.0.0
 */
public class CLI implements UI, Runnable {
    private static final String GREEN = "GREEN";
    private static final String YELLOW = "YELLOW";
    private static final String BG_BLACK = "BACKGROUND_BLACK";
    private final HashMap<String, String> nameMAPcolor = new HashMap<>();
    private final PrintStream output;
    private final Scanner input;
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final PropertyChangeSupport observers = new PropertyChangeSupport(this);
    private final DisplayCell[][] grid;
    private final Printable printable;
    private boolean activeGame;
    private ConnectionSocket connection;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
        grid = new DisplayCell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new DisplayCell();
            }
        }
        printable = new Printable();
        nameMAPcolor.put(GREEN, Constants.ANSI_GREEN);
        nameMAPcolor.put(YELLOW, Constants.ANSI_YELLOW);
        nameMAPcolor.put("RED", Constants.ANSI_RED);
        nameMAPcolor.put("RST", Constants.ANSI_RESET);
        nameMAPcolor.put("BLUE", Constants.ANSI_BLUE);
        nameMAPcolor.put("CYAN", Constants.ANSI_CYAN);
        nameMAPcolor.put(BG_BLACK, Constants.ANSI_BACKGROUND_BLACK);
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
        while (!confirmation) {
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
            output.println(nameMAPcolor.get(GREEN) + "Socket Connection setup completed!" + nameMAPcolor.get("RST"));
        } catch (DuplicateNicknameException e) {
            setup();
        }
        observers.addPropertyChangeListener(new ActionParser(connection, modelView));
        input.nextLine();
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

    public synchronized boolean isActiveGame() {
        return activeGame;
    }

    @Override
    public void run() {
        setup();
        while (isActiveGame()) {
            if (modelView.getStarted() >= 2) {
                loop();
            }
        }
        input.close();
        output.close();
    }

    /**
     * Create empty board
     * @param grid printed board
     */
    private void firstBuildBoard(DisplayCell[][] grid) {
        String[] rows = printable.lvl0.split("\n");
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 10; k++) {
                    grid[i][j].setCellRows(k, rows[k]);
                }
            }
        }
    }

    /**
     * Update grid after a change occured in ClientBoard
     * @param grid printed board
     */
    private void boardUpdater(DisplayCell[][] grid) {
        String[] rows;
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 10; k++) {
                    if (modelView.getBoard().getGrid()[i][j].getLevel() == 0) {
                        if (modelView.getBoard().getGrid()[i][j].isDome()) {
                            rows = printable.lvl0c.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                String temp = rows[4].substring(0, 22) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("BLUE") + rows[4].substring(23);
                                String temp2 = rows[5].substring(0, 22) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("BLUE") + rows[4].substring(23);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        } else {
                            rows = printable.lvl0.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                String temp = rows[4].substring(0, 17) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get(GREEN) + rows[4].substring(18);
                                String temp2 = rows[5].substring(0, 17) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get(GREEN) + rows[4].substring(18);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        }
                    }
                    if (modelView.getBoard().getGrid()[i][j].getLevel() == 1) {
                        if (modelView.getBoard().getGrid()[i][j].isDome()) {
                            rows = printable.lvl1c.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                String temp = rows[4].substring(0, 17) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("BLUE") + rows[4].substring(18);
                                String temp2 = rows[5].substring(0, 17) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("BLUE") + rows[4].substring(18);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        } else {
                            rows = printable.lvl1.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                String temp = rows[4].substring(0, 12) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("RST") + rows[4].substring(13);
                                String temp2 = rows[5].substring(0, 12) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("RST") + rows[4].substring(13);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        }
                    }
                    if (modelView.getBoard().getGrid()[i][j].getLevel() == 2) {
                        if (modelView.getBoard().getGrid()[i][j].isDome()) {
                            rows = printable.lvl2c.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor());
                                String temp = rows[4].substring(0, 26) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("BLUE") + rows[4].substring(27);
                                String temp2 = rows[5].substring(0, 26) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("BLUE") + rows[4].substring(27);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        } else {
                            rows = printable.lvl2.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].isDome()) {
                                rows = printable.lvl2c.split("\n");
                                if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                    String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                    String temp = rows[4].substring(0, 22) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("RST") + rows[4].substring(23);
                                    String temp2 = rows[5].substring(0, 22) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("RST") + rows[4].substring(23);
                                    rows[4] = temp;
                                    rows[5] = temp2;
                                }
                            }
                        }
                    }
                    if (modelView.getBoard().getGrid()[i][j].getLevel() == 3) {
                        rows = printable.lvl3.split("\n");
                        if (modelView.getBoard().getGrid()[i][j].isDome()) {
                            rows = printable.lvl2c.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                String temp = rows[4].substring(0, 26) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("CYAN") + rows[4].substring(27);
                                String temp2 = rows[5].substring(0, 22) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("CYAN") + rows[4].substring(23);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        }
                    }
                    if (modelView.getBoard().getGrid()[i][j].getLevel() == 4) {
                        rows = printable.lvl4.split("\n");
                        if (modelView.getBoard().getGrid()[i][j].isDome()) {
                            rows = printable.lvl2c.split("\n");
                            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                                String color = nameMAPcolor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase());
                                String temp = rows[4].substring(0, 35) + nameMAPcolor.get(BG_BLACK) + color + "☻" + nameMAPcolor.get("BLUE") + rows[4].substring(36);
                                String temp2 = rows[5].substring(0, 31) + nameMAPcolor.get(BG_BLACK) + color + "▲" + nameMAPcolor.get("BLUE") + rows[4].substring(32);
                                rows[4] = temp;
                                rows[5] = temp2;
                            }
                        }
                        grid[i][j].setCellRows(k, rows[k]);
                    }
                }
            }
        }
    }

    /**
     * Print board to the player
     * @param grid printed board
     */
    private void printBoard(DisplayCell[][] grid) {
        System.out.println(printable.rowWave);
        System.out.println(printable.rowWave);
        System.out.println(printable.coupleRowWave + nameMAPcolor.get(YELLOW) + printable.lineBlock + nameMAPcolor.get("RST") + printable.coupleRowWave);
        for (int i = 0; i <= 4; i++) {
            for (int k = 0; k <= 10; k++) {
                System.out.print(printable.coupleRowWave + nameMAPcolor.get(YELLOW) + "█" + nameMAPcolor.get("RST"));
                for (int j = 0; j <= 4; j++) {
                    System.out.print(grid[i][j].getCellRows(k) + nameMAPcolor.get(YELLOW) + "█" + nameMAPcolor.get("RST"));
                }
                System.out.print(printable.coupleRowWave + "\n");
            }
            System.out.println(printable.coupleRowWave + nameMAPcolor.get(YELLOW) + printable.lineBlock + nameMAPcolor.get("RST") + printable.coupleRowWave);
        }
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
                output.println(nameMAPcolor.get("RED") + "Invalid parameter, it must be a number.\nApplication will now quit..." + nameMAPcolor.get("RST"));
                System.exit(-1);
            }
        }
        connection.send(new NumberOfPlayers(selection));
        modelView.setStarted(1);
        input.nextLine();
    }

    /**
     * Lets the player decide his color, relying on the available ones. If the player is the last in a three-players
     * match, the server automatically assign him the last color.
     *
     * @param available the list of available colors, which will be printed out.
     */
    public void chooseColor(List<PlayerColors> available) {
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

    /**
     * Handles an error received from the server, following a user action or saying him he cannot perform any action in that moment.
     *
     * @param error the error received from the server.
     */
    public void errorHandling(GameError error) {
        switch (error.getError()) {
            case CELLOCCUPIED -> {
                output.println(nameMAPcolor.get("RED") + "The following cells are already occupied, please choose them again." + nameMAPcolor.get("RST"));
                error.getCoordinates().forEach(n -> output.print(nameMAPcolor.get("RED") + Arrays.toString(n) + ", " + nameMAPcolor.get("RST")));
            }
            case INVALIDINPUT -> {
                if (error.getMessage() != null) {
                    output.println(nameMAPcolor.get("RED") + error.getMessage() + nameMAPcolor.get("RST"));
                }
            }
            default -> {
                output.println("Generic error!");
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
                output.println(nameMAPcolor.get(GREEN) + ((RequestPlayersNumber) modelView.getServerAnswer()).getMessage() + nameMAPcolor.get("RST"));
                choosePlayerNumber();
            }
            case "RequestColor" -> {
                output.println(nameMAPcolor.get(GREEN) + ((RequestColor) modelView.getServerAnswer()).getMessage() + "\nRemaining:" + nameMAPcolor.get("RST"));
                ((RequestColor) modelView.getServerAnswer()).getRemaining().forEach(n -> output.print(n + ", "));
                output.print("\n");
                chooseColor(((RequestColor) modelView.getServerAnswer()).getRemaining());
            }
            case "GodRequest" -> {
                ChallengerMessages req = (ChallengerMessages) modelView.getServerAnswer();
                if (req.startingPlayer && req.players != null) {
                    output.println(req.message);
                    req.players.forEach(n -> output.println(req.players.indexOf(n) + ": " + n + ","));
                } else if(req.choosable!=null) {
                    output.println(req.getMessage());
                    req.choosable.forEach(n -> output.println(n.toString() + "\n" + n.godsDescription()));
                    output.println("\nSelect your god by typing choose <god-name>:");
                } else if (req.godList != null) {
                    req.godList.forEach(n -> output.print(n + ", "));
                    output.println();
                } else {
                    output.println(req.message);
                }
                modelView.toggleInput();
                if(modelView.getStarted()<3) modelView.setStarted(3);
            }
            default -> {
                output.println("Noting to do");
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
                output.println(nameMAPcolor.get("RED") + "Application will now close..." + nameMAPcolor.get("RST"));
                System.exit(0);
            }
            case "boardUpdate" -> {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                boardUpdater(grid);
                printBoard(grid);
            }
            default -> {
                output.println("Unrecognized answer");
            }
        }
    }

}