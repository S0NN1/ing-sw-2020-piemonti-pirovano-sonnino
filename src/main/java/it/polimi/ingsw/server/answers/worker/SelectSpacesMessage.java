package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectSpacesMessage implements Answer {

    ArrayList<Couple> message;

    public SelectSpacesMessage(ArrayList<Space> moves){
        moves.forEach(space -> message.add(new Couple(space.getX(), space.getY())));
    }

    @Override
    public Object getMessage() {
        return message;
    }
}
