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
import java.util.concurrent.TimeUnit;

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
    private static final String RST = "RST";
    private static final String WHITE = "WHITE";
    private final HashMap<String, String> nameMapColor = new HashMap<>();
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
        grid = new DisplayCell[Constants.GRID_MAX_SIZE][Constants.GRID_MAX_SIZE];
        for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
            for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
                grid[i][j] = new DisplayCell();
            }
        }
        printable = new Printable();
        nameMapColor.put(GREEN, Constants.ANSI_GREEN);
        nameMapColor.put(YELLOW, Constants.ANSI_YELLOW);
        nameMapColor.put("RED", Constants.ANSI_RED);
        nameMapColor.put(RST, Constants.ANSI_RESET);
        nameMapColor.put("BLUE", Constants.ANSI_BLUE);
        nameMapColor.put("CYAN", Constants.ANSI_CYAN);
        nameMapColor.put(BG_BLACK, Constants.ANSI_BACKGROUND_BLACK);
        nameMapColor.put(WHITE, Constants.ANSI_WHITE);
    }

    /**
     * The main class of CLI client. It instantiates a new CLI class, running it.
     *
     * @param args the standard java main parameters.
     */
    public static void main(String[] args) {
        System.out.println(Constants.SANTORINI);
        CLI cli = new CLI();
        cli.run();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
            output.println(nameMapColor.get(GREEN) + "Socket Connection setup completed!" + nameMapColor.get("RST"));
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
     *
     * @param grid printed board
     */
    private void firstBuildBoard(DisplayCell[][] grid) {
        String[] rows = printable.levels[0].split("\n");
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 10; k++) {
                    grid[i][j].setCellRows(k, rows[k]);
                }
            }
        }
    }

    /**
     * Update grid after a change occurred in ClientBoard
     *
     * @param grid printed board
     */
    private void boardUpdater(DisplayCell[][] grid) {
        String[] rows;
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 10; k++) {
                    int level = modelView.getBoard().getGrid()[i][j].getLevel();
                    if (!modelView.getBoard().getGrid()[i][j].isDome()) {
                        rows = printable.levels[level].split("\n");
                        if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                            addWorkerToCell(nameMapColor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase()), rows, level, modelView.getBoard().getGrid()[i][j].getWorkerNum());
                        }
                    } else if (level == 3) {
                        rows = printable.levels[4].split("\n");
                    } else {
                        rows = printable.levelsC[level].split("\n");
                    }
                    grid[i][j].setCellRows(k, rows[k]);
                }
            }
        }
    }

    /**
     * Add worker to printable cell
     *
     * @param color Worker color
     * @param rows  string
     * @param level int
     */
    private void addWorkerToCell(String color, String[] rows, int level, int type) {
        String black;
        String[] temp = new String[3];
        String[] player = new String[3];
        int[][] indexes = new int[Constants.GRID_MAX_SIZE][1];
        String upperBody = "☻";
        String upperBody2 = "☺";
        String lowerBody = nameMapColor.get(WHITE) + "1";
        String lowerBody2 = nameMapColor.get(WHITE) + "2";
        player[0] = null;
        player[1] = "▲";
        player[2] = null;
        indexes[0][0] = 16;
        indexes[1][0] = 11;
        indexes[2][0] = 22;
        indexes[3][0] = 24;
        indexes[4][0] = 34;
        if (type == 1) {
            player[0] = upperBody;
            player[2] = lowerBody;
        } else {
            player[0] = upperBody2;
            player[2] = lowerBody2;
        }
        if (level == 3) {
            for (int i = 0; i <= 2; i++) {
                int j;
                if (i == 0 || i == 2) {
                    j = -1;
                } else {       //If i==1
                    j = 3;
                }
                temp[i] = rows[i + 4].substring(0, indexes[level][0] - j) + color + nameMapColor.get(BG_BLACK) +
                        player[i] + nameMapColor.get(RST) + rows[i + 4].substring(indexes[level][0] - j + 1);
                rows[i + 4] = temp[i];
            }
        } else {
            int j;
            if (level == 2) {
                j = 2;
            } else j = 0;
            for (int i = 0; i <= 2; i++) {
                temp[i] = rows[i + 4].substring(0, indexes[level][0] - j) + color + nameMapColor.get(BG_BLACK) +
                        player[i] + nameMapColor.get(RST) + rows[i + 4].substring(indexes[level][0] - j + 1);
                rows[i + 4] = temp[i];
            }
        }
    }

    /**
     * Print board to the player
     *
     * @param grid printed board
     */
    private void printBoard(DisplayCell[][] grid) {
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK + nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE);
        for (int i = 0; i <= 4; i++) {
            for (int k = 0; k <= 10; k++) {
                System.out.print(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + "█" + nameMapColor.get("RST"));
                for (int j = 0; j <= 4; j++) {
                    System.out.print(grid[i][j].getCellRows(k) + nameMapColor.get(YELLOW) + "█" + nameMapColor.get("RST"));
                }
                System.out.print(Printable.COUPLE_ROW_WAVE + "\n");
            }
            System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK + nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE);
        }
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.ROW_WAVE);
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
                output.println(nameMapColor.get("RED") + "Invalid parameter, it must be a numeric value." + nameMapColor.get("RST"));
                input.nextLine();
                choosePlayerNumber();
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
        firstBuildBoard(grid);
        while (true) {
            output.println(">Make your choice!");
            output.print(">");
            try {
                PlayerColors color = PlayerColors.parseInput(input.nextLine());
                if (available.contains(color)) {
                    connection.send(new ChosenColor(color));
                    modelView.setStarted(2);
                    modelView.setColor(color.toString());
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
                output.println(nameMapColor.get("RED") + "The following cells are already occupied, please choose them again." + nameMapColor.get("RST"));
                error.getCoordinates().forEach(n -> output.print(nameMapColor.get("RED") + Arrays.toString(n) + ", " + nameMapColor.get("RST")));
            }
            case INVALIDINPUT -> {
                if (error.getMessage() != null) {
                    output.println(nameMapColor.get("RED") + error.getMessage() + nameMapColor.get("RST"));
                }
                modelView.setTurnActive(true);
            }
            default -> {
                output.println("Generic error!");
            }
        }
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
                output.println(nameMapColor.get(GREEN) + ((RequestPlayersNumber) modelView.getServerAnswer()).getMessage() + nameMapColor.get("RST"));
                choosePlayerNumber();
            }
            case "RequestColor" -> {
                output.println(nameMapColor.get(GREEN) + ((RequestColor) modelView.getServerAnswer()).getMessage() + "\nRemaining:" + nameMapColor.get("RST"));
                ((RequestColor) modelView.getServerAnswer()).getRemaining().forEach(n -> output.print(n + ", "));
                output.print("\n");
                chooseColor(((RequestColor) modelView.getServerAnswer()).getRemaining());
            }
            case "GodRequest" -> {
                ChallengerMessages req = (ChallengerMessages) modelView.getServerAnswer();
                if (req.isStartingPlayer() && req.getPlayers() != null) {
                    output.println(req.getMessage());
                    req.getPlayers().forEach(n -> output.println(req.getPlayers().indexOf(n) + ": " + n + ","));
                } else if (req.getSelectable() != null) {
                    output.println(req.getMessage());
                    req.getSelectable().forEach(n -> output.println(n.toString() + "\n" + n.godsDescription()));
                    output.println("\nSelect your god by typing choose <god-name>:");
                } else if (req.getGodList() != null) {
                    req.getGodList().forEach(n -> output.print(n + ", "));
                    output.println();
                } else {
                    output.println(req.getMessage());
                }
                modelView.toggleInput();
                if (modelView.getStarted() < 3) modelView.setStarted(3);
            }
            case "WorkerPlacement" -> {
                firstUpdateCli();
                output.println(modelView.getServerAnswer().getMessage());
                output.print(">");
                modelView.toggleInput();
            }
            default -> {
                output.println("Nothing to do");
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
        String command = (evt.getNewValue() != null) ? evt.getNewValue().toString() : null;
        switch (evt.getPropertyName()) {
            case "gameError" -> {
                errorHandling((GameError) evt.getNewValue());
            }
            case "initialPhase" -> {
                assert command != null;
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
                output.println(nameMapColor.get("RED") + "Application will now close..." + nameMapColor.get("RST"));
                System.exit(0);
            }
            case "boardUpdate" -> {
                updateCli();
            }
            case "firstBoardUpdate" -> {
                firstUpdateCli();
            }
            case "selectWorker" -> {
                selectWorker();
            }
            case "end" -> {
                end();
            }
            case "select" -> {
                printSpaces();
            }
            default -> {
                output.println("Unrecognized answer");
            }
        }
    }

    private void end() {
        System.out.print("\r Turn ended");
    }

    public void selectWorker() {
        System.out.print("\r  • SELECTWORKER <1/2>\n");
        System.out.print(">");
    }

    public void printMenu() throws InterruptedException {
        String active;
        String atlas;
        if (modelView.getGamePhase() != 0) {
            if (!modelView.isTurnActive()) {
                active = " NOT ";
            } else active = "";
            System.out.println(active + " YOUR TURN");
        }
        if (modelView.getGod().equalsIgnoreCase("ATLAS")) {
            atlas = "  • PLACEDOME\\n\"";
        } else atlas = "";
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.print("  • MOVE\n" +
                "  • BUILD\n" +
                atlas +
                "  • END\n");
        System.out.print(">");
    }

    public void firstPrintMenu() throws InterruptedException {
        String active;
        if (!modelView.isTurnActive()) {
            active = "NOT ";
        } else active = "";
        System.out.println(active + "YOUR TURN");
        TimeUnit.MILLISECONDS.sleep(500);
    }

    public void firstUpdateCli() {
        clearScreen();
        boardUpdater(grid);
        printBoard(grid);
        try {
            firstPrintMenu();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void updateCli() {
        clearScreen();
        boardUpdater(grid);
        printBoard(grid);
        try {
            printMenu();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void printSpaces() {
        for (int i = 0; i < modelView.getSelectSpaces().size(); i++) {
            System.out.print("(" + modelView.getSelectSpaces().get(i).getX() + "," + modelView.getSelectSpaces().get(i).getY() + ")  ");
        }
        System.out.println();
    }


}