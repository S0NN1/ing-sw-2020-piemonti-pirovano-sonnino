package it.polimi.ingsw.server.answers;

import java.util.HashMap;
import java.util.Map;

/**
 * Class MatchStartedMessage is a Answer used for identifying the start of the match.
 *
 * @author Nicolò Sonnino
 * @see Answer
 */
public class MatchStartedMessage implements Answer {
    private final Map<String, String> playerMapColor;
    private final Map<String, String> playerMapGod;

    /**
     * Constructor MatchStartedMessage creates a new MatchStartedMessage instance.
     */
    public MatchStartedMessage() {
        playerMapColor = new HashMap<>();
        playerMapGod = new HashMap<>();
    }

    /**
     * Method setPlayerMapColor maps player to right color
     *
     * @param player of type String - the player's nickname.
     * @param color of type String - the player's color.
     */
    public void setPlayerMapColor(String player, String color) {
        playerMapColor.put(player, color);
    }

    /**
     * Method getPlayerMapColor returns the playerMapColor of this MatchStartedMessage object.
     *
     *
     *
     * @return the playerMapColor (type Maps&lt;String, String&gt;) of this MatchStartedMessage object.
     */
    public Map<String, String> getPlayerMapColor() {
        return playerMapColor;
    }

    /**
     * Method setPlayerMapGod maps player to god.
     *
     * @param player of type String - the player's nickname.
     * @param god of type String - the player's god card.
     */
    public void setPlayerMapGod(String player, String god) {
        playerMapGod.put(player, god);
    }

    /**
     * Method getPlayerMapGod returns the playerMapGod of this MatchStartedMessage object.
     *
     *
     *
     * @return the playerMapGod (type Maps&lt;String, String&gt;) of this MatchStartedMessage object.
     */
    public Map<String, String> getPlayerMapGod() {
        return playerMapGod;
    }

    /**
     * Method getMessage returns the message of this WorkerPlacement object.
     *
     *
     *
     * @return the message (type Object) of this WorkerPlacement object.
     * @see Answer#getMessage()
     */
    @Override
    public Object getMessage() {
        return null;
    }
}
