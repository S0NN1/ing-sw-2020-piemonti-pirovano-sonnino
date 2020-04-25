package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ChallengerMessages implements Answer {
    public final String message;
    public final List<String> godList;
    public final boolean startingPlayer;
    public final ArrayList<String> players;

    public ChallengerMessages(String message) {
        this.message = message;
        this.godList = null;
        startingPlayer = false;
        players = null;
    }

    public ChallengerMessages(String message, boolean startingPlayer, ArrayList<String> players) {
        this.message = message;
        this.godList = null;
        this.startingPlayer = true;
        this.players = players;
    }

    public ChallengerMessages(List<String> list) {
        this.godList = list;
        this.message = null;
        startingPlayer = false;
        players = null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
