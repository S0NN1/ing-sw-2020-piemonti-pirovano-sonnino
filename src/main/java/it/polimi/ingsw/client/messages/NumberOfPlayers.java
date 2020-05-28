package it.polimi.ingsw.client.messages;

/**
 * Message sent by the client to the server containing the number of the players in the game.
 * @author Luca Pirovano
 * @see Message
 */
public class NumberOfPlayers implements Message {
    public final int playersNumber;

    /**
     * Constructor NumberOfPlayers creates a new NumberOfPlayers instance.
     *
     * @param players of type int - the number of players.
     */
    public NumberOfPlayers(int players) {
        playersNumber = players;
    }
}
