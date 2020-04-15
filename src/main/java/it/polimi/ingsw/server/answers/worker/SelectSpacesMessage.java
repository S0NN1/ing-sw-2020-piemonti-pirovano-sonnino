package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.constants.Couple;

import java.util.ArrayList;

/**
 * @author Alice Piemonti
 */
public class SelectSpacesMessage implements Answer {

    ArrayList<Couple> message = new ArrayList<Couple>();

    public SelectSpacesMessage(ArrayList<Space> moves){
        moves.forEach(space -> message.add(new Couple(space.getX(), space.getY())));
    }

    @Override
    public ArrayList<Couple> getMessage() {
        return message;
    }
}
