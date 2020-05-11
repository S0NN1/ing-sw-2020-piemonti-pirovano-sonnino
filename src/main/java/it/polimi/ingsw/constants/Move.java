package it.polimi.ingsw.constants;

import java.io.Serializable;

public class Move implements Serializable {

    private final Couple oldPosition;
    private final Couple newPosition;

    public Move(int oldX, int oldY, int newX, int newY){
        oldPosition = new Couple(oldX, oldY);
        newPosition = new Couple(newX, newY);
    }

    public Couple getOldPosition() {
        return oldPosition;
    }

    public Couple getNewPosition() {
        return newPosition;
    }
}
