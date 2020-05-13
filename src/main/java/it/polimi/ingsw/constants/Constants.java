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
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BACKGROUND_BLACK="\u001B[40m";
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

    public int gridMaxSize = 5;
    public int gridMinSize = 0;
    /**
     * Arraylist containing gods with move action with turnPhase != 0
     */
    public static final List<String> movePhaseGods = new ArrayList<>(){{
        add("ARTEMIS");
        add("PROMETHEUS");
    }};
    /**
     * Arraylist containing gods with build action with turnPhase != 1
     */
    public static final List<String> buildPhaseGods = new ArrayList<>(){{
        add("DEMETER");
        add("HEPHAESTUS");
        add("PROMETHEUS");
    }};

    /**
     * Arraylist containing gods with move action to cell occupied
     */
    public static final List<String> moveToCellOccupiedGods = new ArrayList<>(){{
        add("APOLLO");
        add("MINOTAUR");
    }};


    //datetime
    public static String getInfo() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " INFO: ");
    }

    public static String getErr() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ERR: ");
    }


}