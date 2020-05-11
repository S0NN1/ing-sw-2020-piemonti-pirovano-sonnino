package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class ChallengerMessages implements Answer {
    private final String message;
    private final List<String> godList;
    private final List<Card> choosable;
    private final boolean startingPlayer;
    private final List<String> players;
    private String chosenGod;

    public ChallengerMessages(String message) {
        this.message = message;
        this.godList = null;
        startingPlayer = false;
        players = null;
        choosable = null;
    }

    public ChallengerMessages(String message, boolean startingPlayer, List<String> players) {
        this.message = message;
        this.godList = null;
        this.startingPlayer = startingPlayer;
        this.players = players;
        choosable = null;
    }

    public ChallengerMessages(List<String> list) {
        this.godList = list;
        this.message = null;
        startingPlayer = false;
        players = null;
        choosable = null;
    }

    public ChallengerMessages(String message, List<Card> choosable) {
        this.godList = null;
        this.message = message;
        startingPlayer = false;
        players = null;
        this.choosable = choosable;
    }

    public List<String> getGodList() {
        return godList;
    }

    public List<Card> getChoosable() {
        return choosable;
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

    public void setChosenGod(String chosenGod) {
        this.chosenGod = chosenGod;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
