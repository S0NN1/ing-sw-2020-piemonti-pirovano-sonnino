package it.polimi.ingsw.constants;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Constants class contains the most significant constants used in the project.
 * @author Luca Pirovano, Alice Piemonti, Nicolò Sonnino
 */
public class Constants {


    private static final String CHARON = "CHARON";
    private static final String ATLAS = "ATLAS";
    private static final String PLACEDOME = "PLACEDOME";
    private static final String ARES = "ARES";
    public static final String ARTEMIS = "ARTEMIS";
    public static final String PROMETHEUS = "PROMETHEUS";
    public static final String DEMETER = "DEMETER";
    public static final String HEPHAESTUS = "HEPHAESTUS";
    public static final String HESTIA = "HESTIA";
    public static final String TRITON = "TRITON";
    public static final String APOLLO = "APOLLO";
    public static final String MINOTAUR = "MINOTAUR";
    public static final String ZEUS = "ZEUS";
    public static final String FORCE_WORKER = "FORCEWORKER";
    public static final String REMOVE_LEVEL = "REMOVELEVEL";


    /**
     * Constructor Constants creates a new Constants instance.
     */
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
    public static final String AUTHORS = "  _                   _ _                  _                            _   _     _                         _                                          _           _       _                         _             \n" +
          " | |                 | (_)                (_)                          | | (_)   | |                       (_)                                        (_)         | |     ( )                       (_)            \n" +
          " | |__  _   _    __ _| |_  ___ ___   _ __  _  ___ _ __ ___   ___  _ __ | |_ _    | |_   _  ___ __ _   _ __  _ _ __ _____   ____ _ _ __   ___     _ __  _  ___ ___ | | ___ |/   ___  ___  _ __  _ __  _ _ __   ___  \n" +
          " | '_ \\| | | |  / _` | | |/ __/ _ \\ | '_ \\| |/ _ \\ '_ ` _ \\ / _ \\| '_ \\| __| |   | | | | |/ __/ _` | | '_ \\| | '__/ _ \\ \\ / / _` | '_ \\ / _ \\   | '_ \\| |/ __/ _ \\| |/ _ \\    / __|/ _ \\| '_ \\| '_ \\| | '_ \\ / _ \\ \n" +
          " | |_) | |_| | | (_| | | | (_|  __/ | |_) | |  __/ | | | | | (_) | | | | |_| |_  | | |_| | (_| (_| | | |_) | | | | (_) \\ V / (_| | | | | (_) |  | | | | | (_| (_) | | (_) |   \\__ \\ (_) | | | | | | | | | | | (_) |\n" +
          " |_.__/ \\__, |  \\__,_|_|_|\\___\\___| | .__/|_|\\___|_| |_| |_|\\___/|_| |_|\\__|_( ) |_|\\__,_|\\___\\__,_| | .__/|_|_|  \\___/ \\_/ \\__,_|_| |_|\\___( ) |_| |_|_|\\___\\___/|_|\\___/    |___/\\___/|_| |_|_| |_|_|_| |_|\\___/ \n" +
          "         __/ |                      | |                                      |/                      | |                                    |/                                                                     \n" +
          "        |___/                       |_|                                                              |_|                                                                                                         ";

    public static final String RULES ="\nView full rules here: https://cdn.1j1ju.com/medias/fc/ec/5d-santorini-rulebook.pdf";
    //server constants
    private static String ADDRESS;
    private static int PORT;

    public static final int GRID_MAX_SIZE = 5;
    public static final int GRID_MIN_SIZE = 0;

    private static final HashMap<String, String> godMapCustomAction = new HashMap<>(){{
        put(CHARON, FORCE_WORKER);
        put(ARES, REMOVE_LEVEL);
        put(ATLAS, PLACEDOME);
    }};

    /**
     * Method getGodMapCustomAction returns the godMapCustomAction of this Constants object.
     *
     *
     *
     * @return the godMapCustomAction (type HashMap<String, String>) of this Constants object.
     */
    public static HashMap<String, String> getGodMapCustomAction() {
        return godMapCustomAction;
    }
private static final List<String> SPECIAL_BUILD_GODS = Collections.singletonList(ATLAS);

    /**
     * Method getSpecialBuildGods returns the specialBuildGods of this Constants object.
     *
     *
     *
     * @return the specialBuildGods (type List<String>) of this Constants object.
     */
    public static List<String> getSpecialBuildGods() {
        return SPECIAL_BUILD_GODS;
    }

    private static final List<String> END_ACTION_GODS = Collections.singletonList(ARES);

    /**
     * Method getEndActionGods returns the endActionGods of this Constants object.
     *
     *
     *
     * @return the endActionGods (type List<String>) of this Constants object.
     */
    public static List<String> getEndActionGods() {
        return END_ACTION_GODS;
    }


    private static final List<String> START_ACTION_GODS = Collections.singletonList(CHARON);

    /**
     * Method getStartActionGods returns the startActionGods of this Constants object.
     *
     *
     *
     * @return the startActionGods (type List<String>) of this Constants object.
     */
    public static List<String> getStartActionGods() {
        return START_ACTION_GODS;
    }

    private static final List<String> BUILD_SAME_BLOCK_GODS = Collections.singletonList(ZEUS);

    /**
     * Method getBuildSameBlockGods returns the buildSameBlockGods of this Constants object.
     *
     *
     *
     * @return the buildSameBlockGods (type List<String>) of this Constants object.
     */
    public static List<String> getBuildSameBlockGods() {
        return BUILD_SAME_BLOCK_GODS;
    }

    private static final List<String> DOUBLE_MOVE_GODS = Arrays.asList(ARTEMIS, TRITON);

    /**
     * Method getDoubleMoveGods returns the doubleMoveGods of this Constants object.
     *
     *
     *
     * @return the doubleMoveGods (type List<String>) of this Constants object.
     */
    public static List<String> getDoubleMoveGods() {
        return DOUBLE_MOVE_GODS;
    }

    private static final List<String> ALTERNATE_PHASE_GODS = Collections.singletonList(PROMETHEUS);

    /**
     * Method getAlternatePhaseGods returns the alternatePhaseGods of this Constants object.
     *
     *
     *
     * @return the alternatePhaseGods (type List<String>) of this Constants object.
     */
    public static List<String> getAlternatePhaseGods() { return ALTERNATE_PHASE_GODS; }

    private static final List<String> DOUBLE_BUILD_GODS = Arrays.asList(DEMETER, HEPHAESTUS, HESTIA);

    /**
     * Method getDoubleBuildGods returns the doubleBuildGods of this Constants object.
     *
     *
     *
     * @return the doubleBuildGods (type List<String>) of this Constants object.
     */
    public static List<String> getDoubleBuildGods() {
        return DOUBLE_BUILD_GODS;
    }

    /**
     * Arraylist containing gods with move action with turnPhase != 0
     */
    private static final List<String> MOVE_PHASE_GODS = Arrays.asList(ARTEMIS, PROMETHEUS, TRITON);

    /**
     * Method getMovePhaseGods returns the movePhaseGods of this Constants object.
     *
     *
     *
     * @return the movePhaseGods (type List<String>) of this Constants object.
     */
    public static List<String> getMovePhaseGods() {
        return MOVE_PHASE_GODS;
    }
    /**
     * Arraylist containing gods with build action with turnPhase != 1
     */
    private static final List<String> BUILD_PHASE_GODS = Arrays.asList(DEMETER, HEPHAESTUS, PROMETHEUS, ARTEMIS, HESTIA, TRITON);

    /**
     * Method getBuildPhaseGods returns the buildPhaseGods of this Constants object.
     *
     *
     *
     * @return the buildPhaseGods (type List<String>) of this Constants object.
     */
    public static List<String> getBuildPhaseGods() {
        return BUILD_PHASE_GODS;
    }

    /**
     * Arraylist containing gods with move action to cell occupied
     */
    private static final List<String> MOVE_TO_CELL_OCCUPIED_GODS = Arrays.asList(APOLLO, MINOTAUR, CHARON);

    /**
     * Method getMoveToCellOccupiedGods returns the moveToCellOccupiedGods of this Constants object.
     *
     *
     *
     * @return the moveToCellOccupiedGods (type List<String>) of this Constants object.
     */
    public static List<String> getMoveToCellOccupiedGods() {
        return MOVE_TO_CELL_OCCUPIED_GODS;
    }


    /**
     * Method getInfo returns the info of this Constants object.
     *
     *
     *
     * @return the info (type String) of this Constants object.
     */
    //datetime
    public static String getInfo() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " INFO: ");
    }

    /**
     * Method getErr returns the err of this Constants object.
     *
     *
     *
     * @return the err (type String) of this Constants object.
     */
    public static String getErr() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ERR: ");
    }

    /**
     * Method setADDRESS sets the ADDRESS of this Constants object.
     *
     *
     *
     * @param ADDRESS the ADDRESS of this Constants object.
     *
     */
    public static void setADDRESS(String ADDRESS) {
        Constants.ADDRESS = ADDRESS;
    }

    /**
     * Method setPORT sets the PORT of this Constants object.
     *
     *
     *
     * @param PORT the PORT of this Constants object.
     *
     */
    public static void setPORT(int PORT) {
        Constants.PORT = PORT;
    }

    /**
     * Method getADDRESS returns the ADDRESS of this Constants object.
     *
     *
     *
     * @return the ADDRESS (type String) of this Constants object.
     */
    public static String getADDRESS() {
        return ADDRESS;
    }

    /**
     * Method getPORT returns the PORT of this Constants object.
     *
     *
     *
     * @return the PORT (type int) of this Constants object.
     */
    public static int getPORT() {
        return PORT;
    }
}