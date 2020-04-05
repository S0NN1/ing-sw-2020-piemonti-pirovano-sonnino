package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class GodRequest implements Answer {
    public final String message;
    public final List<String> godList;

    public GodRequest(String message) {
        this.message = message;
        this.godList = null;
    }

    public GodRequest(List<String> list) {
        this.godList = list;
        this.message = null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
