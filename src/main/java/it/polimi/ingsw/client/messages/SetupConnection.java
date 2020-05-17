package it.polimi.ingsw.client.messages;

public class SetupConnection implements Message {
    private final String nickname;

    public SetupConnection(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}