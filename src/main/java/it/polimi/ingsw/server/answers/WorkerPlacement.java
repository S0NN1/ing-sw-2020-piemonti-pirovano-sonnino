package it.polimi.ingsw.server.answers;

import java.util.List;

public class WorkerPlacement implements Answer{
    private final String message;
    private final List<int[]> availableCoordinates;

    public WorkerPlacement(String message, List<int[]> coords) {
        this.message = message;
        availableCoordinates = coords;
    }

    public List<int[]> getAvailableCoordinates() {
        return availableCoordinates;
    }

    @Override
    public Object getMessage() {
        return message;
    }
}
