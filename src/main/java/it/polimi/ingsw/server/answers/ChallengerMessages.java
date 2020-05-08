package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.Card;

import java.util.ArrayList;
import java.util.List;

public class ChallengerMessages implements Answer {
    public final String message;
    public final List<String> godList;
    public final List<Card> choosable;
    public final boolean startingPlayer;
    public final List<String> players;

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

    @Override
    public String getMessage() {
        return message;
    }
}
