package it.polimi.ingsw.client.messages;

/**
 * Message sent by the client to the server, setups connection for the player with provided nickname.
 * @author Luca Pirovano
 * @see Message
 */
public class SetupConnection implements Message {
    private final String nickname;

    /**
     * Constructor SetupConnection creates a new SetupConnection instance.
     *
     * @param nickname of type String the nickname of the player.
     */
    public SetupConnection(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Method getNickname returns the nickname of this SetupConnection object.
     *
     *
     *
     * @return the nickname (type String) of this SetupConnection object.
     */
    public String getNickname() {
        return nickname;
    }
}