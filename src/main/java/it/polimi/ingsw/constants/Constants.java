package it.polimi.ingsw.constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class contains the most significant constants for an execution of the game.
 * @author Luca Pirovano, Alice Piemonti, Sonny
 */
public class Constants {

    private Constants() {}

    //match constants
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 3;
    public static final String ANSI_UNDERLINE = "\u001B[4m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BACKGROUND_BLACK="\u001B[40m";
    public static final String ANSI_BACKGROUND_PURPLE = "\u001B[45m";
    public static final String SANTORINI ="\n" +
            "          _____                    _____                    _____                _____                   _______                   _____                    _____                    _____                    _____          \n" +
            "         /\\    \\                  /\\    \\                  /\\    \\              /\\    \\                 /::\\    \\                 /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\         \n" +
            "        /::\\    \\                /::\\    \\                /::\\____\\            /::\\    \\               /::::\\    \\               /::\\    \\                /::\\    \\                /::\\____\\                /::\\    \\        \n" +
            "       /::::\\    \\              /::::\\    \\              /::::|   |            \\:::\\    \\             /::::::\\    \\             /::::\\    \\               \\:::\\    \\              /::::|   |                \\:::\\    \\       \n" +
            "      /::::::\\    \\            /::::::\\    \\            /:::::|   |             \\:::\\    \\           /::::::::\\    \\           /::::::\\    \\               \\:::\\    \\            /:::::|   |                 \\:::\\    \\      \n" +
            "     /:::/\\:::\\    \\          /:::/\\:::\\    \\          /::::::|   |              \\:::\\    \\         /:::/~~\\:::\\    \\         /:::/\\:::\\    \\               \\:::\\    \\          /::::::|   |                  \\:::\\    \\     \n" +
            "    /:::/__\\:::\\    \\        /:::/__\\:::\\    \\        /:::/|::|   |               \\:::\\    \\       /:::/    \\:::\\    \\       /:::/__\\:::\\    \\               \\:::\\    \\        /:::/|::|   |                   \\:::\\    \\    \n" +
            "    \\:::\\   \\:::\\    \\      /::::\\   \\:::\\    \\      /:::/ |::|   |               /::::\\    \\     /:::/    / \\:::\\    \\     /::::\\   \\:::\\    \\              /::::\\    \\      /:::/ |::|   |                   /::::\\    \\   \n" +
            "  ___\\:::\\   \\:::\\    \\    /::::::\\   \\:::\\    \\    /:::/  |::|   | _____        /::::::\\    \\   /:::/____/   \\:::\\____\\   /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /:::/  |::|   | _____    ____    /::::::\\    \\  \n" +
            " /\\   \\:::\\   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/   |::|   |/\\    \\      /:::/\\:::\\    \\ |:::|    |     |:::|    | /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\  /:::/   |::|   |/\\    \\  /\\   \\  /:::/\\:::\\    \\ \n" +
            "/::\\   \\:::\\   \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\/:: /    |::|   /::\\____\\    /:::/  \\:::\\____\\|:::|____|     |:::|    |/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\/:: /    |::|   /::\\____\\/::\\   \\/:::/  \\:::\\____\\\n" +
            "\\:::\\   \\:::\\   \\::/    /\\::/    \\:::\\  /:::/    /\\::/    /|::|  /:::/    /   /:::/    \\::/    / \\:::\\    \\   /:::/    / \\::/   |::::\\  /:::|____|\\:::\\  /:::/    \\::/    /\\::/    /|::|  /:::/    /\\:::\\  /:::/    \\::/    /\n" +
            " \\:::\\   \\:::\\   \\/____/  \\/____/ \\:::\\/:::/    /  \\/____/ |::| /:::/    /   /:::/    / \\/____/   \\:::\\    \\ /:::/    /   \\/____|:::::\\/:::/    /  \\:::\\/:::/    / \\/____/  \\/____/ |::| /:::/    /  \\:::\\/:::/    / \\/____/ \n" +
            "  \\:::\\   \\:::\\    \\               \\::::::/    /           |::|/:::/    /   /:::/    /             \\:::\\    /:::/    /          |:::::::::/    /    \\::::::/    /                   |::|/:::/    /    \\::::::/    /          \n" +
            "   \\:::\\   \\:::\\____\\               \\::::/    /            |::::::/    /   /:::/    /               \\:::\\__/:::/    /           |::|\\::::/    /      \\::::/____/                    |::::::/    /      \\::::/____/           \n" +
            "    \\:::\\  /:::/    /               /:::/    /             |:::::/    /    \\::/    /                 \\::::::::/    /            |::| \\::/____/        \\:::\\    \\                    |:::::/    /        \\:::\\    \\           \n" +
            "     \\:::\\/:::/    /               /:::/    /              |::::/    /      \\/____/                   \\::::::/    /             |::|  ~|               \\:::\\    \\                   |::::/    /          \\:::\\    \\          \n" +
            "      \\::::::/    /               /:::/    /               /:::/    /                                  \\::::/    /              |::|   |                \\:::\\    \\                  /:::/    /            \\:::\\    \\         \n" +
            "       \\::::/    /               /:::/    /               /:::/    /                                    \\::/____/               \\::|   |                 \\:::\\____\\                /:::/    /              \\:::\\____\\        \n" +
            "        \\::/    /                \\::/    /                \\::/    /                                      ~~                      \\:|   |                  \\::/    /                \\::/    /                \\::/    /        \n" +
            "         \\/____/                  \\/____/                  \\/____/                                                                \\|___|                   \\/____/                  \\/____/                  \\/____/         \n" +
            "                                                                                                                                                                                                                             \n";


    //server constants
    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 2500;

    public static final int GRID_MAX_SIZE = 5;
    public static final int GRID_MIN_SIZE = 0;
    /**
     * Arraylist containing gods with move action with turnPhase != 0
     */
    private static final List<String> MOVE_PHASE_GODS = new ArrayList<>();

    public static List<String> getMovePhaseGods() {
        MOVE_PHASE_GODS.add("ARTEMIS");
        MOVE_PHASE_GODS.add("PROMETHEUS");
        return MOVE_PHASE_GODS;
    }
    /**
     * Arraylist containing gods with build action with turnPhase != 1
     */
    private static final List<String> BUILD_PHASE_GODS = new ArrayList<>();

    public static List<String> getBuildPhaseGods() {
        BUILD_PHASE_GODS.add("DEMETER");
        BUILD_PHASE_GODS.add("HEPHAESTUS");
        BUILD_PHASE_GODS.add("PROMETHEUS");
        BUILD_PHASE_GODS.add("ARTEMIS");
        return BUILD_PHASE_GODS;
    }

    /**
     * Arraylist containing gods with move action to cell occupied
     */
    private static final List<String> MOVE_TO_CELL_OCCUPIED_GODS = new ArrayList<>();

    public static List<String> getMoveToCellOccupiedGods() {
        MOVE_TO_CELL_OCCUPIED_GODS.add("APOLLO");
        MOVE_TO_CELL_OCCUPIED_GODS.add("MINOTAUR");
        return MOVE_TO_CELL_OCCUPIED_GODS;
    }


    //datetime
    public static String getInfo() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " INFO: ");
    }

    public static String getErr() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ERR: ");
    }


}