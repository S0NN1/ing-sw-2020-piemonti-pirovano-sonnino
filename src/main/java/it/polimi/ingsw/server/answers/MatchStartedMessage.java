package it.polimi.ingsw.server.answers;

import java.util.HashMap;
import java.util.Map;

public class MatchStartedMessage implements Answer {
    private final Map<String, String> playerMapColor;
    private final Map<String, String> playerMapGod;

    public MatchStartedMessage() {
        playerMapColor = new HashMap<>();
        playerMapGod = new HashMap<>();
    }

    public void setPlayerMapColor(String player, String color) {
        playerMapColor.put(player, color);
    }

    public Map<String, String> getPlayerMapColor() {
        return playerMapColor;
    }

    public void setPlayerMapGod(String player, String god) {
        playerMapGod.put(player, god);
    }

    public Map<String, String> getPlayerMapGod() {
        return playerMapGod;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
