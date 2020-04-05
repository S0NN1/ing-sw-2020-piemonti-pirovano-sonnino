package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.model.board.Space;

import java.util.HashMap;
import java.util.Map;

public class BuildMessage implements Answer {

    Couple message;

    public  BuildMessage(Space space){
        message = new Couple(space.getX(), space.getY());
    }

    @Override
    public Couple getMessage() {
        return message;
    }
}
