package it.polimi.ingsw.server.answers;

public class RequestPlayersNumber implements Answer {
    private final boolean challenger;
    private final String message;

    public RequestPlayersNumber(String message, boolean challenger) {
        this.message = message;
        this.challenger = challenger;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isChallenger() {
        return challenger;
    }
}
