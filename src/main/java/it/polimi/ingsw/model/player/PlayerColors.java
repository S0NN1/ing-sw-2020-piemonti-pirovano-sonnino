package it.polimi.ingsw.model.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class PlayerColors is an enumeration containing the match available colors and some parsing methods.
 *
 * @author Luca Pirovano
 */
public enum PlayerColors {
    BLUE, RED, GREEN;

    private static final ArrayList<PlayerColors> available = new ArrayList<>();

    /**
     * Method reset restores the color list to the original ones.
     */
    public static void reset() {
        available.clear();
        available.add(BLUE);
        available.add(RED);
        available.add(GREEN);
    }

    /**
     * Method choose removes the chosen color from the available colors list.
     *
     * @param color of type PlayerColors - the chosen color.
     */
    public static void choose(PlayerColors color) {
        available.remove(color);
    }

    /**
     * Method isChosen returns if the selected color has already been chosen.
     *
     * @param color of type PlayerColors - the chosen color.
     * @return boolean true if the color has already been chosen.
     */
    public static boolean isChosen(PlayerColors color) {
        return !(available.contains(color));
    }

    /**
     * Method notChosen returns the list of available colors.
     * @return List&lt;PlayerColors&gt; - the available colors.
     */
    public static List<PlayerColors> notChosen() {
        return available;
    }

    /**
     * Method parseInput parses a type String input for color choosing.
     *
     * @param input of type String - the chosen color.
     * @return PlayerColors - the Enum value of the desired color.
     */
    public static PlayerColors parseInput(String input) {
        return Enum.valueOf(PlayerColors.class, input.toUpperCase());
    }
}
