package it.polimi.ingsw.server.answers;

public class RequestPlayersNumber implements Answer {
    private static String message="Choose the number of players! [2/3]";
    @Override
    public String getMessage() {
        return message;
    }
}
