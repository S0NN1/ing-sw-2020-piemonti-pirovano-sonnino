package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.constants.Couple;

/**
 * @author Alice Piemonti
 */
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
