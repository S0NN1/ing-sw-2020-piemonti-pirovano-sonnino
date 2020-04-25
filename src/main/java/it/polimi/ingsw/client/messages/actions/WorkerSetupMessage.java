package it.polimi.ingsw.client.messages.actions;

import java.util.ArrayList;

public class WorkerSetupMessage implements UserAction{

    private final ArrayList<Integer> xPositions = new ArrayList<>();
    private final ArrayList<Integer> yPositions = new ArrayList<>();

    public WorkerSetupMessage(String[] in) {
        xPositions.add(Integer.parseInt(in[1]));
        yPositions.add(Integer.parseInt(in[2]));
        xPositions.add(Integer.parseInt(in[3]));
        yPositions.add(Integer.parseInt(in[4]));
    }

    public int getXPosition(int index) {
        return xPositions.get(index);
    }

    public int getYPosition(int index) {
        return yPositions.get(index);
    }
}
