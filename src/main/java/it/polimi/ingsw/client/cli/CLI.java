package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Printable;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.InvalidNicknameException;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main CLI client class; it manages the game if the player decides to play with Command Line Interface.
 *
 * @author Luca Pirovano, Nicolò Sonnino
 * @version 2.0.0
 */
public class CLI implements UI, Runnable {
    private static final Logger logger = Logger.getLogger(CLI.class.getName());
    public static final String RED = "RED";
    private static final String GREEN = "GREEN";
    private static final String YELLOW = "YELLOW";
    private static final String BG_BLACK = "BACKGROUND_BLACK";
    private static final String RST = "RST";
    private static final String WHITE = "WHITE";
    private static final String BG_PURPLE = "BG_PURPLE";
    private static final HashMap<String, String> nameMapColor = new HashMap<>();
    private final PrintStream output;
    private final Scanner input;
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final PropertyChangeSupport observers = new PropertyChangeSupport(this);
    private final DisplayCell[][] grid;
    private boolean activeGame;
    private ConnectionSocket connection;
    private int maxSideIndex;

    /**
     * Constructor CLI creates a new CLI instance.
     */
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
        nameMapColor.put(GREEN, Constants.ANSI_GREEN);
        nameMapColor.put(YELLOW, Constants.ANSI_YELLOW);
        nameMapColor.put(RED, Constants.ANSI_RED);
        nameMapColor.put(RST, Constants.ANSI_RESET);
        nameMapColor.put("BLUE", Constants.ANSI_BLUE);
        nameMapColor.put("CYAN", Constants.ANSI_CYAN);
        nameMapColor.put(BG_PURPLE, Constants.ANSI_BACKGROUND_PURPLE);
        nameMapColor.put(BG_BLACK, Constants.ANSI_BACKGROUND_BLACK);
        nameMapColor.put(WHITE, Constants.ANSI_WHITE);
    }

    /**
     * The main class of CLI client. It instantiates a new CLI class, running it.
     *
     * @param args of type String[] - the standard java main parameters.
     */
    public static void main(String[] args) {
        System.out.println(Constants.SANTORINI);
        System.out.println(Constants.AUTHORS);
        System.out.println(Constants.RULES + "\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println(">Insert the server IP address");
        System.out.print(">");
        String ip = scanner.nextLine();
        System.out.println(">Insert the server port");
        System.out.print(">");
        int port = scanner.nextInt();
        Constants.setAddress(ip);
        Constants.setPort(port);
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * Method clearScreen flushes terminal's screen
     */
    public static void clearScreen() {
        try{
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Method toggleActiveGame changes the value of the parameter activeGame, which states if the game is active or if
     * it has finished.
     *
     * @param activeGame of type boolean - the value based on the status of the game.
     */
    public void toggleActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    /**
     * Method setup called when a client instance has started. It asks player's nickname and tries to
     * establish a connection to the remote server through the socket interface. If the connection is active, displays
     * a message on the CLI.
     */
    public void setup() {
        String nickname = null;
        boolean confirmation = false;
        while (!confirmation) {
            do {
                System.out.println(">Insert your nickname: ");
                System.out.print(">");
                nickname = input.nextLine();
            } while (nickname == null);
            System.out.println(">You chose: " + nickname);
            System.out.println(">Is it ok? [y/n] ");
            System.out.print(">");
            if (input.nextLine().equalsIgnoreCase("y")) {
                confirmation = true;
            } else {
                nickname = null;
            }
        }
        connection = new ConnectionSocket();
        modelView.setPlayerName(nickname);
        try {
            connection.setup(nickname, modelView, actionHandler);
            System.out.println(nameMapColor.get(GREEN) + "Socket Connection setup completed!" + nameMapColor.get("RST"));
        } catch (DuplicateNicknameException | InvalidNicknameException e) {
            setup();
        }
        observers.addPropertyChangeListener("action", new ActionParser(connection, modelView));
    }

    /**
     * Method loop keeps running and executing all actions client side, if the input has toggled (through the apposite
     * method) it calls the action one and parses the player's input.
     */
    public void loop() {
        input.reset();
        String cmd = input.nextLine();
        observers.firePropertyChange("action", null, cmd);
    }

    /**
     * Method isActiveGame returns the activeGame of this CLI object.
     *
     * @return - the activeGame (type boolean) of this CLI object.
     */
    public synchronized boolean isActiveGame() {
        return activeGame;
    }

    /**
     * Method run loops waiting for a message.
     */
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
     * Method firstBuildBoard creates empty board.
     *
     * @param grid of type DisplayCell[][] - the printed board.
     */
    private void firstBuildBoard(DisplayCell[][] grid) {
        String[] rows = Printable.getLEVELS()[0].split("\n");
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 10; k++) {
                    grid[i][j].setCellRows(k, rows[k]);
                }
            }
        }
    }

    /**
     * Method boardUpdater updates grid after a change occurred in ClientBoard.
     *
     * @param grid of type DisplayCell[][] - the printed board.
     */
    private void boardUpdater(DisplayCell[][] grid) {
        String[] rows;
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 10; k++) {
                    int level = modelView.getBoard().getGrid()[i][j].getLevel();
                    rows = generateTypeOfLevel(i, j, level);
                    grid[i][j].setCellRows(k, rows[k]);
                }
            }
        }
    }

    /**
     * Method generateTypeOfLevel generates right levels for CLI use.
     *
     * @param i     of type int - a counter.
     * @param j     of type int - a counter.
     * @param level of type int - the level to print.
     * @return String[] - the grid's level row.
     */
    private String[] generateTypeOfLevel(int i, int j, int level) {
        String[] rows;
        if (!modelView.getBoard().getGrid()[i][j].isDome()) {
            rows = generateRows(level, Printable.getLEVELS());
            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                addWorkerToCell(nameMapColor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase()), rows,
                        level, modelView.getBoard().getGrid()[i][j].getWorkerNum());
            }
        } else if (level == 3) {
            rows = generateRows(4, Printable.getLEVELS());
        } else {
            rows = generateRows(level, Printable.getLEVELSWITHDOME());
        }
        return rows;
    }

    /**
     * Method generateRows splits levels array into multiple Strings.
     *
     * @param level  of type int - the grid's level.
     * @param levels of type String[] - the possible levels printable.
     * @return String[] - the rows generated.
     */
    private String[] generateRows(int level, String[] levels) {
        String[] rows;
        rows = levels[level].split("\n");
        return rows;
    }

    /**
     * Method addWorkerToCell adds worker to Printable cell.
     *
     * @param color of type String - the worker's color.
     * @param rows of type String[] - the rows used to insert player.
     * @param level of type int - the level modified.
     */
    private void addWorkerToCell(String color, String[] rows, int level, int type) {
        String[] temp = new String[3];
        String[] player = new String[3];
        int[] cellInfos = new int[]{level, type};
        int[][] indexes = new int[Constants.GRID_MAX_SIZE][1];
        String lowerBody = nameMapColor.get(WHITE) + "1";
        String lowerBody2 = nameMapColor.get(WHITE) + "2";
        String backgroundColor = BG_BLACK;
        player[0] =  "@";
        player[1] = "╥";
        player[2] = null;
        indexes[0][0] = 16;
        indexes[1][0] = 11;
        indexes[2][0] = 22;
        indexes[3][0] = 24;
        indexes[4][0] = 34;
        if (type == 1) {
            player[2] = lowerBody;
        } else {
            player[2] = lowerBody2;
        }
        if (level == 3) {
            for (int i = 0; i <= 2; i++) {
                int j;
                j = getRightIndex(i == 0 || i == 2, -1, 3);
                HashMap<Integer, String[]> stringMap = createStringMap(temp, player);
                int[] counters = new int[]{i, j};
                insertPlayer(color, rows, cellInfos, stringMap, indexes, backgroundColor, counters);
            }
        } else {
            int j = getRightIndex(level == 2, 2, 0);
            for (int i = 0; i <= 2; i++) {
                HashMap<Integer, String[]> stringMap = createStringMap(temp, player);
                int[] counters = new int[]{i, j};
                insertPlayer(color, rows, cellInfos, stringMap, indexes, backgroundColor, counters);
            }
        }
    }

    /**
     * Method getRightIndex gets right index in order to print levels on the grid.
     *
     * @param b  of type boolean - defines the two cases in order split row in the correct spot.
     * @param i2 of type int - the first type of counter.
     * @param i3 of type int - the second type of counters.
     * @return int - the correct counter.
     */
    private int getRightIndex(boolean b, int i2, int i3) {
        int j;
        if (b) {
            j = i2;
        } else {
            j = i3;
        }
        return j;
    }

    /**
     * Method createStringMap encapsulates player and temp arrays used for addWorkerToCell method for complaints
     * reasons.
     *
     * @param temp   of type String[] - temporary cell's rows in which player is inserted.
     * @param player of type String[] - the player to be inserted into the rows.
     * @return HashMap<Integer, String [ ]> - the two strings.
     */
    private HashMap<Integer, String[]> createStringMap(String[] temp, String[] player) {
        HashMap<Integer, String[]> stringMap = new HashMap<>();
        stringMap.put(0, player);
        stringMap.put(1, temp);
        return stringMap;
    }

    /**
     * Method insertPlayer inserts right player into the row.
     *
     * @param color of type String  - the player's color
     * @param rows of type String[] - the cell's row
     * @param cellInfos of type int[] - the grid's levels
     * @param stringMap of type HashMap<Integer, String[]> - the hashmap mapping levels to correct strings.
     * @param indexes of type int[][] - the indexes for each level.
     * @param backgroundColor of type String - the worker's background color.
     * @param counters of type int[] - the indexes in order to print correctly.
     */
    private void insertPlayer(String color, String[] rows, int[] cellInfos, HashMap<Integer, String[]> stringMap,
                              int[][] indexes, String backgroundColor, int[] counters) {
        if (counters[0] == 2 && modelView.getActiveWorker() == cellInfos[1] && modelView.isTurnActive() &&
                color.equalsIgnoreCase(nameMapColor.get(modelView.getColor().toUpperCase()))) {
            color = Constants.ANSI_WHITE;
            backgroundColor = BG_PURPLE;
        }
        stringMap.get(1)[counters[0]] = rows[counters[0] + 4].substring(0, indexes[cellInfos[0]][0] - counters[1]) +
                color + nameMapColor.get(backgroundColor) +
                stringMap.get(0)[counters[0]] + nameMapColor.get(RST) + rows[counters[0] + 4].substring(
                        indexes[cellInfos[0]][0] - counters[1] + 1);
        rows[counters[0] + 4] = stringMap.get(1)[counters[0]];
    }


    /**
     * Method printBoard prints board to the player.
     *
     * @param grid - the printed board.
     */
    private void printBoard(DisplayCell[][] grid) {
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.ROW_WAVE);
        String[] sideMenuRows= buildSideMenu();
        String[] guideMenuRows= buildSideHelp();
        int check = 0;
        System.out.print(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK +
                nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE + "  " + sideMenuRows[0]);
        for (int i = 0; i <= 4; i++) {
            for (int k = 0; k <11; k++) {
                System.out.print(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.SINGLE_LINE_BLOCK + nameMapColor.get("RST"));
                for (int j = 0; j <= 4; j++) {
                    System.out.print(grid[i][j].getCellRows(k) + nameMapColor.get(YELLOW) + Printable.SINGLE_LINE_BLOCK +
                            nameMapColor.get("RST"));
                }
                insertMenus(sideMenuRows, guideMenuRows, check, k);
            }
            if(check==0 ) {
                check = lastCellRow(sideMenuRows, check);
            }else if(check==1) {
                check = lastCellRow(guideMenuRows, check);
            }
            else {
                System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK +
                        nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE);
            }
        }
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.ROW_WAVE);
    }

    private int lastCellRow(String[] sideMenuRows, int check) {
        if (maxSideIndex > 11) {
            System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK +
                    nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE + "  " + sideMenuRows[11]);
        }
        else {
            System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK +
                    nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE);
        }
        check++;
        return check;
    }

    /**
     * Method insertMenus inserts sideMenu and guideMenu next to the grid.
     *
     * @param sideMenuRows of type String[] - the side menu's rows.
     * @param guideMenuRows of type String[] - the guide menu's rows.
     * @param check of type int - the counter needed for the correct printing.
     * @param k of type int - the counter needed for the correct printing.
     */
    private void insertMenus(String[] sideMenuRows, String[] guideMenuRows, int check, int k) {
        if (check == 0) {
            insertSideMenuRows(sideMenuRows[k + 1]);
        } else if (check == 1) {
            if(k<maxSideIndex) {
                insertGuideMenuRows(guideMenuRows, k);
            } else {
                System.out.println(Printable.COUPLE_ROW_WAVE);
            }
        }  else {
            System.out.println(Printable.COUPLE_ROW_WAVE);
        }
    }

    /**
     * Method insertSideMenuRows inserts side menu rows into the output stream.
     *
     * @param sideMenuRow of type String - the side status menu.
     */
    private void insertSideMenuRows(String sideMenuRow) {
        System.out.print(Printable.COUPLE_ROW_WAVE + "  " + sideMenuRow);
    }

    /**
     * Method insertGuideMenuRows inserts guide rows into the output stream.
     *
     * @param guideMenuRows of type String[] - the side guide's rows.
     * @param i2 of type int - the counter required for correct printing.
     */
    private void insertGuideMenuRows(String[] guideMenuRows, int i2) {
        System.out.println(Printable.COUPLE_ROW_WAVE + " " + guideMenuRows[i2]);
    }

    /**
     * Method buildSideMenu builds side status menu.
     *
     * @return String[] - the side status menu.
     */
    private String[] buildSideMenu() {
        String playerName = modelView.getPlayerName();
        int max = Math.max(playerName.length(), 10);
        String color = modelView.getColor();
        String god = modelView.getGod();
        String[] sideMenuRows = Printable.SIDE_MENU.split("\n");
        for (int i = 0; i < 12; i++) {
            String[] temp = sideMenuRows[i].split("-");
            if (temp.length == 2) {
                if (i == 0 || i == 2 || i == 5 || i == 8 || i == 11) {
                    sideMenuRows[i] = temp[0] + addScores(max) + temp[1] + "\n";
                } else {
                    String var = null;
                    if (i == 4) {
                        var = playerName;
                    } else if (i == 7) {
                        var = god;
                    } else if (i == 10) {
                        var = nameMapColor.get(color) + addBlocks(max) + nameMapColor.get(RST);
                    }
                    assert var != null;
                    sideMenuRows[i] = temp[0] + var + addSpaces(max, var) + temp[1] + "\n";
                }
            } else {
                sideMenuRows[i] = temp[0] + temp[1] + addSpaces(max, temp[1]) + temp[2] + "\n";
            }
        }
        return sideMenuRows;
    }

    /**
     * Method buildSideHelp build side guide.
     *
     * @return String[] - the side guide.
     */
    private String[] buildSideHelp() {
        String godDesc = modelView.getGodDesc();
        String[] sideMenuHelp;
        String godSideMenu;
        if(Printable.getGodMapSideMenu().get(modelView.getGod())==null){
            godSideMenu = "";
        }
        else{
            godSideMenu = Printable.getGodMapSideMenu().get(modelView.getGod()) + "\n";
        }
        String menu =
                nameMapColor.get(YELLOW) + "HELP GUIDE" + nameMapColor.get(RST) + "\n" +
                        nameMapColor.get(YELLOW) + "GOD DESCRIPTION " + nameMapColor.get(RST) + "\n" +
                        godDesc + "\n" +
                        nameMapColor.get(YELLOW) + "SET <row1> <column1> <row2> <column2>" + nameMapColor.get(RST) +
                        ": set workers on specified cells." + "\n" +
                        nameMapColor.get(YELLOW) + "SELECTWORKER <1/2>" + nameMapColor.get(RST) +
                        ": choose which worker you want to use." + "\n" +
                        nameMapColor.get(YELLOW) + "MOVE (no args)/MOVE <row> <col>" + nameMapColor.get(RST) +
                        ": print spaces/moves worker to cell." + "\n" +
                        nameMapColor.get(YELLOW) + "BUILD (no args)/BUILD <row> <column>" + nameMapColor.get(RST) +
                        ": print spaces/builds worker to cell." + "\n" +
                        godSideMenu +
                        nameMapColor.get(YELLOW) + "END" + nameMapColor.get(RST) + ": end turn";
        sideMenuHelp = menu.split("\n");
        maxSideIndex = sideMenuHelp.length;
        return sideMenuHelp;
    }

    /**
     * Method addScores adds spaces to side menu.
     *
     * @param max of type int - max length of side menu.
     * @return StringBuilder - the string added to side menu.
     */
    private StringBuilder addSpaces(int max, String s) {
        if (max - s.length() == 0) {
            return new StringBuilder();
        } else {
            StringBuilder y = new StringBuilder();
            y.append(" ".repeat(Math.max(0, max - s.length())));
            return y;
        }
    }

    /**
     * Method addScores adds blocks to side menu.
     *
     * @param max of type int - the max length of side menu.
     * @return StringBuilder - the string added to side menu.
     */
    private StringBuilder addBlocks(int max) {
        StringBuilder s = new StringBuilder();
        s.append("█".repeat(Math.max(0, max)));
        return s;
    }

    /**
     * Method addScores adds scores to side menu.
     *
     * @param max of type int - the max length of side menu.
     * @return StringBuilder - the string added to side menu.
     */
    private StringBuilder addScores(int max) {
        StringBuilder s = new StringBuilder();
        s.append("-".repeat(Math.max(0, max)));
        return s;
    }

    /**
     * Method choosePlayerNumber lets the first-connected user to decides the match capacity.
     * Terminates the client if the player inserts an incorrect type of input.
     */
    public void choosePlayerNumber() {
        int selection;
        while (true) {
            try {
                System.out.print(">");
                String cmd = input.nextLine();
                selection = Integer.parseInt(cmd);
                break;
            } catch (NumberFormatException e) {
                System.out.println(nameMapColor.get(RED) + "Invalid parameter, it must be a numeric value." +
                        nameMapColor.get("RST"));
            }
        }
        connection.send(new NumberOfPlayers(selection));
        modelView.setStarted(1);
    }

    /**
     * Method chooseColor lets the player decide his color, relying on the available ones.
     * If the player is the last in a three-players match, the server automatically assign him the last color.
     *
     * @param available of type List<PlayerColors> - the list of available colors, which will be printed out.
     */
    public void chooseColor(List<PlayerColors> available) {
        firstBuildBoard(grid);
        while (true) {
            System.out.println(">Make your choice!");
            System.out.print(">");
            try {
                PlayerColors color = PlayerColors.parseInput(input.nextLine());
                if (available.contains(color)) {
                    connection.send(new ChosenColor(color));
                    modelView.setStarted(2);
                    return;
                } else {
                    printError(System.out, "Color not available!");
                }
            } catch (IllegalArgumentException e) {
                printError(System.out, "Invalid input! Please provide one of the accepted colors.");
            }
        }
    }

    /**
     * Method greaterThan prints ">".
     */
    private void greaterThan() {
        System.out.print(">");
    }

    /**
     * Method errorHandling handles an error received from the server, following a user action or saying him he cannot
     * perform any action in that moment.
     *
     * @param error of type GameError - the error received from the server.
     */
    public void errorHandling(GameError error) {
        switch (error.getError()) {
            case CELLOCCUPIED -> {
                System.out.println(nameMapColor.get(RED) + "The following cells are already occupied, please choose them " +
                        "again." + nameMapColor.get("RST"));
                error.getCoordinates().forEach(n -> System.out.print(nameMapColor.get(RED) + Arrays.toString(n) + ", " +
                        nameMapColor.get("RST")));
                System.out.println();
                greaterThan();
            }
            case INVALIDINPUT -> {
                if (error.getMessage() != null) {
                    System.out.println(nameMapColor.get(RED) + error.getMessage() + nameMapColor.get(RST));
                } else {
                    System.out.println(nameMapColor.get(RED) + "Input error, please try again!" + nameMapColor.get(RST));
                }
                greaterThan();
                modelView.setTurnActive(true);
            }
            case WORKERBLOCKED -> printError(System.err, "Selected worker is blocked, select the other one!");
            default -> printError(System.out, "Generic error!");
        }
    }

    /**
     * Method printError prints error message.
     *
     * @param err of type PrintStream - the output stream.
     * @param s of type String - the message.
     */
    private void printError(PrintStream err, String s) {
        err.println(s);
        greaterThan();
    }

    /**
     * Method initialPhaseHandling handles the messages received from the server during the initial phase like,
     * for example, the request of the number of player.
     *
     * @param value of type String - the answer received from the server.
     */
    public void initialPhaseHandling(String value) {
        switch (value) {
            case "RequestPlayerNumber" -> {
                System.out.println(nameMapColor.get(GREEN) + ((RequestPlayersNumber)
                        modelView.getServerAnswer()).getMessage() + nameMapColor.get("RST"));
                choosePlayerNumber();
            }
            case "RequestColor" -> {
                System.out.println(nameMapColor.get(GREEN) + ((ColorMessage) modelView.getServerAnswer()).getMessage() +
                        "\nRemaining:" + nameMapColor.get("RST"));
                ((ColorMessage) modelView.getServerAnswer()).getRemaining().forEach(n -> System.out.print(n + ", "));
                System.out.print("\n");
                chooseColor(((ColorMessage) modelView.getServerAnswer()).getRemaining());
            }
            case "GodRequest" -> {
                ChallengerMessages req = (ChallengerMessages) modelView.getServerAnswer();
                godRequest(req);
            }
            case "WorkerPlacement" -> {
                firstUpdateCli();
                String[] msg = modelView.getServerAnswer().getMessage().toString().split(" ");
                System.out.println(Constants.ANSI_UNDERLINE + msg[0] + nameMapColor.get(RST) + " choose your workers " +
                        "position by typing" + nameMapColor.get(YELLOW) + " SET <row1 <col1> <row2> <col2> " +
                        nameMapColor.get(RST) + "where 1 and 2 indicates worker number.");
                System.out.print(">");
                modelView.activateInput();
            }
            default -> System.out.println("Nothing to do");
        }
    }

    /**
     * Method godRequest handles selection of gods and turn's order.
     *
     * @param req of type ChallengerMessages - the message containing infos about gods and players.
     */
    private void godRequest(ChallengerMessages req) {
        if (req.isStartingPlayer() && req.getPlayers() != null) {
            System.out.println(req.getMessage().split(" ")[0] + " choose the starting player by typing" +
                    nameMapColor.get(YELLOW) + " STARTER <number-of-player>" + nameMapColor.get(RST));
            req.getPlayers().forEach(n -> System.out.println(req.getPlayers().indexOf(n) + ": " + n + ","));
        } else if (req.getSelectable() != null) {
            System.out.println("\n" + req.getMessage());
            req.getSelectable().forEach(n -> System.out.println("\n" + n.toString() + "\n" + n.godsDescription()));
            System.out.println("\nSelect your god by typing" + nameMapColor.get(YELLOW) + " choose <god-name>" +
                    nameMapColor.get(RST));
            System.out.print(">");
        } else if (req.getGodList() != null) {
            System.out.println();
            req.getGodList().forEach(n -> System.out.print(n + ", "));
            System.out.println();
        } else {
            if (req.getMessage().contains("ADDGOD") || req.getMessage().contains("Description") ||
                    req.getMessage().contains("been added")) {
                System.out.println();
            }
            if (req.getMessage().contains("<god-name>")) {
                String[] temp = req.getMessage().split("\n");
                for (int i = 0; i < 9; i++) {
                    System.out.print(temp[i] + "\n");
                }
                System.out.println("\nSelect your god by typing" + nameMapColor.get(YELLOW) + " choose <god-name>" +
                        nameMapColor.get(RST));
                greaterThan();
                modelView.activateInput();
                return;
            }
            System.out.println(req.getMessage());
            greaterThan();
        }
        modelView.activateInput();
        if (modelView.getStarted() < 3) modelView.setStarted(3);
    }

    /**
     * Method propertyChange waits for a server response which is previously processed by the ActionHandler.
     *
     * @param evt of type PropertyChangeEvent - the event containing information about the response type and its new value.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String command = (evt.getNewValue() != null) ? evt.getNewValue().toString() : null;
        switch (evt.getPropertyName()) {
            case "gameError" -> errorHandling((GameError) evt.getNewValue());
            case "initialPhase" -> {
                assert command != null;
                initialPhaseHandling(command);
            }
            case "customMessage" -> {
                System.out.println(evt.getNewValue());
                if (modelView.getStarted() == 2) {
                    modelView.setStarted(3);
                }
            }
            case "connectionClosed" -> {
                System.out.println(evt.getNewValue());
                System.out.println(nameMapColor.get(RED) + "Application will now close..." + nameMapColor.get("RST"));
                System.exit(0);
            }
            case "noPossibleMoves" -> System.err.println("No possible moves!");
            case "modifiedTurnNoUpdate" -> System.out.println(((Answer) evt.getNewValue()).getMessage().toString());
            case "boardUpdate" -> fireBoardUpdate(evt);
            case "firstBoardUpdate" -> firstUpdateCli();
            case "selectWorker" -> selectWorker();
            case "newPlayerTurn" -> newPlayerTurn(evt);
            case "end" -> end(((Answer)evt.getNewValue()).getMessage().toString());
            case "select" -> fireSelectSpaces(evt);
            case "win" -> {
                System.out.println(nameMapColor.get(RED) + "YOU WIN!" + nameMapColor.get(RST));
                System.exit(0);
            }
            case "lose" -> {
                System.out.println(nameMapColor.get(RED) + "YOU LOSE!" + nameMapColor.get(RST));
                System.out.println(nameMapColor.get(YELLOW) + "Player " + evt.getNewValue() + " has won." +
                        nameMapColor.get(RST));
                System.exit(0);
            }
            case "singleLost" -> System.err.println("All workers blocked, YOU LOSE!");
            case "otherLost" -> otherPlayerLost(evt);
            case "matchStarted" -> System.out.println("The match has started!");
            default -> System.out.println("Unrecognized answer");
        }
    }


    /**
     * Method newPlayerTurn prints opponent's turn status.
     *
     * @param evt of type PropertyChangeEvent - boolean array needed for correct printing.
     */
    private void newPlayerTurn(PropertyChangeEvent evt) {
        try {
            printMenu((boolean[]) evt.getOldValue(), null);
        } catch (InterruptedException e) {
            System.err.println("Interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method fireSelectSpaces inserts checkers into printSpaces method.
     *
     * @param evt of type PropertyChangeEvent - the event containing instructions for printSpaces method.
     */
    private void fireSelectSpaces(PropertyChangeEvent evt) {
        if (evt.getOldValue().getClass().isArray()) {
            boolean[] checkers = ((boolean[]) evt.getOldValue());
            String message = null;
            if(evt.getNewValue()!=null) {
                message = evt.getNewValue().toString();
            }
            printSpaces(checkers, message);
        }
    }

    /**
     * Method fireBoardUpdate inserts checkers into updateCli method.
     *
     * @param evt of type PropertyChangeEvent - the event containing instructions for updateCli method.
     */
    private void fireBoardUpdate(PropertyChangeEvent evt) {
        if (evt.getOldValue().getClass().isArray()) {
            boolean[] checkers = ((boolean[]) evt.getOldValue());
            String message = null;
            if(evt.getNewValue()!=null) {
                message = ((Answer)evt.getNewValue()).getMessage().toString();
            }
            updateCli(checkers, message);
        }
    }

    /**
     * Method otherPlayerLost updates CLI, removes loser from CLI and prints its nickname.
     *
     * @param evt of type PropertyChangeEvent - the event containing loser's nickname.
     */
    private void otherPlayerLost(PropertyChangeEvent evt) {
        clearScreen();
        boardUpdater(grid);
        printBoard(grid);
        System.out.println(nameMapColor.get(YELLOW) + "Player " + evt.getNewValue() + " has lost." + nameMapColor.get(RST));
    }

    /**
     * Method end prints end message.
     *
     * @param message of type String - the message printed when turn ends.
     */
    private void end(String message) {
        System.out.print("\r" + message);
    }

    /**
     * Method selectWorker prints SELECT_WORKER option.
     */
    public void selectWorker() {
        System.out.print("\r  • SELECTWORKER <1/2>\n");
        greaterThan();
    }

    /**
     * Method printMenu prints menu under the grid.
     *
     * @param actions of type boolean[] - actions needed for correct print.
     * @param message of type String - the content of message received.
     * @throws InterruptedException when TimeUnit fails.
     */
    public void printMenu(boolean[] actions, String message) throws InterruptedException {
        String active;
        String customPower = Constants.getGodMapCustomAction().get(modelView.getGod().toUpperCase());
        if (modelView.getGamePhase() != 0) {
            if (!modelView.isTurnActive()) {
                active = modelView.getCurrentPlayer() + "'S";
            } else active = "YOUR";
            System.out.println(active + " TURN");
        }
        TimeUnit.MILLISECONDS.sleep(500);
        if (modelView.isTurnActive()) {
                System.out.print(actions[0] ? " - MOVE" : "");
                System.out.println(actions[1] ? " - BUILD": "");
            if(actions.length==4){
                System.out.println(" - " + customPower);
            }
                System.out.println(actions[2] ? " - END" : "");
            if (message != null) {
                System.out.println(message);
            }
            greaterThan();
        }
    }

    /**
     * Method firstPrintMenu prints menu without any entries.
     * @throws InterruptedException when TimeUnit fails.
     */
    public void firstPrintMenu() throws InterruptedException {
        String active;
        if (!modelView.isTurnActive()) {
            active = "NOT ";
        } else active = "";
        System.out.println(active + "YOUR TURN");
        TimeUnit.MILLISECONDS.sleep(500);
    }

    /**
     * Method firstUpdateCli prints and updates for the first CLI.
     */
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

    /**
     * Method updateCli prints and updates CLI.
     *
     * @param actions of type boolean[] - actions needed for correct print.
     * @param message of type String - the content of message received.
     */
    public void updateCli(boolean[] actions, String message) {
        clearScreen();
        boardUpdater(grid);
        printBoard(grid);
        try {
            printMenu(actions, message);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method printSpaces prints possible actions after a SelectBuild/SelectMove.
     *
     * @param actions of type boolean[] - actions needed for correct print.
     * @param message of type String - the spaces extracted from SelectSpaceMessage.
     */
    public void printSpaces(boolean[] actions, String message) {
        updateCli(actions, message);
        for (int i = 0; i < modelView.getSelectSpaces().size(); i++) {
            System.out.print("(" + modelView.getSelectSpaces().get(i).getRow() + "," +
                    modelView.getSelectSpaces().get(i).getColumn() + ")  ");
        }
        System.out.println();
    }
}