package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.model.player.PlayerColors;

public class Setup implements Message {
    private PlayerColors color;

    public Setup(PlayerColors color) {
        this.color=color;
    }

    public PlayerColors getColor() {
        return color;
    }
}
