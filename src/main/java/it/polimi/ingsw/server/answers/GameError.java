package it.polimi.ingsw.server.answers;

import java.util.List;

public class GameError implements Answer {
    private final ErrorsType error;
    private final String message;
    private final List<int[]> coordinates;

    public GameError(ErrorsType error) {
        this.error = error;
        this.message = null;
        this.coordinates = null;
    }

    public GameError(ErrorsType  error, String message) {
        this.error= error;
        this.message = message;
        this.coordinates = null;
    }

    public GameError(ErrorsType  error, String message, List<int[]> coordinates) {
        this.error = error;
        this.message = message;
        this.coordinates = coordinates;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ErrorsType getError() {
        return error;
    }

    public List<int[]> getCoordinates() {
        return coordinates;
    }
}
