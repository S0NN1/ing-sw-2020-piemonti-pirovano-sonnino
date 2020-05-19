package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Printable;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.InvalidNicknameException;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.ChallengerMessages;
import it.polimi.ingsw.server.answers.GameError;
import it.polimi.ingsw.server.answers.ColorMessage;
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
    public static final String RED = "RED";
    private static final String BG_PURPLE = "BG_PURPLE";
    private final HashMap<String, String> nameMapColor = new HashMap<>();
    private final PrintStream output;
    private final Scanner input;
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final PropertyChangeSupport observers = new PropertyChangeSupport(this);
    private final DisplayCell[][] grid;
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
     * @param args the standard java main parameters.
     */
    public static void main(String[] args) {
        System.out.println(Constants.SANTORINI);
        System.out.println(Constants.authors);
        System.out.println(Constants.rules+ "\n");
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
                nickname = input.nextLine();
            } while (nickname == null);
            output.println(">You chose: " + nickname);
            output.println(">Is it ok? [y/n] ");
            output.print(">");
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
            output.println(nameMapColor.get(GREEN) + "Socket Connection setup completed!" + nameMapColor.get("RST"));
        } catch (DuplicateNicknameException | InvalidNicknameException e) {
            setup();
        }
        observers.addPropertyChangeListener("action", new ActionParser(connection, modelView));
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
                    rows = generateTypeOfLevel(i, j, level);
                    grid[i][j].setCellRows(k, rows[k]);
                }
            }
        }
    }

    private String[] generateTypeOfLevel(int i, int j, int level) {
        String[] rows;
        if (!modelView.getBoard().getGrid()[i][j].isDome()) {
            rows = generateRows(level, Printable.getLEVELS());
            if (modelView.getBoard().getGrid()[i][j].getColor() != null) {
                addWorkerToCell(nameMapColor.get(modelView.getBoard().getGrid()[i][j].getColor().toUpperCase()), rows, level, modelView.getBoard().getGrid()[i][j].getWorkerNum());
            }
        } else if (level == 3) {
            rows = generateRows(4, Printable.getLEVELS());
        } else {
            rows = generateRows(level, Printable.getLEVELSWITHDOME());
        }
        return rows;
    }

    private String[] generateRows(int level, String[] levels) {
        String[] rows;
        rows = levels[level].split("\n");
        return rows;
    }

    /**
     * Add worker to Printable cell
     *
     * @param color Worker color
     * @param rows  string
     * @param level int
     */
    private void addWorkerToCell(String color, String[] rows, int level, int type) {
        String[] temp = new String[3];
        String[] player = new String[3];
        int[] cellInfos = new int[]{level, type};
        int[][] indexes = new int[Constants.GRID_MAX_SIZE][1];
        String upperBody = "☻";
        String upperBody2 = "☺";
        String lowerBody = nameMapColor.get(WHITE) + "1";
        String lowerBody2 = nameMapColor.get(WHITE) + "2";
        String backgroundColor=BG_BLACK;
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
                } else {
                    j = 3;
                }
                HashMap<Integer, String[]> stringMap = createStringMap(temp, player);
                int[] counters = new int[]{i,j};
                insertPlayer(color, rows, cellInfos, stringMap, indexes, backgroundColor, counters);
            }
        } else {
            int j;
            if (level == 2) {
                j = 2;
            } else j = 0;
            for (int i = 0; i <= 2; i++) {
                HashMap<Integer, String[]> stringMap = createStringMap(temp, player);
                int[] counters = new int[]{i,j};
                insertPlayer(color, rows, cellInfos, stringMap, indexes, backgroundColor,counters);
            }
        }
    }

    private HashMap<Integer, String[]> createStringMap(String[] temp, String[] player) {
        HashMap<Integer, String[]> stringMap = new HashMap<>();
        stringMap.put(0, player);
        stringMap.put(1, temp);
        return stringMap;
    }

    private void insertPlayer(String color, String[] rows, int[] cellInfos, HashMap<Integer,String[]> stringMap, int[][] indexes, String backgroundColor, int[] counters) {
            if(counters[0]==2 && modelView.getActiveWorker()==cellInfos[1] && modelView.isTurnActive() && color.equalsIgnoreCase(nameMapColor.get(modelView.getColor().toUpperCase()))){
            color = Constants.ANSI_WHITE;
            backgroundColor = BG_PURPLE;
        }
        stringMap.get(1)[counters[0]] = rows[counters[0] + 4].substring(0, indexes[cellInfos[0]][0] - counters[1]) + color + nameMapColor.get(backgroundColor) +
                stringMap.get(0)[counters[0]] + nameMapColor.get(RST) + rows[counters[0] + 4].substring(indexes[cellInfos[0]][0] - counters[1] + 1);
        rows[counters[0] + 4] = stringMap.get(1)[counters[0]];
    }


    /**
     * Print board to the player
     *
     * @param grid printed board
     */
    private void printBoard(DisplayCell[][] grid) {
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.ROW_WAVE);
        String[] sideMenuRows;
        String[] guideMenuRows;
        sideMenuRows = buildSideMenu();
        guideMenuRows = buildSideHelp();
        int check = 0;
        System.out.print(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK + nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE + "  " + sideMenuRows[0]);
        for (int i = 0; i <= 4; i++) {
            for (int k = 0; k <= 10; k++) {
                System.out.print(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + "█" + nameMapColor.get("RST"));
                for (int j = 0; j <= 4; j++) {
                    System.out.print(grid[i][j].getCellRows(k) + nameMapColor.get(YELLOW) + "█" + nameMapColor.get("RST"));
                }
                check = insertMenus(sideMenuRows, guideMenuRows, check, k);
            }
            if (check == 1) {
                System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK + nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE + " " + guideMenuRows[11]);
            } else {
                System.out.println(Printable.COUPLE_ROW_WAVE + nameMapColor.get(YELLOW) + Printable.LINE_BLOCK + nameMapColor.get("RST") + Printable.COUPLE_ROW_WAVE);
            }
            check++;
        }
        System.out.println(Printable.ROW_WAVE);
        System.out.println(Printable.ROW_WAVE);
    }

    private int insertMenus(String[] sideMenuRows, String[] guideMenuRows, int check, int k) {
        if (check == 0) {
            insertSideMenuRows(sideMenuRows[k + 1]);
        } else if (check == 1) {
            insertGuideMenuRows(guideMenuRows, k);
        } else if (check == 2) {
            insertGuideMenuRows(guideMenuRows, 12);
            check++;
        }
        else if(check ==3 && guideMenuRows.length==14) {
            insertGuideMenuRows(guideMenuRows, 13);
            check++;
        } else {
            System.out.println(Printable.COUPLE_ROW_WAVE);
        }
        return check;
    }

    private void insertSideMenuRows(String sideMenuRow) {
        System.out.print(Printable.COUPLE_ROW_WAVE + "  " + sideMenuRow);
    }

    private void insertGuideMenuRows(String[] guideMenuRows, int i2) {
        System.out.println(Printable.COUPLE_ROW_WAVE + " " + guideMenuRows[i2]);
    }

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

    private String[] buildSideHelp() {
        String godDesc = modelView.getGodDesc();
        String[] sideMenuHelp;
        String menu =
                nameMapColor.get(YELLOW) + "HELP GUIDE" + nameMapColor.get(RST) + "\n" +
                        nameMapColor.get(YELLOW) + "GOD DESCRIPTION " + nameMapColor.get(RST) + "\n" +
                        godDesc + "\n" +
                        nameMapColor.get(YELLOW) + "SET <row1> <column1> <row2> <column2>" + nameMapColor.get(RST) + ": set workers on specified cells" + "\n" +
                        nameMapColor.get(YELLOW) + "SELECTWORKER <1/2>" + nameMapColor.get(RST) + ": select which worker you wanna play" + "\n" +
                        nameMapColor.get(YELLOW) + "MOVE (no arguments)" + nameMapColor.get(RST) + ": print your possible move actions, needed to be run before MOVE with arguments, except for the first command" + "\n" +
                        nameMapColor.get(YELLOW) + "MOVE <row> <column>" + nameMapColor.get(RST) + ": move worker to specified cell (if permitted)" + "\n" +
                        nameMapColor.get(YELLOW) + "BUILD (no arguments)" + nameMapColor.get(RST) + ": print your possible build actions, needed to be run before BUILD with arguments" + "\n" +
                        nameMapColor.get(YELLOW) + "BUILD <row> <column>" + nameMapColor.get(RST) + ": build a block on specified cell (if permitted)" + "\n" +
                        nameMapColor.get(YELLOW) + "PLACEDOME (no arguments)" + nameMapColor.get(RST) + ": print your possible build actions in order to place a dome [ATLAS ONLY]" + "\n" +
                        nameMapColor.get(YELLOW) + "PLACEDOME <row> <column>" + nameMapColor.get(RST) + ": build dome on specified cell (if permitted) [ATLAS ONLY]" + "\n" +
                        nameMapColor.get(YELLOW) + "END" + nameMapColor.get(RST) + ": end turn";
        sideMenuHelp = menu.split("\n");
        return sideMenuHelp;
    }

    private StringBuilder addSpaces(int max, String s) {
        if (max - s.length() == 0) {
            return new StringBuilder();
        } else {
            StringBuilder y = new StringBuilder();
            y.append(" ".repeat(Math.max(0, max - s.length())));
            return y;
        }
    }

    private StringBuilder addBlocks(int max) {
        StringBuilder s = new StringBuilder();
        s.append("█".repeat(Math.max(0, max)));
        return s;
    }

    private StringBuilder addScores(int max) {
        StringBuilder s = new StringBuilder();
        s.append("-".repeat(Math.max(0, max)));
        return s;
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
                String cmd = input.nextLine();
                selection = Integer.parseInt(cmd);
                break;
            } catch (NumberFormatException e) {
                output.println(nameMapColor.get(RED) + "Invalid parameter, it must be a numeric value." + nameMapColor.get("RST"));
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
                    return;
                } else {
                    output.println("Color not available!");
                    greaterThan();
                }
            } catch (IllegalArgumentException e) {
                output.println("Invalid input! Please provide one of the accepted colors.");
                greaterThan();
            }
        }
    }

    private void greaterThan() {
        output.print(">");
    }

    /**
     * Handles an error received from the server, following a user action or saying him he cannot perform any action in that moment.
     *
     * @param error the error received from the server.
     */
    public void errorHandling(GameError error) {
        switch (error.getError()) {
            case CELLOCCUPIED -> {
                output.println(nameMapColor.get(RED) + "The following cells are already occupied, please choose them again." + nameMapColor.get("RST"));
                error.getCoordinates().forEach(n -> output.print(nameMapColor.get(RED) + Arrays.toString(n) + ", " + nameMapColor.get("RST")));
                output.println();
                greaterThan();
            }
            case INVALIDINPUT -> {
                if (error.getMessage() != null) {
                    output.println(nameMapColor.get(RED) + error.getMessage() + nameMapColor.get(RST));
                    greaterThan();
                }
                else {
                    output.println(nameMapColor.get(RED) + "Input error, please try again!" + nameMapColor.get(RST));
                    greaterThan();
                }
                modelView.setTurnActive(true);
            }
            case WORKERBLOCKED -> {
                System.err.println("Selected worker is blocked, select the other one!");
                greaterThan();
            }
            default -> {
                output.println("Generic error!");
                greaterThan();
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
                output.println(nameMapColor.get(GREEN) + ((ColorMessage) modelView.getServerAnswer()).getMessage() + "\nRemaining:" + nameMapColor.get("RST"));
                ((ColorMessage) modelView.getServerAnswer()).getRemaining().forEach(n -> output.print(n + ", "));
                output.print("\n");
                chooseColor(((ColorMessage) modelView.getServerAnswer()).getRemaining());
            }
            case "GodRequest" -> {
                ChallengerMessages req = (ChallengerMessages) modelView.getServerAnswer();
                godRequest(req);
            }
            case "WorkerPlacement" -> {
                firstUpdateCli();
                String[] msg = modelView.getServerAnswer().getMessage().toString().split(" ");
                output.println(Constants.ANSI_UNDERLINE + msg[0] + nameMapColor.get(RST) + " choose your workers position by typing" +
                                nameMapColor.get(YELLOW) + " SET <row1 <col1> <row2> <col2> " + nameMapColor.get(RST) + "where 1 and 2 indicates worker number.");
                output.print(">");
                modelView.activateInput();
            }
            default -> output.println("Nothing to do");
        }
    }

    private void godRequest(ChallengerMessages req) {
        if (req.isStartingPlayer() && req.getPlayers() != null) {
            output.println(req.getMessage().split(" ")[0] + " choose the starting player by typing" +
                    nameMapColor.get(YELLOW) + " STARTER <number-of-player>" + nameMapColor.get(RST));
            req.getPlayers().forEach(n -> output.println(req.getPlayers().indexOf(n) + ": " + n + ","));
        } else if (req.getSelectable() != null) {
            output.println("\n" + req.getMessage());
            req.getSelectable().forEach(n -> output.println("\n" + n.toString() + "\n" + n.godsDescription()));
            output.println("\nSelect your god by typing" + nameMapColor.get(YELLOW) + " choose <god-name>" + nameMapColor.get(RST));
            output.print(">");
        } else if (req.getGodList() != null) {
            output.println();
            req.getGodList().forEach(n -> output.print(n + ", "));
            output.println();
        } else {
            if(req.getMessage().contains("ADDGOD") || req.getMessage().contains("Description") || req.getMessage().contains("been added")) {
                output.println();
            }
            if(req.getMessage().contains("<god-name>")){
                String temp[]=req.getMessage().split("\n");
                for(int i=0; i<9; i++){
                output.print(temp[i] + "\n");
            }
                output.println("\nSelect your god by typing" + nameMapColor.get(YELLOW) + " choose <god-name>" + nameMapColor.get(RST));
                greaterThan();
                modelView.activateInput();
                return;
            }
            output.println(req.getMessage());
            greaterThan();
        }
        modelView.activateInput();
        if (modelView.getStarted() < 3) modelView.setStarted(3);
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
            case "gameError" -> errorHandling((GameError) evt.getNewValue());
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
                output.println(nameMapColor.get(RED) + "Application will now close..." + nameMapColor.get("RST"));
                System.exit(0);
            }
            case "noPossibleMoves" -> System.err.println("No possible moves!");
            case "boardUpdate" -> {
                if(evt.getOldValue().getClass().isArray()) {
                    boolean[] checkers = ((boolean[]) evt.getOldValue());
                    String message;
                    if(evt.getNewValue()==null){
                        message=null;
                    }
                    else message = evt.getNewValue().toString();
                    updateCli(checkers[0], checkers[1], checkers[2], message);
                }
            }
            case "firstBoardUpdate" -> firstUpdateCli();
            case "selectWorker" -> selectWorker();   //TODO
            case "end" -> end((String)evt.getNewValue());
            case "select" -> {
                if (evt.getOldValue().getClass().isArray()) {
                    boolean[] checkers = ((boolean[]) evt.getOldValue());
                    String message;
                    if(evt.getNewValue()==null){
                        message=null;
                    }
                    else message = evt.getNewValue().toString();
                    printSpaces(checkers[0], checkers[1], checkers[2], message);
                }
            }
            case "win" -> {
                output.println(nameMapColor.get(RED) + "YOU WIN!" + nameMapColor.get(RST));
                System.exit(0);
            }
            case "lose" -> {
                output.println(nameMapColor.get(RED) + "YOU LOSE!" + nameMapColor.get(RST));
                output.println(nameMapColor.get(YELLOW) + "Player " + evt.getNewValue() + " has won." + nameMapColor.get(RST));
                System.exit(0);
            }
            case "singleLost" -> System.err.println("All workers blocked, YOU LOSE!");
            case "otherLost" -> otherPlayerLost(evt);
            default -> output.println("Unrecognized answer");
        }
    }


    private void otherPlayerLost(PropertyChangeEvent evt) {
        clearScreen();
        boardUpdater(grid);
        printBoard(grid);
        output.println(nameMapColor.get(YELLOW) + "Player " + evt.getNewValue() + " has lost." + nameMapColor.get(RST));
    }

    private void end(String message) {
        System.out.print("\r" + message);
    }

    public void selectWorker() {
        System.out.print("\r  • SELECTWORKER <1/2>\n");
        greaterThan();
    }

    public void printMenu(boolean move, boolean build, boolean end, String message) throws InterruptedException {
        String active;
        String atlas;
        if (modelView.getGamePhase() != 0) {
            if (!modelView.isTurnActive()) {
                active = " NOT ";
            } else active = "";
            System.out.println(active + " YOUR TURN");
        }
        if (modelView.getGod().equalsIgnoreCase("ATLAS")) {
            atlas = "/PLACEDOME\n";
        } else atlas = "";
        TimeUnit.MILLISECONDS.sleep(500);
        if(modelView.isTurnActive()) {
            output.print(move ? " • MOVE\n":"");
            output.print(build ? " • BUILD" + atlas + "\n":"");
            output.print(end ? " • END\n":"");
            if(message!=null){
                output.println(message);
            }
            greaterThan();
        }
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

    public void updateCli(boolean move, boolean build, boolean end, String message) {
        clearScreen();
        boardUpdater(grid);
        printBoard(grid);
        try {
            printMenu(move, build, end, message);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method printSpaces ...
     *
     * @param move of type boolean
     * @param build of type boolean
     * @param end of type boolean
     * @param message of type String
     */
    public void printSpaces(boolean move, boolean build, boolean end, String message) {
        updateCli(move, build, end, message);
        for (int i = 0; i < modelView.getSelectSpaces().size(); i++) {
            System.out.print("(" + modelView.getSelectSpaces().get(i).getX() + "," + modelView.getSelectSpaces().get(i).getY() + ")  ");
        }
        System.out.println();
    }


}