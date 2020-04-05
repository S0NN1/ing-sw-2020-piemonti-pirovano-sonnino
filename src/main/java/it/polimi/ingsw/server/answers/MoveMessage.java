package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.board.Space;

import java.util.HashMap;
import java.util.Map;

public class MoveMessage implements Answer {
    Move message;

    public MoveMessage(Space oldPosition, Space newPosition){
        message = new Move(oldPosition.getX(), oldPosition.getY(), newPosition.getX(),newPosition.getY());
    }

    @Override
    public Move getMessage() {
        return message;
    }

}
