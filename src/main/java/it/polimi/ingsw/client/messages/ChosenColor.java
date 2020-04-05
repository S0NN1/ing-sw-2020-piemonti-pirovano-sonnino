package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.model.player.PlayerColors;

public class ChosenColor implements Message {
    private PlayerColors color;

    public ChosenColor(PlayerColors color) {
        this.color=color;
    }

    public PlayerColors getColor() {
        return color;
    }
}
