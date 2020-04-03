package it.polimi.ingsw.server.answers;

public class GameError implements Answer {
    private ErrorsType error;

    public GameError(ErrorsType error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return null;
    }

    public ErrorsType getError() {
        return error;
    }
}
