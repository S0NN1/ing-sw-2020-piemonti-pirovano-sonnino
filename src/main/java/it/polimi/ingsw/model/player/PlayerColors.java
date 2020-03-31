package it.polimi.ingsw.model.player;

public enum PlayerColors {
    BLUE, RED, GREEN;

    public PlayerColors parseInput(String input) {
        return Enum.valueOf(PlayerColors.class, input.toUpperCase());
    }
}
