package it.polimi.ingsw.client.messages;

public class NumberOfPlayers implements Message {
    public final int playersNumber;

    public NumberOfPlayers(int players) {
        playersNumber = players;
    }
}
