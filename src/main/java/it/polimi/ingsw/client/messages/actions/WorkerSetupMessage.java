package it.polimi.ingsw.client.messages.actions;

import java.util.ArrayList;

public class WorkerSetupMessage {

    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> yPositions;

    public void setxPositions(int x) {
        xPositions.add(x);
    }

    public void setyPositions(int y) {
        yPositions.add(y);
    }

    public int getXPosition(int index) {
        return xPositions.get(index);
    }

    public int getYPosition(int index) {
        return yPositions.get(index);
    }
}
