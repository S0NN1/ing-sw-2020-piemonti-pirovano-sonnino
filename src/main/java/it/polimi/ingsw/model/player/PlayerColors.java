package it.polimi.ingsw.model.player;

import java.util.ArrayList;

public enum PlayerColors {
    BLUE, RED, GREEN;
    private static ArrayList<PlayerColors> available = new ArrayList<>();

    public static void reset() {
        available.clear();
        available.add(BLUE);
        available.add(RED);
        available.add(GREEN);
    }

    public static void choose(PlayerColors color) {
        available.remove(color);
    }

    public static boolean isChosen(PlayerColors color) {
        return !(available.contains(color));
    }

    public static ArrayList<PlayerColors> notChosen() {
        return available;
    }

    public static PlayerColors parseInput(String input) {
        return Enum.valueOf(PlayerColors.class, input.toUpperCase());
    }
}
