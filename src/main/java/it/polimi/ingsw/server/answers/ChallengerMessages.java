package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.Card;

import java.util.List;

/**
 * Class ChallengerMessages is an Action used for selecting gods during Challenger's phase.
 *
 * @author Luca Pirovano
 * @see Answer
 */
public class ChallengerMessages implements Answer {
    private final String message;
    private final List<String> godList;
    private final List<Card> selectable;
    private final boolean startingPlayer;
    private final List<String> players;
    private final String chosenGod;
    private final String godDesc;

    /**
     * Constructor ChallengerMessages creates a new ChallengerMessages instance.
     *
     * @param message of type String - the message to be displayed.
     */
    public ChallengerMessages(String message) {
        this.message = message;
        this.godList = null;
        startingPlayer = false;
        players = null;
        selectable = null;
        chosenGod = null;
        godDesc = null;
    }

    /**
     * Constructor ChallengerMessages creates a new ChallengerMessages instance.
     *
     * @param message of type String - the message to be displayed.
     * @param startingPlayer of type boolean - the boolean indicating if client is the starting player.
     * @param players of type List<String> - the list of players' nicknames.
     */
    public ChallengerMessages(String message, boolean startingPlayer, List<String> players) {
        this.message = message;
        this.godList = null;
        this.startingPlayer = startingPlayer;
        this.players = players;
        selectable = null;
        chosenGod = null;
        godDesc = null;
    }

    /**
     * Constructor ChallengerMessages creates a new ChallengerMessages instance.
     *
     * @param list of type List<String> - the list of god cards.
     */
    public ChallengerMessages(List<String> list) {
        this.godList = list;
        this.message = null;
        startingPlayer = false;
        players = null;
        selectable = null;
        chosenGod = null;
        godDesc = null;
    }

    /**
     * Constructor ChallengerMessages creates a new ChallengerMessages instance.
     *
     * @param message of type String - the message to be displayed.
     * @param selectable of type List<Card> - the list of selectable gods.
     */
    public ChallengerMessages(String message, List<Card> selectable) {
        this.godList = null;
        this.message = message;
        startingPlayer = false;
        players = null;
        this.selectable = selectable;
        chosenGod = null;
        godDesc = null;
    }

    /**
     * Constructor ChallengerMessages creates a new ChallengerMessages instance.
     *
     * @param chosenGod of type Card - the chosen god card.
     */
    public ChallengerMessages(Card chosenGod) {
        this.godList = null;
        this.message = null;
        startingPlayer = false;
        players = null;
        this.selectable = null;
        this.chosenGod = chosenGod.name();
        this.godDesc = chosenGod.godsDescription();
    }

    /**
     * Method getGodList returns the godList of this ChallengerMessages object.
     *
     *
     *
     * @return the godList (type List<String>) of this ChallengerMessages object.
     */
    public List<String> getGodList() {
        return godList;
    }

    /**
     * Method getSelectable returns the selectable of this ChallengerMessages object.
     *
     *
     *
     * @return the selectable (type List<Card>) of this ChallengerMessages object.
     */
    public List<Card> getSelectable() {
        return selectable;
    }

    /**
     * Method isStartingPlayer returns the startingPlayer of this ChallengerMessages object.
     *
     *
     *
     * @return the startingPlayer (type boolean) of this ChallengerMessages object.
     */
    public boolean isStartingPlayer() {
        return startingPlayer;
    }

    /**
     * Method getPlayers returns the players of this ChallengerMessages object.
     *
     *
     *
     * @return the players (type List<String>) of this ChallengerMessages object.
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Method getChosenGod returns the chosenGod of this ChallengerMessages object.
     *
     *
     *
     * @return the chosenGod (type String) of this ChallengerMessages object.
     */
    public String getChosenGod() {
        return chosenGod;
    }

    /**
     * Method getGodDesc returns the godDesc of this ChallengerMessages object.
     *
     *
     *
     * @return the godDesc (type String) of this ChallengerMessages object.
     */
    public String getGodDesc() {
        return godDesc;
    }

    /**
     * Method getMessage returns the message of this WorkerPlacement object.
     *
     *
     *
     * @return the message (type Object) of this WorkerPlacement object.
     * @see Answer#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }
}
