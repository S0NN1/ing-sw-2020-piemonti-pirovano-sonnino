package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.model.player.PlayerColors;

public class SetupConnection implements Message {
    private String nickname;

    public SetupConnection(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}