package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class ChallengerMessages implements Answer {
    private final String message;
    private final List<String> godList;
    private final List<Card> selectable;
    private final boolean startingPlayer;
    private final List<String> players;
    private final String chosenGod;
    private final String godDesc;

    public ChallengerMessages(String message) {
        this.message = message;
        this.godList = null;
        startingPlayer = false;
        players = null;
        selectable = null;
        chosenGod = null;
        godDesc = null;
    }

    public ChallengerMessages(String message, boolean startingPlayer, List<String> players) {
        this.message = message;
        this.godList = null;
        this.startingPlayer = startingPlayer;
        this.players = players;
        selectable = null;
        chosenGod = null;
        godDesc = null;
    }

    public ChallengerMessages(List<String> list) {
        this.godList = list;
        this.message = null;
        startingPlayer = false;
        players = null;
        selectable = null;
        chosenGod = null;
        godDesc = null;
    }

    public ChallengerMessages(String message, List<Card> selectable) {
        this.godList = null;
        this.message = message;
        startingPlayer = false;
        players = null;
        this.selectable = selectable;
        chosenGod = null;
        godDesc = null;
    }

    public ChallengerMessages(Card chosenGod) {
        this.godList = null;
        this.message = null;
        startingPlayer = false;
        players = null;
        this.selectable = null;
        this.chosenGod = chosenGod.name();
        this.godDesc = chosenGod.godsDescription();
    }

    public List<String> getGodList() {
        return godList;
    }

    public List<Card> getSelectable() {
        return selectable;
    }

    public boolean isStartingPlayer() {
        return startingPlayer;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getChosenGod() {
        return chosenGod;
    }

    public String getGodDesc() {
        return godDesc;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
