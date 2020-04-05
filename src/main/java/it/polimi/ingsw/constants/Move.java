package it.polimi.ingsw.constants;

public class Move {

    private Couple oldPosition;
    private Couple newPosition;

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
